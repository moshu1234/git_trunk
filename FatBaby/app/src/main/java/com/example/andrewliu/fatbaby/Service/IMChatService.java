package com.example.andrewliu.fatbaby.Service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.andrewliu.fatbaby.DataBase.AppDataExchange;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by liut1 on 6/21/16.
 */
public class IMChatService extends Service {
    private AppDataExchange data;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                recvMsg();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setAppData(AppDataExchange ade){
        data = ade;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void recvMsg(){
        Log.e("=============","IMChatService recvMsg");
        BmobQuery query = new BmobQuery("IMInterchange");
        query.findObjects(data.getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Integer  i = 0;
                for(i=0;i<jsonArray.length();i++){
                    try{
                        JSONObject object=jsonArray.getJSONObject(i);
                        if(object.getString("mUserTo")!=null && object.getString("mUserTo").equals(data.getUserName())){
                            if(object.getString("mUserFrom")!=null && object.getString("mUserFrom").equals(data.getUserTo())){
                                Log.e("imchatuerlist","recv one msg"+"="+object.getString("mMessage"));
                                //TODO:send msg to current user
                                Message message = new Message();
                                message.what = 1;

                                HashMap<String,String> map = new HashMap<String, String>();
                                map.put("from",object.getString("mUserFrom"));
                                map.put("to",object.getString("mUserTo"));
                                map.put("msg",object.getString("mMessage"));
                                message.obj = map;
                                data.getHandler().sendMessage(message);
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

            }
        });
    }
}
