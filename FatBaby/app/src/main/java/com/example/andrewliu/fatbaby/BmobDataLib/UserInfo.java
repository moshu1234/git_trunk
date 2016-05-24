package com.example.andrewliu.fatbaby.BmobDataLib;

import android.content.Context;
import android.util.Log;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by liut1 on 5/18/16.
 */
public class UserInfo extends BmobObject {
    private String openid;//qq login id
    private String user;
    private String password;
    private String name;
    private Integer binded;
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
    public Integer getBinded() {

        return binded;
    }
    public void setBinded(Integer binded) {

        this.binded = binded;
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
        Log.e("USERINFO","adduser");
        userInfo.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("USERINFO","添加数据成功，返回objectId为："+userInfo.getObjectId());
                userInfo.setObjectId(userInfo.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("USERINFO","创建数据失败：" + s);
            }
        });
    }

    public void updateUserInfo(Context context, final UserInfo userInfo){
        userInfo.update(context ,"f0827fafcb",new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("USERINFO","更新成功："+userInfo.getObjectId());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("USERINFO","更新失败："+s+"-----"+userInfo.getObjectId());
            }
        });
    }

}
