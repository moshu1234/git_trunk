package com.example.andrewliu.fatbaby.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class FoodCalorieDBHandle {
    private FoodCalorieDB mFoodDB;
    public FoodCalorieDBHandle(Context context){
        super();
        mFoodDB = new FoodCalorieDB(context);
    }
    public void add(String foodname,Integer calorie, Integer weight, Integer count, String type){
        if (foodname.equals("")&&calorie.equals(0) && weight.equals(0)&&count.equals(0)&&type.equals("")){
            return;
        }
        mFoodDB.insert(foodname, calorie, weight, count, type);
    }

    public void delete(String foodname,Integer calorie, Integer weight, Integer count, String type){
        if (foodname.equals("")) {
            return;
        }
        mFoodDB.delete(foodname);
    }
    public void deleteDB(Context context){
        mFoodDB.deleteDatabase(context);
    }
    public void update(String foodname,Integer calorie, Integer weight, Integer count, String type){
        if (foodname.equals("")&&calorie.equals(0) && weight.equals(0)&&count.equals(0)&&type.equals("")){
            return;
        }
        mFoodDB.update(foodname, calorie, weight, count, type);
    }
    public void findByName(String foodname){
        if(foodname.equals("")){
            return;
        }
        mFoodDB.find(foodname);
    }
    public void findByCalorie(Integer calorie){

    }
    public void findByType(String type){

    }
    public void setFoodHandler(Handler handler){
        mFoodDB.setHandler(handler);
    }
}
