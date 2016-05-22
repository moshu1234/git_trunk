package com.example.andrewliu.fatbaby.BmobDataLib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.andrewliu.fatbaby.DataBase.UserInfoDB;
import com.example.andrewliu.fatbaby.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

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
//    private Handler userhandler;
    private String LogTitle = "USERINFO";
    private String openid;//qq login id
    private String user;
    private String password;
    private String name;
    private String email;
    private String gender;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String profession;
    private String hobby;

    public String getEmail() {
        return openid;
    }
    public void setEmail(String openid) {
        this.openid = openid;
    }
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

    public Integer getWeight() {

        return weight;
    }
    public void setWeight(Integer weight) {

        this.weight = weight;
    }
    public void addUser(Context context, final UserInfo userInfo){
        Log.e(LogTitle,"adduser");
        userInfo.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e(LogTitle,"添加数据成功，返回objectId为："+userInfo.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(LogTitle,"创建数据失败：" + s);
            }
        });
    }

    public void updateUserInfo(Context context, final UserInfo userInfo){
        userInfo.update(context ,"f0827fafcb",new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i(LogTitle,"更新成功："+userInfo.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(LogTitle,"更新失败："+s+"-----"+userInfo.getObjectId());
            }
        });
    }

    public void userCompare(final Context context, final String user, final String password, final MainActivity.ICoallBack iCoallBack){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {
            private UserInfoDB userInfoDB = new UserInfoDB(context);
            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e(LogTitle,"查询成功:"+arg0.length()+":"+arg0.toString());
                Integer  i = 0;
                for(i=0;i<arg0.length();i++){
                    try{
                        JSONObject object=arg0.getJSONObject(i);
                        Log.e(LogTitle,"uer and password"+object.getString("user")+":"+object.getString("password").equals(password));
                        if(object.getString("user")!=null && object.getString("user").equals(user)){
                            if(object.getString("password")!=null && object.getString("password").equals(password)){
                                Log.e(LogTitle,"user and password right"+"="+object.getString("objectId"));
//                                String []key = {"objid",object.getString("objectId")};
//                                UserInfoDB userInfoDB = new UserInfoDB(context);
//                                userInfoDB.update_info(object.getString("objectId"), key);
                                iCoallBack.onCallSucess(object.getString("objectId"));
                                return;
                            }
                            else {
//                                iCoallBack.onCallToast("密码错误，请重试");
                            }
                        }else {
                            Log.e(LogTitle,"no user valid"+user+":"+password);
                        }
                    }catch (Exception e){
                        Log.e(LogTitle,"exception");
                    }
                }
                iCoallBack.onCallToast("用户或密码错误，请重试");
                Log.e(LogTitle, "no user match, please register");

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e(LogTitle,"查询失败:"+arg1);
            }
        });
    }
    public void queryOpenid(final Context context, final String openid, final MainActivity.ICoallBack iCoallBack){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e(LogTitle, "queryOpenid查询成功:" + arg0.length() + ":" + arg0.toString());
                for (Integer i = 0; i < arg0.length(); i++) {
                    try {
                        JSONObject object = arg0.getJSONObject(i);
                        Log.e(LogTitle, "query openid:" + object.getString("openid")+":"+openid);
                        if (object.getString("openid").equals(openid)) {
                            iCoallBack.onCallSucess(object.getString("objectId"));
                            return;

                        }
                    } catch (Exception e) {
                        Log.e(LogTitle, "exception");
                    }
                }
                //if there is no openid, then add it
                UserInfo userInfo = new UserInfo();
                userInfo.setOpenid(openid);
                userInfo.addUser(context, userInfo);
                Log.e(LogTitle, "no openid match, we'll add :" + openid + "and send objid:" + userInfo.getObjectId());

                iCoallBack.onCallSucess(userInfo.getObjectId());

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e(LogTitle,"查询失败:"+arg1);
            }
        });
    }
}
