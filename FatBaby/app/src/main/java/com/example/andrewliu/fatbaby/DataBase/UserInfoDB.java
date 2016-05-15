package com.example.andrewliu.fatbaby.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liut1 on 5/13/16.
 */
public class UserInfoDB extends SQLiteOpenHelper {
    private final static String USER_DB_NAME ="userInfo.db";//数据库名
    private final static int USER_VERSION = 1;//版本号
    private final static String USER_TABLE_NAME = "userInfo";
    private Handler userDBhandler;
    private Map map;
    public UserInfoDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public UserInfoDB(Context context){
        this(context, USER_DB_NAME, null, USER_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + USER_TABLE_NAME
                + "("
                + "id integer primary key autoincrement,"
                + "username varchar,"
                + "age integer,"
                + "gender varchar,"
                + "height integer,"
                + "profession varchar,"//student, teacher, IT..
                + "hobby varchar,"    //hobbies:movie, music, tabble tennis...
                + "sportTime integer" //target sport time for every day
                + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //add user
    public long insert_userinfo(String username, Integer age, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!username.equals("")){
            cv.put("username",username);
        }
        else{
            cv.put("username","花花");
        }
        if(age > 0){
            cv.put("age",age);
        }
        else{
            cv.put("age",18);
        }
        if(!gender.equals("")){
            cv.put("gender",gender);
        }
        long row = db.insert(USER_TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "username" + " = ?";
        String[] whereValue ={ username };
        db.delete(USER_TABLE_NAME, where, whereValue);
    }
    public void find(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //get cursor
        Cursor cursor = db.query (USER_TABLE_NAME,null,null,null,null,null,null);
        //判断游标是否为空
        if(cursor.moveToFirst()) {
            //遍历游标
            while (cursor.moveToNext()){
                //获得ID
                int id = cursor.getInt(0);
                //获得用户名
                String findName=cursor.getString(1);

                if(name.equals(findName)){
                    Log.e("aaaaaaaaaaaaa","we've find the user "+name);
                    Message msg = new Message();
                    msg.obj = cursor.getString(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getString(5);
                    userDBhandler.sendMessage(msg);
                }
            }
            cursor.close();
        }
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(USER_DB_NAME);
    }

    //update info, if the content according to the key is integer, we need to convert integer to string first
    public void update_userinfo(String username,String key, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "username" + " = ?";
        String[] whereValue = { username };
        boolean integer_flag = false;
        switch (key){
            case "username":
                integer_flag = false;
                break;
            case "age":
                integer_flag = true;
                break;
            case "gender":
                integer_flag = false;
                break;
            case "height":
                integer_flag = true;
                break;
            case "profession":
                integer_flag = false;
                break;
            case "hobby":
                integer_flag = false;
                break;
            case "sportTime":
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
        db.update(USER_TABLE_NAME, cv, where, whereValue);
    }
    public void setHandler(Handler handler){
        userDBhandler = handler;
    }

}
