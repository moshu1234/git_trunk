package com.example.andrewliu.fatbaby.UI.SlidMenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.R;

import java.util.ArrayList;
import java.util.HashMap;

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
	public void fillSettingList(ArrayList<HashMap<String, Object>> list){
		//username
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("setting_pic", R.drawable.gsl);//加入图片
		map.put("setting_title", "用户名");
		map.put("setting_arrow", R.drawable.fhe);
		list.add(map);
		//nickname
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("setting_pic", R.drawable.hdr);//加入图片
		map1.put("setting_title", "昵称");
		map1.put("setting_arrow", R.drawable.fhe);
		list.add(map1);
		//password
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("setting_pic", R.drawable.grc);//加入图片
		map2.put("setting_title", "密码设置");
		map2.put("setting_arrow", R.drawable.fhe);
		list.add(map2);
		//password
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("setting_pic", R.drawable.lez);//加入图片
		map3.put("setting_title", "消息设置");
		map3.put("setting_arrow", R.drawable.fhe);
		list.add(map3);
		//password
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("setting_pic", R.drawable.lnl);//加入图片
		map4.put("setting_title", "隐私设置");
		map4.put("setting_arrow", R.drawable.fhe);
		list.add(map4);
	}
	public void initList(){
		lv = (ListView)messageLayout.findViewById(R.id.list_mt2);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
//		for(int i=0;i<10;i++)
//		{
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("setting_pic", R.drawable.hds);//加入图片
//			map.put("setting_title", "用户名");
//			map.put("setting_arrow", R.drawable.fhe);
//			listItem.add(map);
//		}
		fillSettingList(listItem);
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
