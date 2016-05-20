package com.example.andrewliu.fatbaby;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    public static Tencent mTencent = null;
    private IUiListener baseUiListener;
    private Object qqObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.drawable.actionbar);
//        actionBar.setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
        mTencent = Tencent.createInstance("1105339373", this);

        login();
        loginOther();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void login(){
        Button b_login=(Button)findViewById(R.id.login_b);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogout();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,infoShow.class);
//                intent.setClass(MainActivity.this,ButtomTabView.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
    public void loginOther(){
        Button other_login = (Button)findViewById(R.id.qqlogin);

        other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin();
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this,infoShow.class);
////                intent.setClass(MainActivity.this,ButtomTabView.class);
//                MainActivity.this.startActivity(intent);
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
            @Override
            public void onComplete(Object response) {
                // TODO Auto-generated method stub
//                Util.toastMessage(QQShareActivity.this, "onComplete: " + response.toString());
                Log.e("FatBaby","qq share type "+ "onComplete="+response.toString());
                qqObject = response;

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,infoShow.class);
//                intent.setClass(MainActivity.this,ButtomTabView.class);
                MainActivity.this.startActivity(intent);
            }
            @Override
            public void onError(UiError e) {
                // TODO Auto-generated method stub
//                Util.toastMessage(QQShareActivity.this, "onError: " + e.errorMessage, "e");
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
}
