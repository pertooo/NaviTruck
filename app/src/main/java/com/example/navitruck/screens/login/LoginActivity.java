package com.example.navitruck.screens.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.navitruck.R;
import com.example.navitruck.callback.LoginCallBack;
import com.example.navitruck.dto.AbstractDTO;
import com.example.navitruck.dto.UserDto;
import com.example.navitruck.network.AuthenticateClient;
import com.example.navitruck.network.AuthenticateRestClient;
import com.example.navitruck.network.RestClient;
import com.example.navitruck.screens.dialog.DialogFragment;
import com.example.navitruck.screens.main.MainActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {

    private Context context;
    private Activity activity;

    private EditText usernameEdit;
    private EditText passwordEdit;

    private Button submitBtn;
    private Button resetBtn;

    DialogFragment dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(view);

        context = this;
        activity = this;
        initViews(view);
        setListeners();


    }

    private void initViews(View view){
        usernameEdit = view.findViewById(R.id.et_user_name);
        passwordEdit = view.findViewById(R.id.et_password);

        submitBtn = view.findViewById(R.id.btn_submit);
        resetBtn = view.findViewById(R.id.btn_reset);

        dialog = new DialogFragment();
    }

    private void setListeners(){
        submitBtn.setOnClickListener(view -> {
            startDialog();

            UserDto userDto = new UserDto();
            userDto.setUsername(usernameEdit.getText().toString());
            userDto.setPassword(passwordEdit.getText().toString());

            AuthenticateRestClient client = new AuthenticateRestClient(activity);
            client.authenticate(userDto, this);
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
        dismissDialog();

        String token = response.headers().get("Authorization");

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token), token);
        editor.commit();


        Log.e("onResponse token - ", token);

        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        dismissDialog();
        Toast.makeText(LoginActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
    }
}
