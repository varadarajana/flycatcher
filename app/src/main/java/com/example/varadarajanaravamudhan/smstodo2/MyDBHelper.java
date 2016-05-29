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
    public static final String TODO_COLUMN_STATUS = "status";

    public MyDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP table if exists TODO");
        db.execSQL(
                "create table TODO " +
                        "(id integer primary key, addr text, msg text, status text)"
        );
    }

    public void cleanAndStart(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP table if exists TODO");
        db.execSQL(
                "create table TODO " +
                        "(id integer primary key, addr text, msg text, status text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists TODO");
        onCreate(db);
    }

    public boolean insertTODO(String addr, String msg, boolean bStatus){
        String strStatus = "false";
        if(bStatus){
            strStatus = "true";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("addr", addr);
        cv.put("msg", msg);
        cv.put("status", strStatus);
        db.insert("TODO", null, cv);
        return true;
    }

    public boolean updateTOD(String id, String addr, String msg, boolean bStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("addr", addr);
        cv.put("msg", msg);
        String strStatus = "false";
        if(bStatus){
            strStatus = "true";
        }
        cv.put("status", bStatus);
        db.update("TODO", cv, "id=?", new String[]{id});
        return true;
    }

    public Integer deleteTODO(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("TODO", "id = ?", new String [] {id});
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
            smsSearchResult.setId(cr.getString(0));
            smsSearchResult.setStrAddr(cr.getString(1));
            smsSearchResult.setStrMsg(cr.getString(2));
            if (cr.getString(3).equals("true")) {
                smsSearchResult.setSelected(true);
            }
            todoList.add(smsSearchResult);
            cr.moveToNext();
        }
        return todoList;
    }
}
