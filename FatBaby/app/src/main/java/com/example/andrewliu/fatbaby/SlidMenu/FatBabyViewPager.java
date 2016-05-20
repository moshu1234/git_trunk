package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by liut1 on 5/16/16.
 */
public class FatBabyViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public FatBabyViewPager(Context context) {
        super(context);
    }

    public FatBabyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.e("aaaaa","ACTION_MOVE");
//                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e("aaaaa","ACTION_UP    ACTION_CANCEL");
//                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }
}
