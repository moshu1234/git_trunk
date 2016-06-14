package com.example.andrewliu.fatbaby;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.andrewliu.fatbaby.Activities.infoShow;
import com.example.andrewliu.fatbaby.UI.SlidMenu.AccountBind;
import com.example.andrewliu.fatbaby.UI.ExtendViews.FatBabyViewPager;
import com.example.andrewliu.fatbaby.UI.SlidMenu.Login;
import com.example.andrewliu.fatbaby.UI.SlidMenu.NewUserRegister;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;


public class MainActivity extends AppCompatActivity {

    private Thread thread;
    private FatBabyViewPager mFatBabyViewPager;
    private FragmentStatePagerAdapter mAdapter_login;
    private FragmentStatePagerAdapter mAdapter_register;
    private FragmentStatePagerAdapter mAdapter_bind;
    private List<Fragment> mFragments_login = new ArrayList<Fragment>();
    private List<Fragment> mFragments_register = new ArrayList<Fragment>();
    private List<Fragment> mFragments_bind = new ArrayList<Fragment>();
    private Login loginTab;
    private NewUserRegister newUserRegisterTab;
    private AccountBind accountBindTab;

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
                        accountBindTab.setObjid(msg.obj.toString());
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

        loginTab = new Login();
        loginTab.setHandler(mainHandler);
        newUserRegisterTab = new NewUserRegister();
        newUserRegisterTab.setRegisterHandler(mainHandler);
        accountBindTab = new AccountBind();
        accountBindTab.setBindHandler(mainHandler);
        mFragments_login.add(loginTab);
        mFragments_register.add(newUserRegisterTab);
        mFragments_bind.add(accountBindTab);
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
