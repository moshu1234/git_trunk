package com.example.andrewliu.fatbaby;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BmobDataLib.SportsInfo;
import com.example.andrewliu.fatbaby.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.SlidMenu.AccountBind;
import com.example.andrewliu.fatbaby.SlidMenu.FatBabyViewPager;
import com.example.andrewliu.fatbaby.SlidMenu.Login;
import com.example.andrewliu.fatbaby.SlidMenu.NewUserRegister;
import com.example.andrewliu.fatbaby.SlidMenu.StepDetector;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {

//    ICoallBack iCoallBack=null;
    private Thread thread;
    private FatBabyViewPager mFatBabyViewPager;
    private FragmentStatePagerAdapter mAdapter_login;
    private FragmentStatePagerAdapter mAdapter_register;
    private FragmentStatePagerAdapter mAdapter_bind;
    private List<Fragment> mFragments_login = new ArrayList<Fragment>();
    private List<Fragment> mFragments_register = new ArrayList<Fragment>();
    private List<Fragment> mFragments_bind = new ArrayList<Fragment>();

    private String LogTitle = "MAINACTIVITY";
    private Context mainContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initBmob();
        if (thread == null) {

            thread = new Thread() {// ���߳����ڼ���ǰ����ı仯

                @Override
                public void run() {
                    super.run();
                    int temp = 0;
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void initBmob(){

        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "066c9831ba79e2e93cb66f9cc46807ff");
    }
    public Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("aaa","congratuation!!!"+msg.what+":"+msg.obj);
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //goto register
                    if(mAdapter_register != null) {
                        mFatBabyViewPager.setAdapter(mAdapter_register);
                    }
                    break;
                case 2:
                    if(mAdapter_bind != null) {
                        mFatBabyViewPager.setAdapter(mAdapter_bind);
                    }
                    break;
                case 3:
                    Log.e(LogTitle,"jump to next page");
                    Intent intent = new Intent();
                    intent.putExtra("objid",msg.getData());
                    intent.setClass(MainActivity.this, infoShow.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    public void initViewPager(){
        mFatBabyViewPager = (FatBabyViewPager) findViewById(R.id.login_viewpager);

        Login tab01 = new Login();
        tab01.setHandler(mainHandler);
        NewUserRegister tab02 = new NewUserRegister();
        tab02.setRegisterHandler(mainHandler);
        AccountBind tab03 = new AccountBind();
        tab03.setBindHandler(mainHandler);
        mFragments_login.add(tab01);
        mFragments_register.add(tab02);
        mFragments_bind.add(tab03);
        /**
         * 初始化Adapter
         */
        mAdapter_login = new  FragmentStatePagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments_login.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments_login.get(arg0);
            }
        };
        mAdapter_register = new  FragmentStatePagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments_register.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments_register.get(arg0);
            }
        };
        mAdapter_bind = new  FragmentStatePagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments_bind.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments_bind.get(arg0);
            }
        };
        mFatBabyViewPager.setAdapter(mAdapter_login);
    }
}
