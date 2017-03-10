package com.nexuslink.cyclenavi.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rye on 2017/2/21.
 */

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static HistoryDatabaseHelper instance;

    public static HistoryDatabaseHelper getInstance(Context context) {
        if(instance == null){
            instance = new HistoryDatabaseHelper(context,"HISTORY",null,1);
        }
        return instance;
    }

    public static final String HISTORY = "create table HISTORY("+
                    "latitude real," +
                    "longitude real," +
                    "name text," +
                    "district text)";

    public HistoryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
