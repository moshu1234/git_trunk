package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }


    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }
}
