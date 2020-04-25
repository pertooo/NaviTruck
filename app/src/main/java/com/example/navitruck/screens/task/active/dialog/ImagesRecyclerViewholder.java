package com.example.navitruck.screens.task.active.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;
import com.example.navitruck.dto.TruckStatus;
import com.example.navitruck.screens.task.active.OnAdapterClickView;

public class ImagesRecyclerViewholder extends RecyclerView.ViewHolder  {

    ImageView imageView;
    ImageView deleteView;

    private int position;

    ImagesRecyclerViewholder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_holder);
        deleteView = itemView.findViewById(R.id.delete_img);

    }

    void bind( OnAddImgClick clickListener, int position, int size){
        this.position = position;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == size)
                    clickListener.addImage();
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.deleteImage(position);
                return true;
            }
        });
    }




}
