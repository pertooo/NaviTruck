package com.example.navitruck.screens.task.active;

import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navitruck.R;

public class StatusRecyclerViewholder  extends RecyclerView.ViewHolder  {

    TextView statusKey;
    TextView statusTxt;
    ImageButton noteBtn;
    ImageButton imagesBtn;

    ImageView checkedImage;
    ImageView checkedNote;

    StatusRecyclerViewholder(View itemView) {
        super(itemView);
        statusKey = itemView.findViewById(R.id.statusItemKey);
        statusTxt = itemView.findViewById(R.id.statusItemTxt);
        noteBtn = itemView.findViewById(R.id.noteBtn);
        imagesBtn = itemView.findViewById(R.id.imageBtn);
        checkedImage = itemView.findViewById(R.id.checkedImageImg);
        checkedNote = itemView.findViewById(R.id.checkedNoteImg);
    }



}