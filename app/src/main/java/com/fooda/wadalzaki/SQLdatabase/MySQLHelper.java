package com.fooda.wadalzaki.SQLdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库Helper
 * Created by leosunzh on 2015/12/12.
 */
public class MySQLHelper extends SQLiteOpenHelper{

    public MySQLHelper(Context context) {
        super(context,"myphone.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE persons(_id INTEGER PRIMARY KEY AUTOINCREMENT,uid TEXT,address VARCHAR(20)," +
                "email VARCHAR(20),imagePath VARCHAR(20),name VARCHAR(20),numberThree VARCHAR(20),numberTwo VARCHAR(20),phoneNumber VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
