package com.example.andrewliu.fatbaby.DataBase;

import android.content.Context;
import android.os.Handler;

/**
 * Created by liut1 on 6/22/16.
 */
public class AppDataExchange {
    private String userName;
    private String userFrom;
    private String userTo;
    private Handler handler;
    private Context context;

    public void setUserName(String user){
        userName = user;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserFrom(String user){
        userFrom = user;
    }
    public String getUserFrom(){
        return userFrom;
    }
    public void setUserTo(String user){
        userTo = user;
    }
    public String getUserTo(){
        return userTo;
    }
    public void setHandler(Handler hdl){
        handler = hdl;
    }
    public Handler getHandler(){
        return handler;
    }
    public void setContext(Context ct){
        context = ct;
    }
    public Context getContext(){
        return context;
    }
}
