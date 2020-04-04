package com.example.navitruck.screens.main;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.navitruck.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Spinner spinnerDriverStatus;
    private Spinner spinnerTaskType;
    private TextView textTimer;



    private Button btn_subscribe;
    private Button btn_unsubscribe;

    private final String TOPIC = "JavaSampleApproach";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_unsubscribe = (Button) findViewById(R.id.btn_unsubscribe);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
            }
        });

        btn_unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC);
            }
        });

        initViews();
        setAdapter();
        setListeners();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


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


//                        FirebaseInstanceId.getInstance().getInstanceId()
//                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                        if (!task.isSuccessful()) {
//                                            Log.w(TAG, "getInstanceId failed", task.getException());
//                                            return;
//                                        }
//
//                                        // Get new Instance ID token
//                                        String token = task.getResult().getToken();
//
//                                        // Log and toast
//                                        String msg = "Ola lalalaaa";
//                                        Log.d(TAG, msg);
//                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                        break;
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
