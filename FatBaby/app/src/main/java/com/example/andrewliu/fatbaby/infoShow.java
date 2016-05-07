package com.example.andrewliu.fatbaby;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.BodyCirleShow.BodyProgress;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab01;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab02;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab03;
import com.example.andrewliu.fatbaby.SlidMenu.MenuLeftFragment;
import com.example.andrewliu.fatbaby.SlidMenu.MenuRightFragment;
import com.example.andrewliu.fatbaby.SlidMenu.StepCounterService;
import com.example.andrewliu.fatbaby.SlidMenu.StepDetector;
import com.example.andrewliu.fatbaby.progressbar.CircleProgressBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class infoShow extends SlidingFragmentActivity {

    private Thread thread;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private StepDetector detector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_show);

        // 初始化SlideMenu
        initRightMenu();
        // 初始化ViewPager
        initViewPager();  //
        Log.e("aaaaaa", "start step counter service");
//        detector = new StepDetector(this);
        Intent service = new Intent(this, StepCounterService.class);
        startService(service);
        if (thread == null) {

            thread = new Thread() {// ���߳����ڼ���ǰ����ı仯

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    int temp = 0;
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
//                        Log.e("aaaaaaaaaaa","flag="+StepCounterService.FLAG);
                        if (StepCounterService.FLAG) {
                            Message msg = new Message();
                            if (temp != StepDetector.CURRENT_SETP) {
                                temp = StepDetector.CURRENT_SETP;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                }
            };
            thread.start();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void initViewPager()
    {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        MainTab01 tab01 = new MainTab01();
        MainTab02 tab02 = new MainTab02();
        MainTab03 tab03 = new MainTab03();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        /**
         * 初始化Adapter
         */
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
//        mViewPager.setCurrentItem(1);
    }

    private void initRightMenu()
    {

        Fragment leftMenuFragment = new MenuLeftFragment();
        setBehindContentView(R.layout.left_menu_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
        SlidingMenu menu = getSlidingMenu();
//        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setBehindWidth()
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // menu.setBehindScrollScale(1.0f);
        menu.setSecondaryShadowDrawable(R.drawable.shadow);
        //设置右边（二级）侧滑菜单
//        menu.setSecondaryMenu(R.layout.right_menu_frame);
//        Fragment rightMenuFragment = new MenuRightFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
//        getSlidingMenu().hideSecondaryMenu();
    }

    public void showLeftMenu(View view)
    {
        getSlidingMenu().showMenu();
    }

    /*add by Andrew.Liu, don't show secondary menu*/
//    public void showRightMenu(View view)
//    {
//        getSlidingMenu().showSecondaryMenu();
//    }
    public void showRightMenu(View view)
    {
//        getSlidingMenu().hideSecondaryMenu();
    }

    public void onDestroy(){
        super.onDestroy();
        Log.e("aaaaaa","stop step service");
        Intent service = new Intent(this, StepCounterService.class);
        stopService(service);
    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.e("aaaaaaaaaa","what="+msg.what);
            TextView textView = (TextView)findViewById(R.id.totalSteps);
            if(textView != null){
                textView.setText("今天走了" + StepDetector.CURRENT_SETP + "步");
            }
            super.handleMessage(msg);
        }
    };

}
