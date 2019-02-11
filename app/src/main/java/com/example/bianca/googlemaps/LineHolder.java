package com.example.bianca.googlemaps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LineHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;
    public ImageButton imageButton;

    public LineHolder(View itemView) {
        super(itemView);
        imageButton = itemView.findViewById(R.id.imgBtnDelete);
        imageView = itemView.findViewById(R.id.imgMarker);
        textView =  itemView.findViewById(R.id.txtTitle);
    }
}