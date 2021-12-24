package com.fooda.wadalzaki.SQLdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fooda.wadalzaki.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作逻辑类
 * Created by leosunzh on 2015/12/12.
 */
public class SQLEditor {
    MySQLHelper helper;
    Context context;
    public SQLEditor(Context context){
        this.context = context;
        helper = new MySQLHelper(context);
    }

    /**
     * ADD
     * @param user
     * @param
     */
    public void add(UserInfo user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid",user.getUid());
        values.put("address", user.getAddress());
        values.put("email", user.getEmail());
        values.put("imagePath",user.getImagePath());
        values.put("name", user.getName());
        values.put("numberThree", user.getNumberThree());
        values.put("numberTwo", user.getNumberTwo());
        values.put("phoneNumber", user.getNumberTwo());
        db.insert("persons", null, values);
        db.close();
    }

    /**
     * 删除数据
     */
    public void delete(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("persons","name='"+name+"'",null);
        db.close();
    }

    /**
     * 修改数据
     */
    public void edit(String name,String newNumber,String newName){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number",newNumber);
        values.put("name",newName);
        db.update("persons",values,"name='"+name+"'",null);
        db.close();
    }

    /**
     * 查询数据
     */
    public List<UserInfo> query(String selection){
        List<UserInfo> list = new ArrayList<UserInfo>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("persons",null,selection,null,null,null,null);
        while (cursor.moveToNext()){
            String uid = cursor.getString(1);
            String address = cursor.getString(2);
            String email = cursor.getString(3);
            String imagePath = cursor.getString(4);
            String name = cursor.getString(5);
            String numberThree = cursor.getString(6);
            String numberTwo = cursor.getString(7);
            String phoneNumber = cursor.getString(8);
            list.add(new UserInfo(address,email,imagePath,name,numberThree,numberTwo,phoneNumber,uid));
        }
        cursor.close();
        db.close();
        return list;
    }
}
