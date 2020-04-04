package com.example.navitruck.screens.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.navitruck.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private Spinner spinnerDriverStatus;
    private Spinner spinnerTaskType;
    private TextView textTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initViews();
        setAdapter();
        setListeners();
    }

    private void initViews(){
        spinnerDriverStatus = findViewById(R.id.driver_status);
        spinnerTaskType = findViewById(R.id.wish_task_type);

        textTimer = findViewById(R.id.timer_textview);
    }

    private void setListeners(){
        spinnerDriverStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position){
                    case 0:
                        spinnerTaskType.setVisibility(View.GONE);
                        textTimer.setText("გამორთული:2 დღე");
                        break;
                    case 1:
                        spinnerTaskType.setVisibility(View.VISIBLE);
                        textTimer.setText("ტვირთის გარეშე:36 საათი");
                        break;
                }

                if(position==0){
                    spinnerTaskType.setVisibility(View.GONE);
                    textTimer.setText("გამორთული:2 დღე");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });
    }


    private void setAdapter(){
        String[] spinnerStatusString = getResources().getStringArray(R.array.driver_status_geo);
        List<String> spinnerStatusList = new ArrayList<>(Arrays.asList(spinnerStatusString));

        ArrayAdapter<String> driverStatusSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,spinnerStatusList);
        spinnerDriverStatus.setAdapter(driverStatusSpinnerAdapter);


        String[] taskTypeSpinnerString = getResources().getStringArray(R.array.wish_task_type_geo);
        List<String> taskTypeSpinnerList = new ArrayList<>(Arrays.asList(taskTypeSpinnerString));

        ArrayAdapter<String> taskTypeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,taskTypeSpinnerList);
        spinnerTaskType.setAdapter(taskTypeSpinnerAdapter);
    }



}
