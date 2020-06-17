package com.example.yourdiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "user.db";//数据库名.
    private static int DB_VERSION = 2;
    public DBHelper(Context context) {
        //4个参数:
        //①.上下文;②.数据库的名称;③.一般都为null,用于创建一个Cursor对象;④.版本号.
        super(context, DB_NAME, null, DB_VERSION);
    }
    //建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户账号密码表
        String sql1="CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(20),"+
                "password VARVHAR(20))";
        db.execSQL(sql1);
       ///创建存储日记内容的表
        String sql2="CREATE TABLE diary(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content TEXT,"+
                "name VARCHAR(20),"+
                "date VARCHAR(20))";
        db.execSQL(sql2);
    }

    //数据库升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}