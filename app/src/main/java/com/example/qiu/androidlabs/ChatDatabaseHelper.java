package com.example.qiu.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by qiu on 2017-02-17.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION_NUM = 1;
    public static final String DATABASE_NAME = "Chats";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "MESSAGE";


    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(" CREATE TABLE " + DATABASE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " text);");

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
        onCreate(db);

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }

}