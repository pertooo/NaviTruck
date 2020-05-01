package com.example.navitruck.screens.main.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.screens.task.active.OnAdapterClickView;

public class ActivityRecyclerViewholder extends RecyclerView.ViewHolder  {

    TextView note;
    TextView date;

    ActivityRecyclerViewholder(View itemView) {
        super(itemView);
        note = itemView.findViewById(R.id.note);
        date = itemView.findViewById(R.id.date);
    }

//    void bind(TruckStatus truckStatus, OnAdapterClickView clickListener){
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickListener.onItemClick(truckStatus);
//            }
//        });
//    }




}
