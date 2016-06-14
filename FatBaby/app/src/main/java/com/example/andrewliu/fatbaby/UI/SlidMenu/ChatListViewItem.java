package com.example.andrewliu.fatbaby.UI.SlidMenu;

import android.graphics.Bitmap;

/**
 * Created by liut1 on 6/14/16.
 */
public class ChatListViewItem {
    private Integer type;
    private String text;
    private Bitmap icon;

    public ChatListViewItem(){

    }

    public Integer getType(){
        return type;
    }

    public  void setType(Integer type){
        this.type = type;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public Bitmap getIcon(){
        return icon;
    }

    public void setIcon(Bitmap icon){
        this.icon = icon;
    }
}
