package com.example.andrewliu.fatbaby;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
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

import com.example.andrewliu.fatbaby.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.SlidMenu.StepDetector;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;


public class MainActivity extends AppCompatActivity {
    private String LogTitle = "MainActivity";
    public static Tencent mTencent = null;
    private IUiListener baseUiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        init_and_login();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void init_and_login(){
        mTencent = Tencent.createInstance("1105339373", this);

        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "066c9831ba79e2e93cb66f9cc46807ff");

        login();
        loginOther();
    }
    public void login(){
        Button b_login=(Button)findViewById(R.id.login_b);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCompare();
            }
        });
    }
    public void loginOther(){
        Button other_login = (Button)findViewById(R.id.qqlogin);

        other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin();
            }
        });
    }
    public void qqLogin(){

        baseUiListener = new IUiListener() {
            @Override
            public void onCancel() {
//                if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//                    Util.toastMessage(QQShareActivity.this, "onCancel: ");
//                }
                Log.e("FatBaby","qq share type "+ "onCancel");
            }
            //                {"ret":0,"openid":"D0499A8BFE640614D31A387EB42C5088",
            // "access_token":"0869F95F1F27D197CC6C7679BD03D89B",
            // "pay_token":"F6B5A1C7A8BBD62B8A45C3DF78AC6A6C",
            // "expires_in":7776000,
            // "pf":"desktop_m_qq-10000144-android-2002-",
            // "pfkey":"437efa30dbb1197524d9172a26e19ed2",
            // "msg":"",
            // "login_cost":637,
            // "query_authority_cost":371,
            // "authority_cost":-11545291}
            @Override
            public void onComplete(Object response) {
                String openid = new String();
                try {
                    JSONObject oj = (JSONObject) response;
                    openid = oj.getString("openid");
                }catch (Exception e){
                    Log.e("qq response","get openid fail");
                }
                // TODO Auto-generated method stub
                Log.e("FatBaby","qq share type "+ "onComplete="+response.toString());
                //qq user will login directly
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, infoShow.class);
                MainActivity.this.startActivity(intent);
//                userCompare(openid);
            }
            @Override
            public void onError(UiError e) {
                // TODO Auto-generated method stub
                Log.e("FatBaby","qq share error "+ e.errorMessage);
            }
        };
//        if (!mTencent.isSessionValid())
        {
//            mTencent.setOpenId(qqObject.getClass());
            mTencent.login(this, "get_simple_userinfo,add_topic", baseUiListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode,data);
    }
    public void qqLogout(){
        mTencent.logout(this);
    }
    //match user with server, if true go on
    public void userCompare(String openid){
        if(openid != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setHandler(mainHandler);
            userInfo.userCompare(this, openid);
        }
    }

    //match user with server, if true go on
    public void userCompare(){
        UserInfo userInfo = new UserInfo();
        TextView user = (TextView)findViewById(R.id.username);
        TextView password = (TextView)findViewById(R.id.password);
        userInfo.setHandler(mainHandler);
        userInfo.userCompare(this,user.getText().toString(),password.getText().toString());
    }
    public Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("aaa","congratuation!!!");
            if(msg.what == 1){
                qqLogout();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, infoShow.class);
                MainActivity.this.startActivity(intent);
            }
            super.handleMessage(msg);
        }
    };
}
