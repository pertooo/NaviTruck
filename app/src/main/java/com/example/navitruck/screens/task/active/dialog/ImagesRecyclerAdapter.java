package com.example.navitruck.screens.task.active.dialog;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.ImageRecyclerDTO;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.screens.task.active.OnAdapterClickView;
import com.example.navitruck.screens.task.active.StatusRecyclerViewholder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerViewholder> {

    private ArrayList<ImageRecyclerDTO> mData;
    private LayoutInflater mInflater;
    private OnAddImgClick mClickListener;

    private Context context;

    // data is passed into the constructor
    ImagesRecyclerAdapter(Context context, ArrayList<ImageRecyclerDTO> data, OnAddImgClick mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
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


        holder.imageView.setImageURI(mData.get(position).getUri());
        if(mData.get(position).isSelected())
            holder.deleteView.setVisibility(View.VISIBLE);
        else
            holder.deleteView.setVisibility(View.GONE);


        holder.deleteView.setOnClickListener(view -> {
            mData.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void showDeleteIcon(int position){
        if(position != mData.size()-1){ // except last item
            mData.get(position).setSelected(true);
            this.notifyDataSetChanged();
        }
    }

    public void hideDeleteIcon(int position){
        mData.get(position).setSelected(false);
        this.notifyDataSetChanged();
    }

    public void handleDataChange(List<ImageRecyclerDTO> newData){
        mData.addAll(0, newData);
        this.notifyDataSetChanged();
    }

    public void handleDataChange(ImageRecyclerDTO data){
        mData.add(0, data);
        this.notifyDataSetChanged();
    }

    public List<Uri> getUploadImages(){
        List<Uri> data = mData.stream()
                .map(ImageRecyclerDTO::getUri)
                .collect(Collectors.toList());
        return data.subList(0, data.size()-1);
    }
}


