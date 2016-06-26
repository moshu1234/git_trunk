package com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.DataBase.AppDataExchange;
import com.example.andrewliu.fatbaby.DataBase.BmobDataLib.IMData;
import com.example.andrewliu.fatbaby.DataBase.BmobDataLib.IMInterchange;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.Service.StepDetector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 6/21/16.
 */
public class IMChatDialog extends Fragment {
    private int sendType=0;
    private View view;
    private RecyclerView recyclerView;
    List<ChatListViewItem> data = new ArrayList<ChatListViewItem>();
    private RecyclerView.Adapter adapter;
    private AppDataExchange appData;
    private Thread thread;
    private int threadFlag = 0;
    private int recvFlag = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.imchat_dialog_list, container, false);
        initListView();
        createRecvThread();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void initListView(){
        Button button= (Button)view.findViewById(R.id.imchat_dialog_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)view.findViewById(R.id.imchat_dialog_text);
                sendMsg(appData.getUserFrom(),appData.getUserTo(),editText.getText().toString());
                editText.setText("");
            }
        });
        recyclerView= (RecyclerView) view.findViewById(R.id.imchat_dialog_view);
        ChatListViewItem item1 = new ChatListViewItem();
        item1.setType(0);
        item1.setText("hello");
        item1.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.pic5));

        ChatListViewItem item2 = new ChatListViewItem();
        item2.setType(1);
        item2.setText("how are you");
        item2.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon3));

        data.add(item1);
        data.add(item2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(data.size());
    }
    public void displayMsg(String msg,int type){
        ChatListViewItem item = new ChatListViewItem();
        item.setText(msg);
        item.setType(type);
        item.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic5));
        data.add(item);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(data.size());
    }
    public void sendMsg(String usrFrom, String usrTo, String msg){
        displayMsg(msg,0);
        saveToInterchangeBmob(usrFrom, usrTo, msg);
        saveToBmob(usrFrom, usrTo, msg);
    }
    public Handler IMChatDialogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                recvMsg();
            }
            super.handleMessage(msg);
        }
    };
    public Handler getIMChatDialogHandler(){
        return IMChatDialogHandler;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void recvMsg(){
        if(recvFlag == 1){
            return;
        }
        recvFlag = 1;
//        Log.e("=============","IMChatDialog recvMsg:"+appData.getUserFrom()+":"+appData.getUserTo());
        BmobQuery query = new BmobQuery("IMInterchange");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Integer  i = 0;
                for(i=0;i<jsonArray.length();i++){
                    try{
                        JSONObject object=jsonArray.getJSONObject(i);
                        if(object.getString("mUserFrom")!=null && object.getString("mUserFrom").equals(appData.getUserTo())){
                            if(object.getString("mUserTo")!=null && object.getString("mUserTo").equals(appData.getUserFrom())){
                                Log.e("imchatuerlist","recv one msg"+"="+object.getString("mMessage"));
                                //TODO:send msg to current user
                                displayMsg(object.getString("mMessage"),1);
                                saveToBmob(object.getString("mUserFrom"), object.getString("mUserTo"), object.getString("mMessage"));
                                delFromInterchangeBmob(object.getString("objectId"));
                                recvFlag = 0;
                                return;
                            }
                        }
                    }catch (Exception e){
                        Log.e("imchatuerlist",e.getMessage());
                    }
                }
                Log.e("==========","have no message");
                recvFlag = 0;
                return;
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        recvFlag = 0;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void saveToBmob(String userFrom, String userTo, String msg){
        IMData mIMData = new IMData();
        mIMData.setmUserFrom(userFrom);
        mIMData.setmUserTo(userTo);
        mIMData.setmMessage(msg);
        mIMData.save(getContext());
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void saveToInterchangeBmob(String userFrom, String userTo, String msg){
        IMInterchange mIMData = new IMInterchange();
        mIMData.setmUserFrom(userFrom);
        mIMData.setmUserTo(userTo);
        mIMData.setmMessage(msg);
        mIMData.save(getContext());
    }
    public void delFromInterchangeBmob(String objid){
        IMInterchange imInterchange = new IMInterchange();
        imInterchange.setObjectId(objid);
        imInterchange.delete(getContext());
    }
    public void setAppData(AppDataExchange data){
        appData = data;
        Log.e("=================",appData.getUserFrom()+":"+appData.getUserTo());
//        appData.setHandler(IMChatDialogHandler);
    }
    public void createRecvThread(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadFlag = 2;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(threadFlag == 2)
                    {
                        continue;
                    }
                    if(threadFlag == 0)
                    {
                        break;
                    }
                    Message message = new Message();
                    message.what = 1;
                    IMChatDialogHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }
    public void startRecvThread(){
//        Log.e("=============","IMChatDialog recvMsg:"+appData.getUserFrom()+":"+appData.getUserTo());
        threadFlag = 1;
    }
    public void stopRecvThread(){
        threadFlag = 0;
    }
}