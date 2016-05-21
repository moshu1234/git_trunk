package com.example.andrewliu.fatbaby;

import android.content.Context;
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
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BmobDataLib.SportsInfo;
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
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {

    /**
     * 一定一个接口
     */
    public interface ICoallBack{
        public void onCallSucess(String s);
        public void onCallToast(String s);
    }
//    ICoallBack iCoallBack=null;

    private String LogTitle = "MAINACTIVITY";
    private Context mainContext = this;
    public static Tencent mTencent = null;
    private IUiListener baseUiListener;
    public static Integer caseFlag = 0;
    public static String caseConten = new String();
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

//        UserInfo userInfo = new UserInfo();
//        userInfo.setOpenid("aaa");
//        userInfo.setPassword("123456");
//        userInfo.setName("123456");
//        userInfo.save(this, new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Log.e(LogTitle,"1");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Log.e(LogTitle,"2");
//            }
//        });
//        userInfo.addUser(this,userInfo);
        login();
        loginOther(this);
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
    public void loginOther(final Context context){
        Button other_login = (Button)findViewById(R.id.qqlogin);

        other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin(context);
            }
        });
    }
    public void qqLogin(final Context context){

        baseUiListener = new IUiListener() {
            @Override
            public void onCancel() {
                Log.e("FatBaby","qq share type "+ "onCancel");
            }
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
                //save openid and key on bmob
                UserInfo userInfo = new UserInfo();
                userInfo.queryOpenid(context, openid, new ICoallBack() {
                    @Override
                    public void onCallSucess(String s) {
                        gotoNextPage(s);
                    }

                    @Override
                    public void onCallToast(String s) {
                        myToast(s);
                    }
                });
            }
            @Override
            public void onError(UiError e) {
                // TODO Auto-generated method stub
                Log.e("FatBaby","qq share error "+ e.errorMessage);
            }
        };
        mTencent.login(this, "get_simple_userinfo,add_topic", baseUiListener);
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
    public void userCompare(){
        UserInfo userInfo = new UserInfo();
        TextView user = (TextView)findViewById(R.id.username);
        TextView password = (TextView)findViewById(R.id.password);
        userInfo.userCompare(this, user.getText().toString(), password.getText().toString(), new ICoallBack() {
            @Override
            public void onCallSucess(String s) {
                gotoNextPage(s);
            }

            @Override
            public void onCallToast(String s) {
                myToast(s);
            }
        }) ;
//        gotoNextPage();
    }
    public Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("aaa","congratuation!!!"+msg.what+":"+msg.obj.toString());
            super.handleMessage(msg);
        }
    };
    public void myToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public void gotoNextPage(String objid){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, infoShow.class);
        if (caseConten != null)
            intent.putExtra("objid", objid);
        MainActivity.this.startActivity(intent);
    }
}
