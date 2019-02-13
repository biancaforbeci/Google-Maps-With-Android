package com.example.bianca.googlemaps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bianca.googlemaps.Database.MarkerDAO;
import com.example.bianca.googlemaps.Database.MarkerMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private final List<MarkerMaps> listMarkers;
    private Context context;

    public LineAdapter(ArrayList users) { listMarkers = users; }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder holder, final int position) {
        holder.textView.setText(listMarkers.get(position).getTitle() +  "\n" + listMarkers.get(position).getDate());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });

        switch (listMarkers.get(position).getImage()){

            case 1:
                holder.imageView.setImageResource(R.drawable.marcador);
                break;

            case 2:
                holder.imageView.setImageResource(R.drawable.pin);
                break;

            case 3:
                holder.imageView.setImageResource(R.drawable.favorite);
                break;

            case 4:
                holder.imageView.setImageResource(R.drawable.marker2);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return listMarkers != null ? listMarkers.size() : 0;
    }

    private void removeItem(int position) {

            new MarkerDAO().delete(listMarkers.get(position));
            listMarkers.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listMarkers.size());
    }


}