package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.DataBase.UserInfoDB;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.infoShow;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 5/24/16.
 */
public class Login extends Fragment {
    private String LogTitle = "LOGIN";

    /**
     * 一定一个接口
     */
    public interface ICoallBack{
        public void onCallSucess(String s);
        public void onCallToast(String s);
    }
    private View view;
    private Handler loginHandler;
    public static Tencent mTencent = null;
    private IUiListener baseUiListener;
    public static Integer caseFlag = 0;
    public static String caseConten = new String();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.login, container, false);
        login();
        loginOther(getContext());
        userRegister();
        return view;
    }

    public void login(){
        Button b_login=(Button)view.findViewById(R.id.login_b);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.obj = "register";
                message.what = 3;
                if(loginHandler == null){
                    Log.e("login","loginhander is null");
                }
                loginHandler.sendMessage(message);
//                userCompare();
            }
        });
    }
    public void loginOther(final Context context){
        Button other_login = (Button)view.findViewById(R.id.qqlogin);

        other_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin(context);
            }
        });
    }
    public void userRegister(){
        Button button = (Button)view.findViewById(R.id.register_b);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Message message = new Message();
                message.obj = "register";
                message.what = 1;
                loginHandler.sendMessage(message);
            }
        });
    }
    public void qqLogin(final Context context){

        mTencent = Tencent.createInstance("1105339373", getContext());
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
                queryOpenid(context, openid, new ICoallBack() {
                    @Override
                    public void onCallSucess(String s) {
                        userBindCheck(s);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode,data);
    }

    public void qqLogout(){
        mTencent.logout(getContext());
    }

    //match user with server, if true go on
    public void userCompare(){
        UserInfo userInfo = new UserInfo();
        TextView user = (TextView)view.findViewById(R.id.username);
        TextView password = (TextView)view.findViewById(R.id.password);
        userCompare(getContext(), user.getText().toString(), password.getText().toString(), new ICoallBack() {
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
    public void myToast(String s){
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void userBindCheck(final String objid){
        Log.e(LogTitle,"user bind check");
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
        bmobQuery.getObject(getContext(), objid, new GetListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                myToast("查询成功");
                Log.e(LogTitle,"check success:"+userInfo.getUser()+":"+userInfo.getPassword());
                if(TextUtils.isEmpty(userInfo.getUser()) == false){
                    if(TextUtils.isEmpty(userInfo.getPassword()) == false){
                        gotoNextPage(objid);
                        //bond already, go to next page
                        return;

                    }
                }
                if(userInfo.getBinded() != null && userInfo.getBinded()==1) {
                    gotoNextPage(objid);
                    //bond already, go to next page
                    return;
                }
                //wether user need bind
                if(userInfo.getBinded() == null || !userInfo.getBinded().equals(1)) {
                    //no invalid user or password, goto bind page
                    Message message = new Message();
                    message.what = 2;
                    message.obj = objid;
                    loginHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(LogTitle,"check fail "+s);
//                myToast("查询失败"+s);
            }
        });
    }
    public void gotoNextPage(String objid){
        //TODO:check wether need to go to account bin page
        Intent intent = new Intent();
        intent.setClass(getContext(), infoShow.class);
        if (caseConten != null)
            intent.putExtra("objid", objid);
        getContext().startActivity(intent);
    }


    public void userCompare(final Context context, final String user, final String password, final Login.ICoallBack iCoallBack){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {
            private UserInfoDB userInfoDB = new UserInfoDB(context);
            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e(LogTitle,"查询成功:"+arg0.length()+":"+arg0.toString());
                Integer  i = 0;
                for(i=0;i<arg0.length();i++){
                    try{
                        JSONObject object=arg0.getJSONObject(i);
                        Log.e(LogTitle,"uer and password"+object.getString("user")+":"+object.getString("password").equals(password));
                        if(object.getString("user")!=null && object.getString("user").equals(user)){
                            if(object.getString("password")!=null && object.getString("password").equals(password)){
                                Log.e(LogTitle,"user and password right"+"="+object.getString("objectId"));
//                                String []key = {"objid",object.getString("objectId")};
//                                UserInfoDB userInfoDB = new UserInfoDB(context);
//                                userInfoDB.update_info(object.getString("objectId"), key);
                                iCoallBack.onCallSucess(object.getString("objectId"));
                                return;
                            }
                            else {
//                                iCoallBack.onCallToast("密码错误，请重试");
                            }
                        }else {
                            Log.e(LogTitle,"no user valid"+user+":"+password);
                        }
                    }catch (Exception e){
                        Log.e(LogTitle,"exception");
                    }
                }
                iCoallBack.onCallToast("用户或密码错误，请重试");
                Log.e(LogTitle, "no user match, please register");

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e(LogTitle,"查询失败:"+arg1);
            }
        });
    }
    public void queryOpenid(final Context context, final String openid, final Login.ICoallBack iCoallBack){
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(context, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e(LogTitle, "queryOpenid查询成功:" + arg0.length() + ":" + arg0.toString());
                for (Integer i = 0; i < arg0.length(); i++) {
                    try {
                        JSONObject object = arg0.getJSONObject(i);
                        Log.e(LogTitle, "query openid:" + object.getString("openid")+":"+openid);
                        if (object.getString("openid").equals(openid)) {
                            Log.e(LogTitle,"get objid :"+object.getString("objectId"));
                            iCoallBack.onCallSucess(object.getString("objectId"));
                            return;

                        }
                    } catch (Exception e) {
                        Log.e(LogTitle, "exception");
                    }
                }
                //if there is no openid, then add it
                final UserInfo userInfo = new UserInfo();
                userInfo.setOpenid(openid);
                userInfo.addUser(context, userInfo);
                userInfo.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Log.e(LogTitle, "no openid match, we'll add :" + openid + "and send objid:" + userInfo.getObjectId());
                        iCoallBack.onCallSucess(userInfo.getObjectId());
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e(LogTitle,"查询失败:"+arg1);
            }
        });
    }
    public void setHandler(Handler handler){
        loginHandler = handler;
    }
}
