package com.example.navitruck.screens.task.active;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.TruckStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ActiveTaskFragment extends AppCompatActivity implements OnAdapterClickView {

    private static final String TAG = "ActiveTaskFragment";


    RecyclerView recyclerView;
    StatusRecyclerAdapter adapter;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.fragment_current_task, null);
        setContentView(view);

        initViews(view);

        ArrayList<TruckStatus> array = getStatusList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new StatusRecyclerAdapter(this, array, this::onItemClick);
        recyclerView.setAdapter(adapter);

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

        recyclerView = v.findViewById(R.id.statusRec);
    }

    @Override
    public void onItemClick(TruckStatus truckStatus) {
        Toast.makeText(this, truckStatus.getStatusStr(), Toast.LENGTH_LONG).show();
    }
}
