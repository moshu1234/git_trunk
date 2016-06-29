package com.example.andrewliu.fatbaby.Activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.Service.StepDetector;
import com.example.andrewliu.fatbaby.UI.ExtendViews.BounceView;

import java.io.FileNotFoundException;
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
        bt = (Button)findViewById(R.id.bounce_select);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
		        /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
		        /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
		        /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap1 = BitmapFactory.decodeStream(cr.openInputStream(uri));
                Bitmap bitmap = resize(bitmap1);
//                ImageView imageView = (ImageView) findViewById(R.id.bounce_test);
				/* 将Bitmap设定到ImageView */
//                imageView.setImageBitmap(bitmap);
                bounceView.initBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap resize(Bitmap bitmap) {
        float sx,sy;
        ViewGroup.LayoutParams layoutParams = bounceView.getLayoutParams();
        Log.e("========","aaa"+layoutParams.width+":"+layoutParams.height);
        sx = (float) layoutParams.width/(float) bitmap.getWidth();
        sy = (float) layoutParams.height/(float) bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(sx,sy); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
}
