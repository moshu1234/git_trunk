package com.example.andrewliu.fatbaby.BmobDataLib;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 5/18/16.
 * every day fitness info
 */
public class SportsInfo extends BmobObject {
    private String name;
    private String date;
    private Integer progress;
    private Integer milesRun;
    private Integer fitnessTime;
    private Integer weight;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String gender) {
        this.date = date;
    }
    public Integer getProgress() {
        return progress;
    }
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    public Integer getMilesRun() {
        return milesRun;
    }
    public void setMilesRun(Integer milesRun) {
        this.milesRun = milesRun;
    }
    public Integer getFitnessTime() {
        return fitnessTime;
    }
    public void setFitnessTime(Integer fitnessTime) {
        this.fitnessTime = fitnessTime;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void addSportsInfo(Context context, SportsInfo sportsInfo){
        sportsInfo.save(context, new SaveListener() {
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
