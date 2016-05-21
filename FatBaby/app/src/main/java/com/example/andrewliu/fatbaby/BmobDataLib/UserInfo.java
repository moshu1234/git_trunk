package com.example.andrewliu.fatbaby.BmobDataLib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.andrewliu.fatbaby.DataBase.UserInfoDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by liut1 on 5/18/16.
 */
public class UserInfo extends BmobObject {
    Boolean ret;
    private String openid;//qq login id
    private String user;
    private String password;
    private String name;
    private String gender;
    private Integer age;
    private Integer height;
    private String profession;
    private String hobby;
    private Handler userhandler;

    public void setRet(boolean ret){
        this.ret = ret;}
    public String getOpenid() {

        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getUser() {

        return name;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {

        this.password = password;
    }
    public String getName() {

        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {

        this.gender = gender;
    }
    public String getProfession() {

        return profession;
    }
    public void setProfession(String profession) {

        this.profession = profession;
    }
    public String getHobby() {
        return gender;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {

        this.age = age;
    }
    public Integer getHeight() {

        return height;
    }
    public void setHeight(Integer height) {

        this.height = height;
    }

    public void addUser(Context context, UserInfo userInfo){
        userInfo.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//                toast("添加数据成功，返回objectId为："+user.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                // TODO Auto-generated method stub
//                toast("创建数据失败：" + s);
            }
        });
    }

    public void updateUserInfo(Context context, final UserInfo userInfo){
        userInfo.update(context ,"f0827fafcb",new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("bmob","更新成功："+userInfo.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                // TODO Auto-generated method stub
                Log.i("bmob","更新失败："+s+"-----"+userInfo.getObjectId());
            }
        });
    }
    public void userCompare(final Context context, final String openid){
        //query total table UserInfo
        Log.e("usercompare","openid:"+openid);
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {
            private UserInfoDB userInfoDB = new UserInfoDB(context);
            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e("bbb","查询成功:"+arg0.length()+":"+arg0.toString());
                Integer  i = 0;
                for(i=0;i<arg0.length();i++){
                    try{
                        JSONObject object=arg0.getJSONObject(i);
                        Log.e("aaaa",openid+":openid:"+object.getString("openid"));
                        if(object.getString("openid").equals(openid)){
                                Log.e("aaa","openid right"+"="+object.getString("objectId"));
                                String []key = {"openid",object.getString("openid"),"access_token",object.getString("access_token")};
                                UserInfoDB userInfoDB = new UserInfoDB(context);
                                userInfoDB.update_info(object.getString("objectId"), key);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = object.getString("objectId");
                                userhandler.sendMessage(msg);
//                                userInfoDB.find(object.getString("objectId"));

                        }else {
                            Log.e("aaaa","no user valid"+openid);
                        }
                    }catch (Exception e){
                        Log.e("UserInfo","exception");
                    }
                }
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e("aaa","查询失败:"+arg1);
            }
        });
    }
    public void userCompare(final Context context, final String user, final String password){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {
            private UserInfoDB userInfoDB = new UserInfoDB(context);
            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e("bbb","查询成功:"+arg0.length()+":"+arg0.toString());
                Integer  i = 0;
                for(i=0;i<arg0.length();i++){
                    try{
                        JSONObject object=arg0.getJSONObject(i);
                        Log.e("aaaa","uer and password"+object.getString("user")+":"+object.getString("password").equals(password));
                        if(object.getString("user").equals(user)){
                            if(object.getString("password").equals(password)){
                                Log.e("aaa","user and password right"+"="+object.getString("objectId"));
                                String []key = {"objid",object.getString("objectId")};
                                UserInfoDB userInfoDB = new UserInfoDB(context);
                                userInfoDB.update_info(object.getString("objectId"), key);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = object.getString("objectId");
                                userhandler.sendMessage(msg);
//                                userInfoDB.find(object.getString("objectId"));
                            }
                        }else {
                            Log.e("aaaa","no user valid"+user+":"+password);
                        }
                    }catch (Exception e){
                        Log.e("UserInfo","exception");
                    }
                }
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e("aaa","查询失败:"+arg1);
            }
        });
    }
    public void queryData(Context context){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e("aaa","查询成功:"+arg0.length()+":"+arg0.toString());
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e("aaa","查询失败:"+arg1);
            }
        });
    }
    public void setHandler(Handler handler){
        userhandler = handler;
    }
}
