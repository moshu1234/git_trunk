package com.example.andrewliu.fatbaby.UI.SlidMenu.Login;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.DataBase.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by liut1 on 5/24/16.
 */
public class AccountBind extends Fragment {
    private View view;
    private Handler BindHandler;
    private String qqObjid = new String();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.account_bind, container, false);
        initButtons();
        return view;
    }
    public void setObjid(String objid)
    {
        this.qqObjid = objid;
    }
    public void initButtons(){
        Button button_ok, button_cancel;
        button_ok = (Button)view.findViewById(R.id.sure_bind);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBind();
                //DO bind and jump to infoshow page and never show this page again
            }
        });
        button_cancel = (Button)view.findViewById(R.id.cancel_bind);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO if user clicked the box, never show again, or show every time
                //jump to info show page
                cancleBind();
            }
        });
    }
    public void cancleBind(){
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.no_tips_bind);
        if(checkBox.isChecked()){
            UserInfo userInfo = new UserInfo();
            userInfo.setBinded(1);
            userInfo.update(getContext(), qqObjid, new UpdateListener() {
                @Override
                public void onSuccess() {
                    Log.e("cancleBind","update bind ok");
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.e("cancleBind","update bind fail");
                }
            });
        }
        Message message = new Message();
        message.what = 3;
        message.obj = qqObjid;
        BindHandler.sendMessage(message);

    }
    public void userBind(){
        final EditText userText = (EditText) view.findViewById(R.id.bind_user);
        final EditText editText = (EditText)view.findViewById(R.id.bind_password);
        if(TextUtils.isEmpty(userText.getText()) || TextUtils.isEmpty(editText.getText())){
            Log.e("AccountBind","username or password is null");
            myToast("用户名和密码不能为空");
        }
        Log.e("aaaa","text:"+userText.getText()+":"+editText.getText());
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject object= jsonArray.getJSONObject(i);
                        String user = object.getString("user");
                        String passwd = object.getString("password");
                        if(notEmptyString(user) && user.equals(userText.getText().toString())){
                            if(notEmptyString(passwd) && passwd.equals(editText.getText().toString())){
                                mergeQQandUserinfo(object.getString("objectId"));
                                Message message = new Message();
                                message.what = 3;
                                message.obj = object.getString("objectId");
                                BindHandler.sendMessage(message);
                                return;
                            }
                        }
                    }catch (Exception e){
                        Log.e("userBind",""+e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
    public void mergeQQandUserinfo(final String objid){
        Log.e("MERGEQQANDUSERINFO","mergerQQandUserinfo:"+objid);
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
        bmobQuery.getObject(getContext(), qqObjid, new GetListener<UserInfo>() {
            @Override
            public void onSuccess(final UserInfo qqInfo) {
                UserInfo userInfo = new UserInfo();
                if(notEmptyString(qqInfo.getOpenid())){
                    userInfo.setOpenid(qqInfo.getOpenid());
                }
                if(notEmptyString(qqInfo.getName())){
                    userInfo.setName(qqInfo.getName());
                }
                if(notEmptyString(qqInfo.getGender())){
                    userInfo.setGender(qqInfo.getGender());
                }
                if(notZeroInteger(qqInfo.getAge())){
                    userInfo.setAge(qqInfo.getAge());
                }
                if(notZeroInteger(qqInfo.getHeight())){
                    userInfo.setHeight(qqInfo.getHeight());
                }
                if(notZeroInteger(qqInfo.getWeight())){
                    userInfo.setWeight(qqInfo.getWeight());
                }
                if(notEmptyString(qqInfo.getProfession())){
                    userInfo.setProfession(qqInfo.getProfession());
                }
                if(notEmptyString(qqInfo.getHobby())){
                    userInfo.setHobby(qqInfo.getHobby());
                }
                if(notEmptyString(qqInfo.getEmail())){
                    userInfo.setEmail(qqInfo.getEmail());
                }
                userInfo.setBinded(1);
                userInfo.update(getContext(), objid, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("AccountBind","user update successfully");
                        UserInfo del = new UserInfo();
                        del.setObjectId(qqObjid);
                        del.delete(getContext(), new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                Log.e("AccountBind","delete qq objectid "+qqObjid+" successful");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Log.e("AccountBind","delete qq objectid "+qqObjid+" faled");
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("AccountBind","find "+qqObjid+" failed");
            }
        });
    }
    public void setBindHandler(Handler handler){
        BindHandler = handler;
    }
    public Boolean notEmptyString(String s){
        if(TextUtils.isEmpty(s) == true){
            return false;
        }
        return true;
    }
    public Boolean notZeroInteger(Integer i){
        if(i != null && i > 0){
            return  true;
        }

        return false;
    }
    public void myToast(String s){
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
