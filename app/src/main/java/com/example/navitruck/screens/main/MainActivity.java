package com.example.navitruck.screens.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.navitruck.R;
import com.example.navitruck.dto.ActivityDTO;
import com.example.navitruck.dto.MyStatDTO;
import com.example.navitruck.screens.base.BaseActivity;
import com.example.navitruck.screens.main.activity.ActivityRecyclerAdapter;
import com.example.navitruck.screens.main.mystat.MyStatsRecyclerAdapter;
import com.example.navitruck.screens.task.active.ActiveTaskFragment;
import com.example.navitruck.screens.task.active.dialog.ImagesRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button openActiveTaskBtn;

    RecyclerView activityRecyclerView;
    ActivityRecyclerAdapter activityAdapter;

    RecyclerView mystatRecyclerView;
    MyStatsRecyclerAdapter mystatAdapter;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        context = this;

        initViews();
        setAdapter();
        setListeners();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_settings:
                // do what you want here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ActivityAdapter() {
        ArrayList<ActivityDTO> arrayList = new ArrayList<>();
        arrayList.add(new ActivityDTO("Route Staretd", "21/07 12:25"));
        arrayList.add(new ActivityDTO("changed status to routing", "23/07 21:29"));
        arrayList.add(new ActivityDTO("changed status to fucking", "23/07 23:23"));
        arrayList.add(new ActivityDTO("task Finished", "24/07 05:45"));
        arrayList.add(new ActivityDTO("Route Staretd", "21/07 12:25"));
        arrayList.add(new ActivityDTO("changed status to routing", "23/07 21:29"));
        arrayList.add(new ActivityDTO("changed status to fucking", "23/07 23:23"));
        arrayList.add(new ActivityDTO("task Finished", "24/07 05:45"));
        arrayList.add(new ActivityDTO("Route Staretd", "21/07 12:25"));
        arrayList.add(new ActivityDTO("changed status to routing", "23/07 21:29"));
        arrayList.add(new ActivityDTO("changed status to fucking", "23/07 23:23"));
        arrayList.add(new ActivityDTO("task Finished", "24/07 05:45"));


        activityRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        activityAdapter = new ActivityRecyclerAdapter(context, arrayList);
        activityRecyclerView.setAdapter(activityAdapter);

    }

    private void MyStatAdapter() {
        ArrayList<MyStatDTO> arrayList = new ArrayList<>();
        arrayList.add(new MyStatDTO("Amount", 450, 3300, 10650, MyStatDTO.UnitType.Dollar));
        arrayList.add(new MyStatDTO("Distance", 1300, 7840, 19654, MyStatDTO.UnitType.Mile));

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation


        mystatRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mystatAdapter = new MyStatsRecyclerAdapter(context, arrayList);
        mystatRecyclerView.setAdapter(mystatAdapter);

    }

    private void initViews() {
        activityRecyclerView = findViewById(R.id.activity_recyclerview);
        mystatRecyclerView = findViewById(R.id.my_stat_recyclerview);


        openActiveTaskBtn = findViewById(R.id.open_active_task_button);

    }

    private void setListeners() {


        openActiveTaskBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActiveTaskFragment.class);
            startActivity(intent);
        });
    }


    private void setAdapter() {
        ActivityAdapter();
        MyStatAdapter();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return true;
    }


}
