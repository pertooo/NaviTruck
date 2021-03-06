package com.example.navitruck.screens.task.active;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.callback.TaskUpdateCallBack;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.dto.response.ResponseTaskDTO;
import com.example.navitruck.network.rest.TaskRestClient;
import com.example.navitruck.screens.dialog.CircularProgressBarFragment;
import com.example.navitruck.screens.main.MainActivity;
import com.example.navitruck.screens.task.active.dialog.AcceptFormDialogFragment;
import com.example.navitruck.screens.task.active.dialog.OnAcceptFormView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;


public class ActiveTaskFragment extends AppCompatActivity implements OnAdapterClickView, OnAcceptFormView {

    private static final String TAG = "ActiveTaskFragment";


    RecyclerView recyclerView;
    private Button backBtn;
    StatusRecyclerAdapter adapter;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    ArrayList<TruckStatus> statusArrayList = null;
    private Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.fragment_current_task, null);
        setContentView(view);
        activity = this;

        initViews(view);

        statusArrayList = getStatusList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
   //     recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new StatusRecyclerAdapter(this, statusArrayList, this::onItemClick);
        recyclerView.setAdapter(adapter);


        backBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(ActiveTaskFragment.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

    }




    private ArrayList<TruckStatus> getStatusList(){
        Gson gson = new Gson();
        String json = sharedPref.getString("statusList", "");
        Type type = new TypeToken<ArrayList<TruckStatus>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void initViews(View v){
        sharedPref = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        backBtn = v.findViewById(R.id.back_button);
        recyclerView = v.findViewById(R.id.statusRec);
    }

    @Override
    public void onItemClick(TruckStatus truckStatus) {
        AcceptFormDialogFragment fragment = new AcceptFormDialogFragment(this::acceptStatus, truckStatus.getStatus()-1);
        FragmentManager manager = getSupportFragmentManager();

        fragment.show(manager, "");
    }


    @Override
    public void acceptStatus(int position) {
        adapter.handleRecordChange(position);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return true;
    }



}
