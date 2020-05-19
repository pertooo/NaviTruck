package com.example.navitruck.screens.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.callback.TaskAcceptCallback;
import com.example.navitruck.dto.Task;
import com.example.navitruck.dto.abst.AbstractDTO;
import com.example.navitruck.dto.response.ResponseTaskDTO;
import com.example.navitruck.network.TaskClient;
import com.example.navitruck.network.rest.AuthenticateRestClient;
import com.example.navitruck.network.rest.TaskRestClient;
import com.example.navitruck.screens.dialog.CircularProgressBarFragment;
import com.example.navitruck.screens.task.active.ActiveTaskFragment;

import retrofit2.Call;
import retrofit2.Response;

public class NotifyTaskReceived extends AppCompatActivity implements TaskAcceptCallback {

    private static final String TAG = "NotifyTaskReceived";

    private Activity activity;

    private Button closeBtn;
    private Button submitBtn;
    private Button plusBtn;
    private Button minusBtn;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    CircularProgressBarFragment progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.fragment_new_task, null);

        setContentView(view);

        getSupportActionBar().hide();
        initViews(view);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Task task = (Task) bundle.getSerializable("task");
            setVaues(task);

        }

        setListeners();

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private void initViews(View view){
        activity = this;

        sharedPref = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //TODO FOR MARI

        submitBtn = view.findViewById(R.id.accept_button);
        closeBtn = view.findViewById(R.id.close);
        plusBtn = view.findViewById(R.id.plus_button);
        minusBtn = view.findViewById(R.id.minus_button);

        progress = new CircularProgressBarFragment();
    }

    private void setVaues(Task task){
        //TODO FOR MARI


    }
    private void setListeners(){
        closeBtn.setOnClickListener(view -> {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
            LayoutInflater factory = LayoutInflater.from(view.getContext());
            final View view1 = factory.inflate(R.layout.alert_dialog_fragment, null);
            alertDialog.setView(view1);
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Constants.SEEN_LAST_TASK = true;
                    finish();
                }
            });

            alertDialog.show();

        });

        submitBtn.setOnClickListener(view -> {
            startDialog();
            TaskRestClient client = new TaskRestClient(activity);
            client.accept(7, 3, getSubmitPrice(), this);

        });

        plusBtn.setOnClickListener(plusListener);
        minusBtn.setOnClickListener(minusListener);
    }

    View.OnClickListener plusListener = view -> {
        sumPrice(true);
    };

    View.OnClickListener minusListener = view -> {
        sumPrice(false);
    };

    private void sumPrice(boolean isPlus){
        double price =getSubmitPrice();
        double sum = 0d;
        if(isPlus){
            sum = price + 0.05d;
        }else{
            sum = price - 0.05d;
        }
        sum = Math.round(sum * 20.0) / 20.0;
        submitBtn.setText(sum +" $");
    }

    private double getSubmitPrice(){
        String priceStr = submitBtn.getText().toString();
        priceStr = priceStr.substring(0, priceStr.length()-1);

        return Double.valueOf(priceStr);
    }

    private void startDialog(){
        if(progress!=null){
            FragmentManager fm = getSupportFragmentManager();
            progress.setCancelable(false);

            progress.show(fm, "");
        }
    }

    private void endDialog(){
        if(progress!=null){
            progress.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK){

            return false;

        }
        return true;
    }


    @Override
    public void onResponse(Call<ResponseTaskDTO<Object>> call, Response<ResponseTaskDTO<Object>> response) {

        ResponseTaskDTO<Object> responseObj = response.body();

        Object obj = responseObj.getContent();
        if(obj.toString().equals("ASSIGNED")){
            Intent intent = new Intent(this, ActiveTaskFragment.class);
            finish();
            startActivity(intent);
        };

        endDialog();
    }

    @Override
    public void onFailure(Call<ResponseTaskDTO<Object>> call, Throwable t) {
        endDialog();
    }
}
