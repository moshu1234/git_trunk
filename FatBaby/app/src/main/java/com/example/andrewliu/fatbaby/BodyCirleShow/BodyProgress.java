package com.example.andrewliu.fatbaby.BodyCirleShow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.andrewliu.fatbaby.R;

/**
 * TODO: document your custom view class.
 */
public class BodyProgress extends View {
    private Paint paint;
    private int roundColor;
    private int diffColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int max;
    private int progress;
    private String text;
    private boolean textIsDisplayable;
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public BodyProgress(Context context) {
        this(context, null);
    }

    public BodyProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BodyProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BodyProgress);

        //获取自定义属性和默认值
        diffColor = mTypedArray.getColor(R.styleable.BodyProgress_diffColor, Color.RED);
        roundColor = mTypedArray.getColor(R.styleable.BodyProgress_broundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.BodyProgress_broundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.BodyProgress_btextColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.BodyProgress_btextSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.BodyProgress_broundWidth, 30);
        max = mTypedArray.getInteger(R.styleable.BodyProgress_bmax, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.BodyProgress_btextIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.BodyProgress_bstyle, 0);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas){
        int x=10;
        int w=30;
        int i=0;
        int left,top,right,bottom;
        int day[]={60,59,58,55,57,62};
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(25,0,30+25,180,paint);
//        int day[]=null;
//        day=new int[6];
        for( i=0;i<6;i++){
            left=x+40*i;
            top=180-day[i]*2;
            right=left+w;
            bottom=180;
            Log.e("aaaaa",left+" "+top+" "+right+" "+bottom);
            paint.setColor(roundProgressColor);
            canvas.drawRect(left,top,right,bottom,paint);
            paint.setColor(textColor);
            canvas.drawText(String.valueOf(day[i]),left,top-10,paint);
        }
    }

    protected void onDraw1(Canvas canvas) {
//        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        int centre = getWidth()/2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth/2); //圆环的半径
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

//        Log.e("log", centre + "");

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0

//        if(textIsDisplayable && percent != 0 && style == STROKE){
//            canvas.drawText(text, centre - textWidth / 2, centre + textSize/2, paint); //画出进度百分比
//        }
        float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(percent+"%", centre - textWidth / 2, centre, paint);
        textWidth = paint.measureText(text);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(text, centre - textWidth / 2, centre + textSize, paint);


        /**
         * 画圆弧 ，画圆环的进度
         */

        //设置进度是实心还是空心
        int initColor=roundProgressColor;
        int i = 0;
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限


        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
//                Log.e("-----------------b", "color="+initColor);
                if(progress !=0) {
                    for(i=0;i<=progress;i++){
//                        Log.e("-----------------a","progress="+i);
                        initColor += diffColor;
                        paint.setColor(initColor);
                        canvas.drawArc(oval, 360*i/max, 360 * 5 / max, false, paint);//每5度颜色递增
                    }
//                    canvas.drawArc(oval, 0, 360 * progress / max, true, paint);  //根据进度画圆弧
                }
//                initColor += diffColor;
//                paint.setColor(initColor);
//                canvas.drawArc(oval, 0, 360 * progress / max, false, paint);  //根据进度画圆弧
                break;
            }
            case FILL:{
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if(progress !=0) {
                    for(i=0;i<progress;i++){
                        Log.e("-----------------a","progress"+i);
                        initColor += 0x50505;
                        paint.setColor(initColor);
                        canvas.drawArc(oval,i,360 * i / max, true, paint);
                    }
//                    canvas.drawArc(oval, 0, 360 * progress / max, true, paint);  //根据进度画圆弧
                }
                break;
            }
        }

    }
    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     * @param max
     */
    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }

    }
    public synchronized void setText(String text){
        this.text=text;
        postInvalidate();
    }
    public synchronized void updateProgressText(int progress, String text){
        setProgress(progress);
        setText(text);
    }
    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
}
