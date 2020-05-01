package com.example.navitruck.screens.main.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.ActivityDTO;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.screens.task.active.OnAdapterClickView;

import java.util.List;

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerViewholder>  {

    private List<ActivityDTO> mData;
    private LayoutInflater mInflater;

    private Context context;

    public ActivityRecyclerAdapter(Context context, List<ActivityDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ActivityRecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = mInflater.inflate(R.layout.item_activity, parent, false);
        return new ActivityRecyclerViewholder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ActivityRecyclerViewholder holder, int position) {
        ActivityDTO dto = mData.get(position);

        holder.note.setText(dto.getText());
        holder.date.setText(dto.getDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }





}