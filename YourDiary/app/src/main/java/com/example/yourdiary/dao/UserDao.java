package com.example.yourdiary.dao;
/*
*对表user的操作
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yourdiary.db.DBHelper;

public class UserDao {
    private static final String TABLE_NAME = "user";
    //对user表进行操作
    private DBHelper dbHelper;

    //用于获取日记时候筛选
    public static String name;

    public UserDao(Context context){
        dbHelper = new DBHelper(context);

    }

    //插入操作
    public long insert(ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //返回行号
        long LineNumber = db.insert(TABLE_NAME,null,values);
        db.close();
        return LineNumber;
    }

    //查询账号并判断
    public boolean selectName(String username){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select * from user where username=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        if (cursor.getCount()!=0) {
            //获取用户的username，并存储到全局变量name中
            cursor.moveToNext();
            name = cursor.getString(cursor.getColumnIndex("username"));
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

    //查询密码判断
    public boolean selectPassword(String username,String password){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? AND password=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,password});
        if (cursor.getCount()!=0) {
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }
}
