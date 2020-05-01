package com.example.navitruck.screens.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.callback.LoginCallBack;
import com.example.navitruck.dto.UserDto;
import com.example.navitruck.network.rest.AuthenticateRestClient;
import com.example.navitruck.screens.dialog.LoadingDialogFragment;
import com.example.navitruck.screens.main.ChartActivity;
import com.example.navitruck.screens.main.MainActivity;
import com.example.navitruck.screens.task.NotifyTaskReceived;
import com.example.navitruck.Utils.BasicShearedDataService;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {

    private Activity activity;

    private EditText usernameEdit;
    private EditText passwordEdit;

    private Button submitBtn;
    private Button resetBtn;

    LoadingDialogFragment dialog;

    private  SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private final String TOPIC = "11214";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(view);

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        initViews(view);
        setSavedData();
        setListeners();

        BasicShearedDataService shearedDataService = new BasicShearedDataService(this);
        shearedDataService.save(false);
    }

    @Override
    protected void onResume(){
        super.onResume();

        checkloggedIn();

    }


    private void initViews(View view){
        activity = this;


        sharedPref = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        usernameEdit = view.findViewById(R.id.et_user_name);
        passwordEdit = view.findViewById(R.id.et_password);

        submitBtn = view.findViewById(R.id.btn_submit);
        resetBtn = view.findViewById(R.id.btn_reset);

        dialog = new LoadingDialogFragment();
    }

    private void checkloggedIn(){
        startDialog();

        boolean logged = sharedPref.getBoolean(activity.getString(R.string.logged_in), true);

        if(logged){
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null && !Constants.SEEN_LAST_TASK){

                //Then new Task received. Open without authentication
                playSnd();

                Intent intent = new Intent(this, NotifyTaskReceived.class);
         //       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                startActivity(intent);

                dismissDialog();
            }else{
                //Then Login Automatically
                login();
            }


        }else {
            dismissDialog();
        }
    }

    private void setSavedData(){
        String username = sharedPref.getString(activity.getString(R.string.logged_user), "");
        String password = sharedPref.getString(activity.getString(R.string.logged_pass), "");

        usernameEdit.setText(username);
        passwordEdit.setText(password);
    }

    private void playSnd(){
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,1150);
    }

    private void login(){

        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);


//        //TODO delete comment for release
//        String username = usernameEdit.getText().toString();
//        String pass = passwordEdit.getText().toString();
//
//        editor.putString(getString(R.string.logged_user), username);
//        editor.putString(getString(R.string.logged_pass), pass);
//
//        UserDto userDto = new UserDto();
//        userDto.setUsername(username);
//        userDto.setPassword(pass);
//
//        AuthenticateRestClient client = new AuthenticateRestClient();
//        client.authenticate(userDto, this);
    }

    private void setListeners(){
        submitBtn.setOnClickListener(view -> {
            startDialog();
           login();
        });

        resetBtn.setOnClickListener(view -> {
            usernameEdit.setText("");
            passwordEdit.setText("");
        });
    }

    private void startDialog(){
        if(dialog!=null){
            FragmentManager fm = getSupportFragmentManager();
            dialog.setCancelable(false);
            dialog.show(fm, "dialog");
        }
    }

    private void dismissDialog(){
        if(dialog!=null)
            dialog.dismiss();
    }


    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        String token = response.headers().get(Constants.HEADER_STRING);

        if(token!=null){

            editor.putString(getString(R.string.token), token);
            editor.putBoolean(getString(R.string.logged_in), true);
            editor.commit();

            dismissDialog();

            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }else{
            dismissDialog();

            Toast.makeText(LoginActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        dismissDialog();
        Toast.makeText(LoginActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
    }

    public void test(){
        editor.putBoolean(getString(R.string.last_task_seen), false);
        editor.commit();
    }

}
