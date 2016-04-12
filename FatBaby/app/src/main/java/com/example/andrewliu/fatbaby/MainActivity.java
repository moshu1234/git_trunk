package com.example.andrewliu.fatbaby;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.drawable.actionbar);
//        actionBar.setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
        login();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void login(){
        Button b_login=(Button)findViewById(R.id.login);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,infoShow.class);
//                intent.setClass(MainActivity.this,ButtomTabView.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
