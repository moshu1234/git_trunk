package com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab;

import android.annotation.TargetApi;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.andrewliu.fatbaby.DataBase.AppDataExchange;
import com.example.andrewliu.fatbaby.DataBase.BmobDataLib.IMUserList;
import com.example.andrewliu.fatbaby.Log.MyToast;
import com.example.andrewliu.fatbaby.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 6/21/16.
 */
public class IMChatUserList extends Fragment {
    private View view;
    private AppDataExchange data;
    private ArrayList<HashMap<String, Object>> listItem;
    private SimpleAdapter mSimpleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.imchat_user_list, container, false);
        initUserList();
        return view;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void initUserList(){
        ListView mListView = (ListView)view.findViewById(R.id.imchat_user_list);
        listItem = new ArrayList<HashMap<String,     Object>>();
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_pic", R.drawable.gsl);//加入图片
        map.put("user_title", "客服中心");
        listItem.add(map);
        getUserList();
        mSimpleAdapter = new SimpleAdapter(getContext(),listItem,//需要绑定的数据
                R.layout.imchat_item_user,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
                new String[] {"user_pic","user_title"},
                new int[] {R.id.imchat_item_user_pic,R.id.imchat_item_user_name}
        );
        mListView.setAdapter(mSimpleAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:according position get username and goto dialog chat page
                if(position < listItem.size()){
//                    data.setUserTo(listItem.get(position).get("user_title").toString());
                    Message message = new Message();
                    message.what = 1;//set user
                    message.obj = listItem.get(position).get("user_title").toString();
                    data.getHandler().sendMessage(message);
                }
            }
        });
        Button button = (Button)view.findViewById(R.id.add_user_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriends();
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void getUserList(){
        BmobQuery query = new BmobQuery("IMUserList");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Integer  i = 0;
                for(i=0;i<jsonArray.length();i++){
                    try{
                        JSONObject object=jsonArray.getJSONObject(i);
//                        Log.e("imchatuerlist","uer and password"+object.getString("user")+":"+object.getString("password").equals(password));
                        if(object.getString("mUserBelongTo")!=null && object.getString("mUserBelongTo").equals(data.getUserName())){
                            if(object.getString("mUserName")!=null){
                                Log.e("imchatuerlist","find one friend"+"="+object.getString("mUserName"));
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("user_pic", R.drawable.gsl);//加入图片
                                map.put("user_title", object.getString("mUserName"));
                                listItem.add(map);
                                mSimpleAdapter.notifyDataSetChanged();
                                return;
                            }
                        }
                    }catch (Exception e){
                        Log.e("imchatuerlist",e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("=======","getUserList onFailure:"+s);
            }
        });
    }

    public void setAppData(AppDataExchange ade){
        data = ade;
    }
    public void addFriends(){
        final EditText et = (EditText)view.findViewById(R.id.add_user_text);
        if(TextUtils.isEmpty(et.getText())){
            return;
        }
        if(data.getUserName().equals(et.getText().toString())){
            MyToast myToast = new MyToast();
            myToast.getShortToastByString(getContext(),"不能添加自己");
            return;
        }
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        if(obj.getString("name")!=null && obj.getString("user").equals(et.getText().toString())){
                            data.setUserTo(obj.getString("name"));
                            displayFriends(obj.getString("name"));
                            addFriendIntoList(obj.getString("name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
    public void displayFriends(String name){
        for(int i=0;i<listItem.size();i++){
            if(name.equals(listItem.get(i).get("user_title"))){
                MyToast myToast = new MyToast();
                myToast.getShortToastByString(getContext(),"此用户已添加过了");
                return;
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_pic", R.drawable.gsl);//加入图片
        map.put("user_title", name);
        listItem.add(map);
        mSimpleAdapter.notifyDataSetChanged();
    }
    public void addFriendIntoList(final String name){
        BmobQuery query = new BmobQuery("IMUserList");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                for(int i=0;i<jsonArray.length();i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String user = obj.getString("mUserBelongTo");
                        String friend = obj.getString("mUserName");
                        if(!TextUtils.isEmpty(user)){
                            if(name.equals(friend)){
                                MyToast myToast = new MyToast();
                                myToast.getShortToastByString(getContext(),"已经是好友了");
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //add friend
                Log.e("=======","addFriendIntoList");
                IMUserList imUserList = new IMUserList();
                imUserList.setmUserBelongTo(data.getUserName());
                imUserList.setmUserName(name);
                imUserList.save(getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("=======","addFriendIntoList onSuccess");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.e("=======","addFriendIntoList onFailure:"+s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                IMUserList imUserList = new IMUserList();
                imUserList.setmUserBelongTo(data.getUserName());
                imUserList.setmUserName(name);
                imUserList.save(getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("=======","addFriendIntoList onSuccess");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.e("=======","addFriendIntoList onFailure:"+s);
                    }
                });
            }
        });
    }
}
