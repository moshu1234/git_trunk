package com.example.andrewliu.fatbaby.DataBase.BmobDataLib;

import cn.bmob.v3.BmobObject;

/**
 * Created by liut1 on 6/21/16.
 */
public class IMInterchange extends BmobObject {
    private String mMessage;
    private String mUserFrom;
    private String mUserTo;

    public void setmMessage(String message){
        mMessage = message;
    }
    public String getmMessage(){
        return mMessage;
    }
    public void setmUserFrom(String userName){
        mUserFrom = userName;
    }
    public String getmUserFrom(){
        return mUserFrom;
    }
    public void setmUserTo(String userName){
        mUserTo = userName;
    }
    public String getmUserTo(){
        return mUserTo;
    }
}
