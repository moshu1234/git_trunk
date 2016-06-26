package com.example.andrewliu.fatbaby.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.Service.StepDetector;
import com.example.andrewliu.fatbaby.UI.ExtendViews.BounceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liut1 on 6/25/16.
 */
public class ActivityPicBounce extends myBaseActivities {
    private Bitmap bitmap;
    private int flag = 0;
    private float cx,cy;
    private List<Float> points = new ArrayList<>();
    private int started = 0;
    private SensorManager mSensorManager;
    private float sx=0;
    private float sy=0;
    private BounceView bounceView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_bounce);
        bounceView = (BounceView)findViewById(R.id.bounce_test);
        bounceView.initBitmap(R.drawable.bounce2);
        StepDetector stepDetector = new StepDetector(this);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(stepDetector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        stepDetector.setCallBack(new StepDetector.StepCoallBack() {
            @Override
            public void SensorChangeSuccess(float x, float y) {
//                Log.e("=====","started:"+started);
                float diff = sx - x;
                if(diff > 2){
                    started = 1;
                }
                if(started == 0){
                    //stop right now
                    bounceView.startThread(started);
                }else {
                    if(diff > 2){
                        bounceView.setBounceFast(1);
                        started = 1;
                    }
                    else {
                        bounceView.setBounceFast(0);
                        started++;
                        //paused more than 500, then stop
                        if(started > 800){
                            started = 0;
                        }
                    }
                }
                if(diff < -1.0 || diff > 1.0) {
                    sx = x;
                    sy = y;
                    bounceView.startThread(started);
                }
            }
        });
        bounceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("=====",""+event.getX()+event.getY());
                cx = event.getX();
                cy = event.getY();
//                bounceView.bounceVertical(event.getX(), event.getY());
                return false;
            }
        });
        setBouncePoint();
    }
    public void setBouncePoint(){
        Button bt= (Button)findViewById(R.id.point_save);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bounceView.setCirclePoints(cx,cy);
            }
        });
        bt= (Button)findViewById(R.id.point_clear);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                points.clear();
                bounceView.setCirclePointsClear();
            }
        });

        bt= (Button)findViewById(R.id.bounce_start);
        final Button finalBt = bt;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bounceView.bounceVertical(cx, cy);
                if(started == 0){
//                    bounceView.setCirclePoint(cx,cy);
                    started = 1;
                    finalBt.setText("停止游戏");
                }else {
//                    started = 0;
                    finalBt.setText("开始游戏");
                }
            }
        });
    }
}
