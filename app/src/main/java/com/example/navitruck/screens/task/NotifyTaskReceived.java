package com.example.navitruck.screens.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.Task;

public class NotifyTaskReceived extends Activity {

    private static final String TAG = "NotifyTaskReceived";

    private Activity activity;

    private Button closeBtn;
    private Button submitBtn;
    private Button plusBtn;
    private Button minusBtn;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.fragment_new_task, null);
        setContentView(view);

        initViews(view);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Task task = (Task) bundle.getSerializable("task");
        }

        setListeners();

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private void initViews(View view){
        activity = this;

        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        submitBtn = view.findViewById(R.id.accept_button);
        closeBtn = view.findViewById(R.id.close);
        plusBtn = view.findViewById(R.id.plus_button);
        minusBtn = view.findViewById(R.id.minus_button);
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



}
