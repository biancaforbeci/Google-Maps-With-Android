package com.example.bianca.googlemaps.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conection extends SQLiteOpenHelper {

    private static Conection conection;

    public static  Conection getInstance(){
        return conection;
    }

    public Conection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        conection=this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String statement = " create table markers ( " +
                "id integer primary key autoincrement,"+
                "latitude double,"+
                "longitude double,"+
                "title varchar(300),"+
                "date Text"+
                ")";

        sqLiteDatabase.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
