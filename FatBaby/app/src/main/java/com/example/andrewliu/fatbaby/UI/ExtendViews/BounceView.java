package com.example.andrewliu.fatbaby.UI.ExtendViews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.volley.The;

/**
 * Created by liut1 on 6/25/16.
 */
public class BounceView extends View {
    private Bitmap bitmap;
    //定义两个常量，这两个常量指定该图片横向、纵向上都被划分为20格。
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    //记录该图片上包含441个顶点
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    //定义一个数组，保存Bitmap上的21 * 21个点的座标
    private final float[] verts = new float[COUNT * 2];
    //定义一个数组，记录Bitmap上的21 * 21个点经过扭曲后的座标
    //对图片进行扭曲的关键就是修改该数组里元素的值。
    private final float[] orig = new float[COUNT * 2];
//    private List<Integer> circle1 = new ArrayList<>();
//    private List<Integer> circle2 = new ArrayList<>();
//    private List<Integer> circle3 = new ArrayList<>();
//    private List<Integer> circle4 = new ArrayList<>();
//    private List<Integer> circle5 = new ArrayList<>();
    private List<List<Integer>> circle = new ArrayList<>();
    private int flag = 0;
    private int threadStart = 0;
    private List<Float> bouncePoint = new ArrayList<>();
    private int sequence[] = new int[]{3,6,9,12,15,12,9,6,3,0,-3,-6,-9,-12,-15,-12,-9,-6,-3,0};
    private int sequenceNum=0;
    private int bounceFast = 0;
    private int bounceFastChangeDelay = 0;
    private static int bounceTime = 50;
    public BounceView(Context context)
    {
        super(context);
        setFocusable(true);

    }
    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void initBitmap(int drawableId){
        thread.start();
        //根据指定资源加载图片
        bitmap = BitmapFactory.decodeResource(getResources(),
                drawableId);
        //获取图片宽度、高度
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++)
        {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++)
            {
                float fx = bitmapWidth * x / WIDTH;
                    /*
                     * 初始化orig、verts数组。
                     * 初始化后，orig、verts两个数组均匀地保存了21 * 21个点的x,y座标
                     */
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index += 1;
            }
        }
        //设置背景色
//        setBackgroundColor(Color.WHITE);
    }
    public void initBitmap(Bitmap bitmap){
//        thread.start();
        //根据指定资源加载图片
        //获取图片宽度、高度
        this.bitmap = bitmap;
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++)
        {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++)
            {
                float fx = bitmapWidth * x / WIDTH;
                    /*
                     * 初始化orig、verts数组。
                     * 初始化后，orig、verts两个数组均匀地保存了21 * 21个点的x,y座标
                     */
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index += 1;
            }
        }
        //设置背景色
//        setBackgroundColor(Color.WHITE);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
            /* 对bitmap按verts数组进行扭曲
             * 从第一个点（由第5个参数0控制）开始扭曲
             */
        if(bitmap != null)
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts
                , 0, null, 0, null);
    }
    public void bounceOnce(float diff){
        for(int i = 0;i<circle.size();i++){
            for(int j=0;j<circle.get(i).size();j+=2) {
                verts[circle.get(i).get(j + 1)] = orig[circle.get(i).get(j + 1)] + diff;
            }
            invalidate();
        }
    }
    public void setBounceFast(int fast){
        if(fast == 0){
            bounceFastChangeDelay++;
//            Log.e("========","bounceFastChangeDelay:"+bounceFastChangeDelay);
            if(bounceFastChangeDelay < 1000) {
                if (bounceTime < 200) {
                    if (bounceFastChangeDelay % 10 == 0) {
                        bounceTime++;
//                        Log.e("========", "bounceTime:" + bounceTime);
                    }
                }
            }
        }
        else {
            bounceTime = 10;
            bounceFastChangeDelay = 0;
        }
    }
    public void setCirclePointsClear(){
        while (circle.size() > 0){
            circle.remove(0);
        }
    }
    public void setCirclePoints(float cx, float cy){
        if(circle.size() >= 5){
            return;
        }
        bouncePoint.add(cx);
        bouncePoint.add(cy);
        List<Integer> list = new ArrayList<>();
//        while (circle.size() > 0){
//            circle.remove(0);
//        }
        for (int i = 0; i < COUNT * 2; i += 2)
        {
            float dx = cx - orig[i + 0];
            float dy = cy - orig[i + 1];
            float dd = dx * dx + dy * dy;
            //计算每个座标点与当前点（cx、cy）之间的距离
            float d = (float)Math.sqrt(dd);
            //对verts数组（保存bitmap上21 * 21个点经过扭曲后的座标）重新赋值
            if(d < 150){
                list.add(i);
                list.add(i+1);
            }
        }
        circle.add(list);
    }
    public void startThread(int start){
        threadStart = start;
    }
    public Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                if(threadStart != 0){
                    Message message = new Message();
                    message.what = 1;
                    boucnHandler.sendMessage(message);
                }
                try {
                    Thread.sleep(bounceTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    });
    public Handler boucnHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            bounceOnce(sequence[sequenceNum]);
            sequenceNum++;
            if(sequenceNum >= sequence.length){
                sequenceNum = 0;
            }
            return false;
        }
    });

}
