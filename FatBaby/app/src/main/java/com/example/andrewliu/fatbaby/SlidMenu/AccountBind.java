package com.example.andrewliu.fatbaby.SlidMenu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andrewliu.fatbaby.R;

/**
 * Created by liut1 on 5/24/16.
 */
public class AccountBind extends Fragment {
    private View view;
    private Handler BindHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.account_bind, container, false);
        initButtons();
        return view;
    }

    public void initButtons(){
        Button button_ok, button_cancel;
        button_ok = (Button)view.findViewById(R.id.sure_bind);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DO bind and jump to infoshow page and never show this page again
            }
        });
        button_cancel = (Button)view.findViewById(R.id.cancel_bind);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO if user clicked the box, never show again, or show every time
                //jump to info show page
            }
        });
    }
    public void userBind(){

    }
    public void setBindHandler(Handler handler){
        BindHandler = handler;
    }
}
