package com.example.andrewliu.fatbaby.UI.SlidMenu.IMChatTab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.R;

import java.util.List;

/**
 * Created by liut1 on 6/21/16.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private List<ChatListViewItem> mDatas;
    public UserListAdapter(List<ChatListViewItem> data){
        mDatas = data;
    }
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.imchat_item_user, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(UserListAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.imchat_item_user_name);
        }
    }
}
