package com.example.navitruck.screens.main.mystat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;

public class MyStatsRecyclerViewholder extends RecyclerView.ViewHolder  {

    TextView title;
    TextView weekVal, monthVal, totalVal;

    MyStatsRecyclerViewholder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        weekVal = itemView.findViewById(R.id.week_value);
        monthVal = itemView.findViewById(R.id.month_value);
        totalVal = itemView.findViewById(R.id.total_value);
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
