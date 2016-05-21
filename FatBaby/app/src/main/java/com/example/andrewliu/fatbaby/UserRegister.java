package com.example.andrewliu.fatbaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.DataBase.UserInfoDB;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class UserRegister extends AppCompatActivity {
    private Integer max_age = 70;
    private String[] gender_s;
    private String[] age_s;
    private Spinner gender_sp;
    private Spinner age_sp;
    private ArrayAdapter<String> gender_adp;
    private ArrayAdapter<String> age_adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        init_gender_spinner();
//        init_age_spinner();
        init_gender_spinner1();
        confirmRegister();
    }
    public void init_gender_spinner(){
        gender_s = getResources().getStringArray(R.array.gender);
        gender_sp = (Spinner)findViewById(R.id.spinner_gender);
        gender_adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gender_s);
        //设置下拉列表的风格
        gender_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_sp.setAdapter(gender_adp);
        //添加事件Spinner事件监听
        gender_sp.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        gender_sp.setVisibility(View.VISIBLE);
    }
    public void init_gender_spinner1(){
        age_s = getResources().getStringArray(R.array.gender);
        age_sp = (Spinner)findViewById(R.id.spinner_age);
        age_adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,age_s);
        //设置下拉列表的风格
        age_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_sp.setAdapter(age_adp);
        //添加事件Spinner事件监听
        age_sp.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        age_sp.setVisibility(View.VISIBLE);
    }
    public void init_age_spinner(){
//        age_s = getResources().getStringArray(R.array.gender);
        max_age = Integer.valueOf(getResources().getString(R.string.max_age));
        List<String> age_list = new ArrayList<>();
//        Log.e("aaa",""+max_age);
//        age_s = new String[max_age-10];
        for(int i=10;i<max_age-10;i++){
            age_list.add(String.valueOf(i));
        }
        String ss[] = {"10","11","12","13"};

        age_sp = (Spinner)findViewById(R.id.spinner_age);
        age_adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,age_list);
        //设置下拉列表的风格
        age_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_sp.setAdapter(age_adp);
        //添加事件Spinner事件监听
        age_sp.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        gender_sp.setVisibility(View.VISIBLE);
    }
    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//            view.setText("你的血型是："+m[arg2]);
            //TODO here need to save gender and age
            myToast("spinner clicked");
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void myToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public Boolean saveRegisterInfo(){
        EditText et;
        et = (EditText) findViewById(R.id.user_register);
        if(et.getText().equals("")){
            myToast("用户名或密码不能为空");
            return false;
        }
        et = (EditText) findViewById(R.id.password_register);
        if(et.getText().equals("")){
            myToast("用户名或密码不能为空");
            return false;
        }
        UserInfo userInfo = new UserInfo();

        et = (EditText) findViewById(R.id.user_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.password_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.name_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.height_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.weight_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.email_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.profession_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) findViewById(R.id.hobby_register);
        userInfo.setUser(et.getText().toString());
        userInfo.addUser(this,userInfo);

        return true;
    }
    public void confirmRegister(){
        Button button = (Button)findViewById(R.id.register_confirm);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(saveRegisterInfo()) {
                    Intent intent = new Intent();
                    intent.setClass(UserRegister.this, infoShow.class);
                    UserRegister.this.startActivity(intent);
                }
            }
        });
    }
}
