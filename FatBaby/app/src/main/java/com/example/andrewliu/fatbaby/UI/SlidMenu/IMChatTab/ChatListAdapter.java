package com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.R;

import java.util.List;

/**
 * Created by liut1 on 6/14/16.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder>{
    private List<ChatListViewItem> mDatas;
    public ChatListAdapter(List<ChatListViewItem> data){
        mDatas = data;
    }
    @Override
    public int getItemViewType(int position){
        ChatListViewItem item = mDatas.get(position);
        return item.getType();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.e("=========","onCreateViewHolder:"+viewType);
        MyViewHolder holder;
        if(viewType == 0) {
            holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.imchat_item_send, parent,
                    false));
        }
        else {
            holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.imchat_item_recv, parent,
                    false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.tv.setText(mDatas.get(position).getText());
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;
        ImageView iv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.imchat_item_text);
            iv = (ImageView)view.findViewById(R.id.imchat_item_pic);
        }
    }
}
