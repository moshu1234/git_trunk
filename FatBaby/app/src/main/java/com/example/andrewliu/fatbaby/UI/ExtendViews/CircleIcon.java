package com.example.andrewliu.fatbaby.UI.ExtendViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liut1 on 6/23/16.
 */
public class CircleIcon extends View {
//    private Paint paint;
    private Bitmap icon;
    private int w,h;

    public CircleIcon(Context context) {
        super(context);
    }

    public CircleIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onDraw(Canvas canvas){
        if(icon == null || w == 0 || h == 0){
            return;
        }
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),iconID);

        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(resize(icon), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        canvas.drawCircle(w/2,h/2,w/2,paint);
    }

    public void setIconID(Bitmap bitmap){
        this.icon = bitmap;
        postInvalidate();
    }
    public void setIconSize(int w, int h){
        this.w = w;
        this.h = h;
    }
    /**Bitmap放大的方法*/
    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f,1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
    /**Bitmap缩小的方法*/
    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.8f,0.8f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }/**Bitmap缩小的方法*/
    private Bitmap resize(Bitmap bitmap) {
        float sx,sy;
        sx = (float) w/(float) bitmap.getWidth();
        sy = (float) h/(float) bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(sx,sy); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
}
