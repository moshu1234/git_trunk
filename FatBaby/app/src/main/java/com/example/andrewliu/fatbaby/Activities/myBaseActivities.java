package com.example.andrewliu.fatbaby.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

/**
 * Created by liut1 on 6/18/16.
 */
public class myBaseActivities extends AppCompatActivity {
    public static LinkedList<Activity> allActivitys = new LinkedList<Activity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allActivitys.add(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        allActivitys.remove(this);
    }
    public static void finishAll(){
        for (Activity activity : allActivitys) {
            activity.finish();
        }
        allActivitys.clear();
        //这个主要是用来关闭进程的, 光把所有activity finish的话，进程是不会关闭的
        System.exit(0);
//      android.os.Process.killProcess(android.os.Process.myPid());
    }
}
