package com.example.andrewliu.fatbaby.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.andrewliu.fatbaby.SlidMenu.StepDetector;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
/*
* Main course, slack
* name, calorie, weight, count
* */
public class FoodCalorieDB extends SQLiteOpenHelper{
    private final static String DB_NAME ="foodCalorie.db";//数据库名
    private final static int VERSION = 1;//版本号
    private final static String TABLE_NAME = "foodCalorie";
    private Handler DBhandler;
    public FoodCalorieDB(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }
    public FoodCalorieDB(Context context){
        this(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                        + TABLE_NAME
                        + "("
                        + "id integer primary key autoincrement,"
                        + "foodname varchar,"
                        + "calorie integer,"
                        + "weight integer,"
                        + "count integer,"
                        + "type varchar"
                        + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //增加操作
    public long insert(String foodname,Integer calorie, Integer weight, Integer count, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put("foodname", foodname);
        cv.put("calorie", calorie);
        cv.put("weight", weight);
        cv.put("count", count);
        cv.put("type", type);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(String foodname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "foodname" + " = ?";
        String[] whereValue ={ foodname };
        db.delete(TABLE_NAME, where, whereValue);
    }
    //修改操作
    public void update( String foodname,Integer calorie, Integer weight, Integer count, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "foodname" + " = ?";
        String[] whereValue = { foodname };

        ContentValues cv = new ContentValues();
        if (!foodname.equals("")) {
            cv.put("foodname", foodname);
        }
        if (!calorie.equals(0)) {
            cv.put("calorie", calorie);
        }
        if (!weight.equals(0)) {
            cv.put("weight", weight);
        }
        if(!count.equals(0)) {
            cv.put("count", count);
        }
        if(!type.equals("")) {
            cv.put("type", type);
        }
        db.update(TABLE_NAME, cv, where, whereValue);
    }
    public void find(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //get cursor
        Cursor cursor = db.query (TABLE_NAME,null,null,null,null,null,null);
        //判断游标是否为空
        if(cursor.moveToFirst()) {
            //遍历游标
            Log.e("fffffffffffffff","count="+cursor.getCount());
            while (cursor.moveToNext()){
                //获得ID
                int id = cursor.getInt(0);
                //获得用户名
                String findName=cursor.getString(1);
                Log.e("fffffffffffff","findName="+findName);
                if(name.equals(findName)){
                    Log.e("aaaaaaaaaaaaa","we've find the food "+name);
                    Message msg = new Message();
                    msg.obj = cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getString(5);
                    DBhandler.sendMessage(msg);
                }
            }
            cursor.close();
        }
    }
    public void setHandler(Handler handler){
        DBhandler = handler;
    }
}
