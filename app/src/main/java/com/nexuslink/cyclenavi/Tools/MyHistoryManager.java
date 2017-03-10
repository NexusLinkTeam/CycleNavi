package com.nexuslink.cyclenavi.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rye on 2017/2/21.
 */

public class MyHistoryManager {

    public static void saveHistory(Context context ,double latitude, double longitude, String name, String district){
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude",latitude);
        contentValues.put("longitude",longitude);
        contentValues.put("name",name);
        contentValues.put("district",district);
        Log.d("TAG123",district);
        HistoryDatabaseHelper.getInstance(context).getWritableDatabase().insert("HISTORY","",contentValues);
    }
    public static List<History> QueryHistory(Context context){
        List<History> histories = new ArrayList<>();
         Cursor cursor = HistoryDatabaseHelper.getInstance(context).getReadableDatabase().query("HISTORY",null,null,null,null,null,null);
        if(cursor.moveToLast()){
            do{
                histories.add(new History(cursor.getDouble(cursor.getColumnIndex("latitude")),
                        cursor.getDouble(cursor.getColumnIndex("longitude")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("district"))));

            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return histories;
    }

}
