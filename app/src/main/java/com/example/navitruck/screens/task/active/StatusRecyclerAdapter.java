package com.example.navitruck.screens.task.active;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.Utils.Constants;
import com.example.navitruck.dto.TruckStatus;

import java.util.ArrayList;
import java.util.List;

public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerViewholder> {

    private List<TruckStatus> mData;
    private LayoutInflater mInflater;

    private Context context;

    // data is passed into the constructor
    StatusRecyclerAdapter(Context context, List<TruckStatus> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public StatusRecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = mInflater.inflate(R.layout.item_status, parent, false);
        return new StatusRecyclerViewholder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(StatusRecyclerViewholder holder, int position) {
        TruckStatus status = mData.get(position);
        holder.statusKey.setText(status.getStatus()+"");
        holder.statusTxt.setText(status.getStatusStr());

        final int sdk = android.os.Build.VERSION.SDK_INT;

        if(status.isImages())
            holder.checkedImage.setVisibility(View.VISIBLE);
        else
            holder.checkedImage.setVisibility(View.GONE);

        if(status.getNote()!=null){
            holder.checkedNote.setVisibility(View.VISIBLE);
        }else
            holder.checkedNote.setVisibility(View.GONE);

        if(status.isDone()){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.itemView.setBackground(ContextCompat.getDrawable(context, R.color.green));
            } else {
                holder.itemView.setBackground(ContextCompat.getDrawable(context, R.color.green));
            }
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }






//    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData.get(id);
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
}