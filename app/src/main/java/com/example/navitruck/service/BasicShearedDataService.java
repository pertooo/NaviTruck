package com.example.navitruck.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.TruckStatus;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BasicShearedDataService {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    ArrayList<TruckStatus> statusArrayList = new ArrayList<>();

    public BasicShearedDataService(Context context){

        sharedPref = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        initStatusArray();


    }

    public void save(){
        Gson gson = new Gson();
        String statusJsonList = gson.toJson(statusArrayList);


        editor.putString("statusList", statusJsonList);
        editor.commit();
    }

    private void initStatusArray(){
        statusArrayList.add(new TruckStatus(1,"Going To Pickup", "ok", false, true));
        statusArrayList.add(new TruckStatus(2,"Waiting For Pickup", null, false, true));
        statusArrayList.add(new TruckStatus(3,"Picking Up", "", true, true));
        statusArrayList.add(new TruckStatus(4,"On Way to Dest"));
        statusArrayList.add(new TruckStatus(5,"Waiting for Unload"));
        statusArrayList.add(new TruckStatus(6,"Unloading"));
        statusArrayList.add(new TruckStatus(7,"Finished"));
    }


}
