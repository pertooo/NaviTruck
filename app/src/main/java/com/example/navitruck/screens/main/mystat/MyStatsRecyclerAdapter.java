package com.example.navitruck.screens.main.mystat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.ActivityDTO;
import com.example.navitruck.dto.MyStatDTO;

import java.util.List;

public class MyStatsRecyclerAdapter extends RecyclerView.Adapter<MyStatsRecyclerViewholder>  {

    private List<MyStatDTO> mData;
    private LayoutInflater mInflater;

    private Context context;

    public MyStatsRecyclerAdapter(Context context, List<MyStatDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyStatsRecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = mInflater.inflate(R.layout.item_my_stat, parent, false);
        return new MyStatsRecyclerViewholder(view);
    }





    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyStatsRecyclerViewholder holder, int position) {
        MyStatDTO dto = mData.get(position);

        String prefix = Constants.UNIT;
        if(dto.getUnit()== MyStatDTO.UnitType.Mile)
            prefix = Constants.Distance;
        else if(dto.getUnit() == MyStatDTO.UnitType.Dollar)
            prefix = Constants.CURRENCY;

        holder.title.setText(dto.getTitle());
        holder.weekVal.setText(dto.getWeekVal()+ prefix);
        holder.monthVal.setText(dto.getMonthVal()+ prefix);
        holder.totalVal.setText(dto.getTotalVal()+ prefix);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }





}