package com.example.andrewliu.fatbaby.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

/**
 * Created by liut1 on 5/13/16.
 */
public class FitnessInfoDB extends SQLiteOpenHelper {
    /**
     * 一定一个接口
     */
    public interface FitnessCallback{
        public void Sucess(JSONObject s);
        public void fail(String s);
    }
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
            cv.put("date",date);
        }
        else{
            return -1;
        }
        if(progress > -1){
            cv.put("progress",progress);
        }
        if(running > -1){
            cv.put("running",running);
        }
        if(fitness > -1){
            cv.put("fitness",fitness);
        }
        if(weight > -1){
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(FITNESS_TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                String name=cursor.getString(1);
                if(date.equals(name)){
                    Log.e("fitnessinfodb","found "+date+", delete it right now");

                    String where = "date" + " = ?";
                    String[] whereValue = { date };
                    db.delete(FITNESS_TABLE_NAME, where,whereValue);
                }
            }
            cursor.close();
        }
        return;
    }
    public Boolean find(String date){
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
                    Log.e("aaaaaaaaaaaaa","we've find the fitness data "+date);
//                    Message msg = new Message();
//                    msg.obj = cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getInt(5)+" "+cursor.getInt(6);
//                    fitnessDBhandler.sendMessage(msg);
                    return true;
                }
            }
            cursor.close();
        }
        return false;
    }
    public void find(String date,FitnessCallback fitnessCallback){
        SQLiteDatabase db = this.getWritableDatabase();
        Boolean found = false;
        //get cursor
        Cursor cursor = db.query (FITNESS_TABLE_NAME,null,null,null,null,null,null);
        Log.e("Fitness find","cursor");
        //判断游标是否为空
        if(cursor.moveToFirst()) {
            Log.e("Fitness find","moveToFirst");
            //遍历游标
            while (cursor.moveToNext()){
                //获得ID
                int id = cursor.getInt(0);
                //获得用户名
                String findName=cursor.getString(1);
                Log.e("Fitness find","date:"+findName);
                if(date.equals(findName)){
                    Log.e("aaaaaaaaaaaaa","we've find the fitness data "+date);
//                    Message msg = new Message();
//                    msg.obj = cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getInt(5)+" "+cursor.getInt(6);
//                    fitnessDBhandler.sendMessage(msg);
                    JSONObject object = new JSONObject();
                    try {
                        object.put("date",cursor.getString(1));
                        object.put("progress",cursor.getString(2));
                        object.put("running",cursor.getString(3));
                        object.put("fitness",cursor.getString(4));
                        object.put("weight",cursor.getString(5));
                    }catch (Exception e){
                        Log.e("fitneesinfodb","exception "+e.getMessage());
                    }
                    found = true;
                    fitnessCallback.Sucess(object);
                }
            }
            cursor.close();
        }
        if(found == false){
            fitnessCallback.fail("we don't have this day "+date);
        }
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(FITNESS_DB_NAME);
    }
    //update info, if the content according to the key is integer, we need to convert integer to string first
    public void update_fitness(String date,String key, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "date" + " = ?";
        String[] whereValue = { date };
        boolean integer_flag = false;
        switch (key){
            case "date":
                integer_flag = false;
                break;
            case "progress":
                integer_flag = true;
                break;
            case "running":
                integer_flag = true;
                break;
            case "fitness":
                integer_flag = true;
                break;
            case "profession":
                integer_flag = false;
                break;
            case "weight":
                integer_flag = true;
                break;
            case "tommrow":
                integer_flag = true;
                break;
            default:
                integer_flag = true;
                break;
        }

        ContentValues cv = new ContentValues();
        if(integer_flag){
            cv.put(key, Integer.valueOf(content));
        }
        else{
            cv.put(key, content);
        }
        db.update(FITNESS_TABLE_NAME, cv, where, whereValue);
    }
    public void setHandler(Handler handler){
        fitnessDBhandler = handler;
    }

}
