package com.example.yourdiary.dao;
/*
 *对表diary的操作
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yourdiary.bean.Diary;
import com.example.yourdiary.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DiaryDao {
    private static final String TABLE_NAME = "diary";

    private DBHelper dbHelper;

    public DiaryDao(Context context){
        dbHelper = new DBHelper(context);

    }

    //插入操作
    public long insert(ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long LineNumber = db.insert(TABLE_NAME,null,values);
        db.close();

        //返回行号
        return LineNumber;
    }

    //删除某篇日记
    public int deleteByDiary(String date){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        int number = db.delete(TABLE_NAME,"date=? AND name=?",new String[]{date,UserDao.name});
        db.close();
        return number;
    }

    //修改日记
    public int update(ContentValues values,String date){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        int number = db.update(TABLE_NAME,values,"name=? AND date=?",new String[]{UserDao.name,date});
        db.close();
        return  number;

    }
    //查询某个日期的日记
    public String select(String date){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{"content,name,date"},"name=? AND date=?",
                new String[]{UserDao.name,date},
                null,null,null);

        //如果这天写日记了，获取日记内容
        if(cursor.moveToNext()){
            String content =cursor.getString(cursor.getColumnIndex("content"));
            db.close();
            cursor.close();
            return content;
        }
        //如果没写
        else{
            String content ="";
            db.close();
            cursor.close();
            return content;
        }
    }

    //查询全部日记操作
    public List<Diary> selectAll(){
        List<Diary> diaries =new ArrayList<>();

        SQLiteDatabase db =dbHelper.getReadableDatabase();
        //结果按日期逆序排序排列
        Cursor cursor =db.query(TABLE_NAME,new String[]{"content,name,date"},
                "name=?", new String[]{UserDao.name},null,null,"date DESC");

        while (cursor.moveToNext()){
            Diary diary =new Diary();
            String date =cursor.getString(cursor.getColumnIndex("date"));
            String content=cursor.getString(cursor.getColumnIndex("content"));
            diary.setDate(date);
            diary.setContent(content);
            diaries.add(diary);
        }
        cursor.close();
        db.close();

        return diaries;
    }

}
