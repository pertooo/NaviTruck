package com.example.navitruck.screens.task.active;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navitruck.R;
import com.example.navitruck.dto.TruckStatus;

import java.util.ArrayList;



public class ActiveTaskFragment extends AppCompatActivity  {

    private static final String TAG = "ActiveTaskFragment";


    RecyclerView recyclerView;
    StatusRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = getLayoutInflater().inflate(R.layout.fragment_current_task, null);
        setContentView(view);

        initViews(view);

        ArrayList<TruckStatus> array = new ArrayList<>();
        array.add(new TruckStatus(1,"Going To Pickup", "ok", false, true));
        array.add(new TruckStatus(2,"Waiting For Pickup", null, false, true));
        array.add(new TruckStatus(3,"Picking Up", "", true, true));
        array.add(new TruckStatus(4,"On Way to Dest"));
        array.add(new TruckStatus(5,"Waiting for Unload"));
        array.add(new TruckStatus(6,"Unloading"));
        array.add(new TruckStatus(7,"Finished"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        adapter = new StatusRecyclerAdapter(this, array);
        recyclerView.setAdapter(adapter);

    }

    private void initViews(View v){
        recyclerView = v.findViewById(R.id.statusRec);
    }
}
