package com.mybooks.presenter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JTY on 2016/8/10 0014.
 */
public class MyDBOpenHelper extends SQLiteOpenHelper{

    public String person=" create table if not exists person(" +
            "personid integer primary key autoincrement," +
            "name text," +
            "password text," +
            "login BOOLEAN) ";

    public String account="create table if not exists account(" +
            "accountid integer primary key autoincrement," +
            "time text," +
            "money float," +
            "type text," +
            "earnings BOOLEAN," +//0=false是支出，1=true是收入
            "remark text," +
            "name text)";
    public MyDBOpenHelper(Context context) {
        super(context, "MybooksDB", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(person);
        db.execSQL(account);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
