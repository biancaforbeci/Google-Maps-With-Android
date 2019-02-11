package com.example.bianca.googlemaps.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MarkerDAO {

    public void save(MarkerMaps markerMaps){
        SQLiteDatabase conn= Conection.getInstance().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("latitude", markerMaps.getLatitude());
        values.put("longitude", markerMaps.getLongitude());
        values.put("title", markerMaps.getTitle());
        values.put("date",markerMaps.getDate());

        if(markerMaps.getId()==null){
            conn.insert("markers",null,values);
        }else{
            conn.update("markers",values, "id = ?", new String[]{markerMaps.getId().toString()});
        }


    }

    public ArrayList<MarkerMaps> list(){
        SQLiteDatabase conn = Conection.getInstance().getReadableDatabase();
        Cursor c = conn.query("markers", new String[]{"id","latitude","longitude","title","date"},null,null,null,null,"title");
        ArrayList<MarkerMaps> listMarkerMaps = new ArrayList<MarkerMaps>();
        if(c.moveToFirst()){ //move to first
            do{
                MarkerMaps markerMaps = new MarkerMaps();
                markerMaps.setId(c.getInt(0));
                markerMaps.setLatitude(c.getDouble(1));
                markerMaps.setLongitude(c.getDouble(2));
                markerMaps.setTitle(c.getString(3));
                markerMaps.setDate(c.getString(4));
                listMarkerMaps.add(markerMaps);
            }while(c.moveToNext());
        }
        return listMarkerMaps;
    }

    public void delete(MarkerMaps markerMaps){
        SQLiteDatabase conn= Conection.getInstance().getWritableDatabase();
        conn.delete("markers","id = ?", new String[]{markerMaps.getId().toString()});
    }



}