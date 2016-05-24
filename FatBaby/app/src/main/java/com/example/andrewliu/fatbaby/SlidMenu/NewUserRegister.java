package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BmobDataLib.UserInfo;
import com.example.andrewliu.fatbaby.MainActivity;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.infoShow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liut1 on 5/24/16.
 */
public class NewUserRegister extends Fragment {
    private View view;
    private Handler registerHandler;
    private String LogTitle = "USERREGISTER";
    public interface ICoallBack{
        public void onCallSucess(String s);
        public void onCallToast(String s);
    }
    private Integer max_age = 70;
    private String[] gender_s;
    private String[] age_s;
    private Spinner gender_sp;
    private Spinner age_sp;
    private ArrayAdapter<String> gender_adp;
    private ArrayAdapter<String> age_adp;
    private String objid = new String();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.user_register, container, false);
        init_gender_spinner();
        init_age_spinner();
        confirmRegister();
        return view;
    }
    public void init_gender_spinner(){
        gender_s = getResources().getStringArray(R.array.gender);
        gender_sp = (Spinner)view.findViewById(R.id.spinner_gender);
        gender_adp = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,gender_s);
        //设置下拉列表的风格
        gender_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_sp.setAdapter(gender_adp);
        //添加事件Spinner事件监听
        gender_sp.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        gender_sp.setVisibility(View.VISIBLE);
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

        age_sp = (Spinner)view.findViewById(R.id.spinner_age);
        age_adp = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,age_list);
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
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void isUserExisted(final ICoallBack iCoallBack){
        EditText et;
        et = (EditText) view.findViewById(R.id.user_register);
        final String user = et.getText().toString();
        if(TextUtils.isEmpty(user)){
            iCoallBack.onCallToast("用户名不能为空");
            return;
        }
        et = (EditText) view.findViewById(R.id.password_register);
        if(TextUtils.isEmpty(et.getText().toString())){
            iCoallBack.onCallToast("密码不能为空");
            return;
        }
        BmobQuery query = new BmobQuery("UserInfo");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray arg0) {
                //注意：查询的结果是JSONArray,需要自行解析
                Log.e(LogTitle,"查询成功:"+arg0.length()+":"+arg0.toString());
                Integer  i = 0;
                for(i=0;i<arg0.length();i++){
                    try{
                        JSONObject object=arg0.getJSONObject(i);
                        String user_r = object.getString("user");
                        Log.e(LogTitle,"uer:"+object.getString("user"));
                        if(user_r!=null && user_r.equals(user)){
                            Log.e(LogTitle,"user existed:"+user_r);
                            iCoallBack.onCallToast("用户名已存在，请重新输入");
                            return;
                        }else {
                            Log.e(LogTitle,"keep finding:"+user);
                        }
                    }catch (Exception e){
                        Log.e(LogTitle,"exception");
                    }
                }
                iCoallBack.onCallToast("注册成功！");
                iCoallBack.onCallSucess("");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.e(LogTitle,"没有userinfo tab，直接创建:"+arg1);
                iCoallBack.onCallToast("注册成功！");
                iCoallBack.onCallSucess("");
            }
        });
    }
    public void saveRegisterInfo(){
        EditText et;
        UserInfo userInfo = new UserInfo();

        et = (EditText) view.findViewById(R.id.user_register);
        userInfo.setUser(et.getText().toString());
        et = (EditText) view.findViewById(R.id.password_register);
        userInfo.setPassword(et.getText().toString());
        et = (EditText) view.findViewById(R.id.name_register);
        userInfo.setName(et.getText().toString());
        et = (EditText) view.findViewById(R.id.height_register);
        if(TextUtils.isEmpty(et.getText()) == false) {
            userInfo.setHeight(Integer.valueOf(et.getText().toString()));
        }
        et = (EditText) view.findViewById(R.id.weight_register);
        if(TextUtils.isEmpty(et.getText()) == false) {
            userInfo.setWeight(Integer.valueOf(et.getText().toString()));
        }
        et = (EditText) view.findViewById(R.id.email_register);
        userInfo.setEmail(et.getText().toString());
        et = (EditText) view.findViewById(R.id.profession_register);
        userInfo.setProfession(et.getText().toString());
        et = (EditText) view.findViewById(R.id.hobby_register);
        userInfo.setHobby(et.getText().toString());
//        userInfo.addUser(getContext(),userInfo);
        userInfo.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                Message message = new Message();
                message.what = 3;
                message.obj = objid;
                registerHandler.sendMessage(message);
            }

            @Override
            public void onFailure(int i, String s) {
                myToast("创建用户失败"+s);
            }
        });

        objid = userInfo.getObjectId();
    }
    public void confirmRegister(){
        Button button = (Button)view.findViewById(R.id.register_confirm);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isUserExisted(new ICoallBack() {
                    @Override
                    public void onCallSucess(String s) {
                        saveRegisterInfo();
                    }

                    @Override
                    public void onCallToast(String s) {
                        myToast(s);
                    }
                });
            }
        });
    }
    public void setRegisterHandler(Handler handler){
        registerHandler = handler;
    }
}
