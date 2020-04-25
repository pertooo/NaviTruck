package com.example.navitruck.screens.task.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.TruckStatus;

import java.util.List;

public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerViewholder>  {

    private List<TruckStatus> mData;
    private LayoutInflater mInflater;
    private OnAdapterClickView mClickListener;

    private Context context;

    // data is passed into the constructor
    StatusRecyclerAdapter(Context context, List<TruckStatus> data, OnAdapterClickView clickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = clickListener;
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
        holder.bind(status, mClickListener);


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

        holder.noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNoteClick(status);
            }
        });

        holder.imagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImagesClick(status);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void onNoteClick(TruckStatus status){
        Toast.makeText(context, "Note - "+status.getNote(), Toast.LENGTH_SHORT).show();
    }

    private void onImagesClick(TruckStatus status){
        Toast.makeText(context, "On images clikc ", Toast.LENGTH_SHORT).show();
    }

    public void handleRecordChange(int position){
        mData.get(position).setDone(true);
        this.notifyItemChanged(position);
    }


}