package com.example.andrewliu.fatbaby.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
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
                + "objid varchar,"
                + "openid varchar,"
                + "access_token varchar"
                + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //add user
    public long insert_userinfo(String objid, String openid, String access_token){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!objid.equals("")){
            cv.put("objid",objid);
        }
        if(!openid.equals("")){
            cv.put("openid",openid);
        }
        if(!access_token.equals("")){
            cv.put("access_token",access_token);
        }
        long row = db.insert(USER_TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(String objid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "objid" + " = ?";
        String[] whereValue ={ objid };
        db.delete(USER_TABLE_NAME, where, whereValue);
    }
    public void find(String objid){
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

                if(objid.equals(findName)){
                    Log.e("aaaaaaaaaaaaa","we've find the user "+objid);
//                    Message msg = new Message();
//                    msg.obj = cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3);
//                    userDBhandler.sendMessage(msg);
                }
            }
            cursor.close();
        }
    }
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(USER_DB_NAME);
    }

    //update info, string[key][value]
    public void update_info(String objid, String[] content){
        Integer ret = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "objid" + " = ?";
        String[] whereValue = { objid };
        ContentValues cv = new ContentValues();
        if(content != null) {
            for (Integer i = 0; i < content.length; ) {
                Log.e("aaaa",content[i]);
                switch (content[i]) {
                    case "objid":

                        if (!content[i+1].equals("")) {
                            Log.e("bbb","update objid:"+content[i+1]);
                            cv.put("objid", content[i+1]);
                        }
                        break;
                    case "openid":
                        if (!content[i+1].equals("")) {
                            Log.e("bbb","update openid:"+content[i+1]);
                            cv.put("openid", content[i+1]);
                        }
                        break;
                    case "access_token":
                        if (!content[i+1].equals("")) {
                            Log.e("bbb","update access_token:"+content[i+1]);
                            cv.put("access_token", content[i+1]);
                        }
                        break;
                    default:
                        Log.e("userinfodb", "no valid info");
                        break;
                }
                i = i+2;

                ret = db.update(USER_TABLE_NAME, cv, where, whereValue);
                if (ret < 1) {
                    cv.put("objid", objid);
                    db.insert(USER_TABLE_NAME, null, cv);
                }
            }
        }
        else{
            Log.e("aa","no user found in userinfodb");
        }
    }
    public void setHandler(Handler handler){
        userDBhandler = handler;
    }

}
