package com.example.navitruck.screens.task.active.dialog;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.screens.task.active.OnAdapterClickView;
import com.example.navitruck.screens.task.active.StatusRecyclerViewholder;

import java.util.ArrayList;
import java.util.List;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerViewholder> {

    private List<Uri> mData;
    private ArrayList<Boolean> mDataBoolean;
    private LayoutInflater mInflater;
    private OnAddImgClick mClickListener;

    private Context context;

    // data is passed into the constructor
    ImagesRecyclerAdapter(Context context, List<Uri> data, OnAddImgClick mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
        this.mDataBoolean = new ArrayList<>(mData.size());
    }

    // inflates the row layout from xml when needed
    @Override
    public ImagesRecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = mInflater.inflate(R.layout.item_img, parent, false);
        return new ImagesRecyclerViewholder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ImagesRecyclerViewholder holder, int position) {
        holder.bind( mClickListener, position, mData.size());

        if(mData.size()>0) {
            if(mData.size()==position)
                return;
            holder.imageView.setImageURI(mData.get(position));
        }else
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked, context.getTheme()));

        if(mDataBoolean.size()>0 && mDataBoolean.get(position)){
            holder.deleteView.setVisibility(View.VISIBLE);
        }

        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(position);
                mDataBoolean.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    public void showDeleteIcon(int position){
        if(mDataBoolean.size()>0){
            mDataBoolean.set(position, true);
            this.notifyItemChanged(position);
        }
    }

    public void handleDataChange(List<Uri> newData){
        mData.addAll(newData);
        for(int i = 0; i < newData.size(); i++){
            mDataBoolean.add(false);
        }
        this.notifyDataSetChanged();
    }

    public void handleDataChange(Uri uri){
        mData.add(uri);
        mDataBoolean.add(false);
        this.notifyDataSetChanged();
    }

}


