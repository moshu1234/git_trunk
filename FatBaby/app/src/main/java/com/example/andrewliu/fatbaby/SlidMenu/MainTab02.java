package com.example.andrewliu.fatbaby.SlidMenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.DataBase.FoodCalorieDBHandle;
import com.example.andrewliu.fatbaby.R;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTab02 extends Fragment
{
	private View messageLayout;
	private Handler mt2Handler;
	private ListView lv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		messageLayout = inflater.inflate(R.layout.main_tab_02, container, false);
		initList();
		return messageLayout;
	}
	public void setHandler(Handler handler){
		mt2Handler = handler;
	}
	public void initList(){
		lv = (ListView)messageLayout.findViewById(R.id.list_mt2);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
		for(int i=0;i<10;i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("setting_pic", R.drawable.hds);//加入图片
			map.put("setting_title", "用户名");
			map.put("setting_arrow", R.drawable.right);
			listItem.add(map);
		}
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(getContext(),listItem,//需要绑定的数据
				R.layout.setting_item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
				 new String[] {"setting_pic","setting_title", "setting_arrow"},
			new int[] {R.id.setting_pic,R.id.setting_title,R.id.setting_arrow}
			);
		lv.setAdapter(mSimpleAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				myToast("你点击了第"+position+"行"+id);//设置标题栏显示点击的行
			}
		});
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			Message message = new Message();
			message.what = 2;
			message.obj = "bottom2";
			mt2Handler.sendMessage(message);
		} else if (!isVisibleToUser) {
			Log.e("===========","bu shi ke jian d");
		}
	}
	public void myToast(String s){
		Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
	}
}
