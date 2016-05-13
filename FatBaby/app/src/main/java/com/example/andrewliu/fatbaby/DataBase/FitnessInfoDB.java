package com.example.andrewliu.fatbaby.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by liut1 on 5/13/16.
 */
public class FitnessInfoDB extends SQLiteOpenHelper {
    private final static String FITNESS_DB_NAME ="fitnessInfo.db";//数据库名
    private final static int FITNESS_VERSION = 1;//版本号
    private final static String FITNESS_TABLE_NAME = "fitnessInfo";
    private Handler fitnessDBhandler;
    public FitnessInfoDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public FitnessInfoDB(Context context){
        this(context, FITNESS_DB_NAME, null, FITNESS_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + FITNESS_TABLE_NAME
                + "("
                + "id integer primary key autoincrement,"
                + "date varchar," //data
                + "progress integer,"//50%,60%,100%
                + "running integer,"//how far
                + "fitness integer,"//how long
                + "weight integer,"//weight the day
                + "tommrow integer" //if 1,means this line is set for tommrow
                + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //add user
    public long insert_fitnessinfo(String date, Integer progress, Integer running,Integer fitness,Integer weight,Integer tommrow){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!date.equals("")){
            cv.put("weekday",date);
        }
        else{
            return -1;
        }
        if(progress > 0){
            cv.put("progress",progress);
        }
        if(running > 0){
            cv.put("running",running);
        }
        if(fitness > 0){
            cv.put("fitness",fitness);
        }
        if(weight > 0){
            cv.put("weight",weight);
        }
        cv.put("tommrow",tommrow);

        long row = db.insert(FITNESS_TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(String date)
    {
        //won't delete any data
        return;
    }
    public void find(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        //get cursor
        Cursor cursor = db.query (FITNESS_TABLE_NAME,null,null,null,null,null,null);
        //判断游标是否为空
        if(cursor.moveToFirst()) {
            //遍历游标
            while (cursor.moveToNext()){
                //获得ID
                int id = cursor.getInt(0);
                //获得用户名
                String findName=cursor.getString(1);

                if(date.equals(findName)){
                    Log.e("aaaaaaaaaaaaa","we've find the user "+date);
                    Message msg = new Message();
                    msg.obj = cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getInt(5)+" "+cursor.getInt(6);
                    fitnessDBhandler.sendMessage(msg);
                }
            }
            cursor.close();
        }
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(FITNESS_DB_NAME);
    }
    //修改操作
    public void update_fitness_date( String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "date" + " = ?";
        String[] whereValue = { date };

        ContentValues cv = new ContentValues();
        if (!date.equals("")) {
            cv.put("date", date);
        }
        else {
            return;
        }
        db.update(FITNESS_TABLE_NAME, cv, where, whereValue);
    }
    public void update_fitness_progress( String date,Integer progress)
    {
        update_info(date,"progress", progress);
    }
    public void update_fitness_running( String date,Integer running)
    {
        update_info(date,"running", running);
    }
    public void update_fitness_fitness( String date,Integer fitness)
    {
        update_info(date,"fitness", fitness);
    }
    public void update_fitness_weight( String date,Integer weight)
    {
        update_info(date,"weight", weight);
    }
    public void update_fitness_tommrow( String date,Integer tommrow)
    {
        update_info(date,"tommrow", tommrow);
    }
    private void update_info(String username, String contentname, Integer content){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "username" + " = ?";
        String[] whereValue = { username };

        ContentValues cv = new ContentValues();
        if (!content.equals(0)) {
            cv.put(contentname, content);
        }
        else {
            return;
        }
        db.update(FITNESS_TABLE_NAME, cv, where, whereValue);
    }
    public void setHandler(Handler handler){
        fitnessDBhandler = handler;
    }

}
