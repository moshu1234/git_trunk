package com.example.andrewliu.fatbaby.UI.ExtendViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by liut1 on 5/18/16.
 */
public class FatBabyScrollView extends HorizontalScrollView {
    private boolean canScroll;

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;

    public FatBabyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        canScroll = true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.e("bbbbb","ACTION_MOVE");
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e("bbbbb","ACTION_UP ACTION_CANCEL ");
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }
}
