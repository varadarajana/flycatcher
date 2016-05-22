package com.example.varadarajanaravamudhan.smstodo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by varadarajanaravamudhan on 14/05/16.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "MYTODO.db";
    public static final String TABLE_NAME  = "TODO";
    public static final String TODO_COLOUMN_ID = "id";
    public static final String TODO_COLUMN_ADDR = "addr";
    public static final String TODO_COLUMN_MSG = "msg";

    public MyDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table TODO " +
                        "(id integer primary key, addr text, msg text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists TODO");
        onCreate(db);
    }

    public boolean insertTODO(String addr, String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("addr", addr);
        cv.put("msg", msg);
        db.insert("TODO", null, cv);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean cleanTODO(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from TODO");
        return true;
    }

    public ArrayList<SMSSearchResults> getAllTODO(){
        ArrayList<SMSSearchResults> todoList = new ArrayList<SMSSearchResults>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from TODO", null);
        cr.moveToNext();

        while(cr.isAfterLast() == false){
            SMSSearchResults smsSearchResult = new SMSSearchResults();
            smsSearchResult.setStrAddr(cr.getString(1));
            smsSearchResult.setStrMsg(cr.getString(2));
            todoList.add(smsSearchResult);
            cr.moveToNext();
        }
        return todoList;
    }
}
