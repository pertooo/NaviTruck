package com.example.navitruck.dto;

import android.net.Uri;

public class ImageRecyclerDTO {
    private Uri uri;
    private boolean selected;

    public ImageRecyclerDTO(Uri uri, boolean selected){
        this.uri = uri;
        this.selected = selected;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
