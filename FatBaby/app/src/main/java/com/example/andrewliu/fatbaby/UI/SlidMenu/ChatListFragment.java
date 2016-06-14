package com.example.andrewliu.fatbaby.UI.SlidMenu;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.andrewliu.fatbaby.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liut1 on 6/14/16.
 */
public class ChatListFragment extends Fragment {
    private int sendType=0;
    private View view;
    private RecyclerView recyclerView;
    private List<String> mDatas;
    List<ChatListViewItem> data = new ArrayList<ChatListViewItem>();
    private RecyclerView.Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.chat_list_view, container, false);
        initData();
        initListView();
        return view;
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void initListView(){
        recyclerView= (RecyclerView) view.findViewById(R.id.chat_list_view);
        final EditText editText = (EditText)view.findViewById(R.id.send_et);
        Button button= (Button)view.findViewById(R.id.send_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatListViewItem item = new ChatListViewItem();
                item.setText(editText.getText().toString());
                editText.setText("");
                item.setType(sendType);
                if(sendType == 0) {
                    sendType = 1;
                    item.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hlj));
                }else {
                    sendType = 0;
                    item.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lda));
                }
                data.add(item);
                recyclerView.smoothScrollToPosition(data.size());
                adapter.notifyDataSetChanged();
            }
        });
        ChatListViewItem item1 = new ChatListViewItem();
        item1.setType(0);
        item1.setText("hello");
        item1.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.hlj));

        ChatListViewItem item2 = new ChatListViewItem();
        item2.setType(1);
        item2.setText("how are you");
        item2.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.lda));

//        ChatListViewItem item3 = new ChatListViewItem();
//        item3.setType(0);
//        item3.setText("fine, thank you");
//        item3.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.hlj));
//
//        ChatListViewItem item4 = new ChatListViewItem();
//        item4.setType(1);
//        item4.setText("I'm fine too");
//        item4.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.lda));
        data.add(item1);
        data.add(item2);
//        data.add(item3);
//        data.add(item4);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(data.size());
//        recyclerView.setAdapter(new ChatListAdapter(mDatas));
    }
}
