package com.example.andrewliu.fatbaby.DataBase.BmobDataLib;

import cn.bmob.v3.BmobObject;

/**
 * Created by liut1 on 6/21/16.
 */
public class IMUserList extends BmobObject{
    private String mUserName;
    private String mUserBelongTo;

    public void setmUserName(String name){
        mUserName = name;
    }
    public String getmUserName(){
        return mUserName;
    }
    public void setmUserBelongTo(String name){
        mUserBelongTo = name;
    }
    public String getmUserBelongTo(){
        return mUserBelongTo;
    }
}
