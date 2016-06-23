package com.example.andrewliu.fatbaby.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.andrewliu.fatbaby.DataBase.AppDataExchange;
import com.example.andrewliu.fatbaby.DataBase.BmobDataLib.IMUserList;
import com.example.andrewliu.fatbaby.Log.MyToast;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.Service.IMChatService;
import com.example.andrewliu.fatbaby.UI.ExtendViews.FatBabyViewPager;
import com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab.IMChatDialog;
import com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab.IMChatUserList;
import com.example.andrewliu.fatbaby.UI.SlidMenu.Login.NewUserRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liut1 on 6/21/16.
 */
public class ActivityIMChat extends myBaseActivities {
    private FatBabyViewPager mFatBabyViewPager;
    private FragmentStatePagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    AppDataExchange data;
    private IMChatService imChatService;
    private IMChatDialog imChatDialog;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imchat);
        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("user");
        MyToast myToast = new MyToast();
        myToast.getShortToastByString(this,name);
        data = new AppDataExchange();
        data.setUserName(name);
        data.setUserFrom(name);
        data.setContext(this);
        data.setHandler(IMActivityHandler);
        addChatFragments();
    }
    public void addChatFragments(){
        mFatBabyViewPager = (FatBabyViewPager) findViewById(R.id.chat_view);
        IMChatUserList imChatUserList = new IMChatUserList();
        imChatUserList.setAppData(data);
        mFragments.add(imChatUserList);
        imChatDialog = new IMChatDialog();
        mFragments.add(imChatDialog);
        mAdapter = new  FragmentStatePagerAdapter(getSupportFragmentManager())
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
        mFatBabyViewPager.setAdapter(mAdapter);
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected","onServiceConnected:"+name);
            data.setHandler(imChatDialog.getIMChatDialogHandler());
            imChatService = (IMChatService) service;
            ((IMChatService) service).setAppData(data);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceDisconnected","onServiceDisconnected:"+name);
        }
    };
    public void bindDataService(){
        Intent intent = new Intent(this,IMChatService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }
    public void unbindDataService(){
        unbindService(conn);
    }
    public Handler IMActivityHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 1){
                data.setUserTo(msg.obj.toString());
                imChatDialog.setAppData(data);
                mFatBabyViewPager.setCurrentItem(1);
                imChatDialog.startRecvThread();

//                bindDataService();
            }
            return false;
        }
    });
}

