package com.example.navitruck.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.TruckStatus;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

public class BasicShearedDataService {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    ArrayList<TruckStatus> statusArrayList = new ArrayList<>();

    ArrayList<TruckStatus> statusArrayListUpdated = new ArrayList<>();

    public BasicShearedDataService(Context context){

        sharedPref = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        initStatusArray();


    }

    public BasicShearedDataService(Context context, ArrayList<TruckStatus> arrayList){
        sharedPref = context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        statusArrayListUpdated = arrayList;
    }

    public void save(boolean updated){
        Gson gson = new Gson();
        String statusJsonList = null;
        if(updated){
            statusJsonList = gson.toJson(statusArrayListUpdated);
            editor.putString("statusList", statusJsonList);
            editor.commit();
        }else{
            if(sharedPref.getString("statusList", null) == null){
                statusJsonList = gson.toJson(statusArrayList);
                editor.putString("statusList", statusJsonList);
                editor.commit();
            }


        }



    }



    private void initStatusArray(){
        statusArrayList.add(new TruckStatus(1,"Going To Pickup", "ok", false, false));
        statusArrayList.add(new TruckStatus(2,"Waiting For Pickup", null, false, false));
        statusArrayList.add(new TruckStatus(3,"Picking Up", "", true, false));
        statusArrayList.add(new TruckStatus(4,"On Way to Dest"));
        statusArrayList.add(new TruckStatus(5,"Waiting for Unload"));
        statusArrayList.add(new TruckStatus(6,"Unloading"));
        statusArrayList.add(new TruckStatus(7,"Finished"));
    }


}
