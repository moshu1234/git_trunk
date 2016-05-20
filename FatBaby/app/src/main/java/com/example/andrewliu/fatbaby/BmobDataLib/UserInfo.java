package com.example.andrewliu.fatbaby.BmobDataLib;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 5/18/16.
 */
public class UserInfo extends BmobObject {
    private String name;
    private String gender;
    private Integer age;
    private Integer height;
    private String profession;
    private String hobby;

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
}
