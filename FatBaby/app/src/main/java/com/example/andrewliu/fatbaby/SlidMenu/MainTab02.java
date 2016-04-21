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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.DataBase.FoodCalorieDBHandle;
import com.example.andrewliu.fatbaby.R;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTab02 extends Fragment
{
	private ListView mList;
	private View messageLayout;
	private CircleRefreshLayout mRefreshLayout;
	private Button mStop;
	private FoodCalorieDBHandle foodCalorieDBHandle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		messageLayout = inflater.inflate(R.layout.main_tab_02, container, false);

		mRefreshLayout = (CircleRefreshLayout) messageLayout.findViewById(R.id.refresh_tab02);
		mList = (ListView) messageLayout.findViewById(R.id.list);
		mStop = (Button) messageLayout.findViewById(R.id.stop_refresh);
		String[] strs = {
				"The",
				"Canvas",
				"class",
				"holds",
				"the",
				"draw",
				"calls",
				".",
				"To",
				"draw",
				"something,",
				"you",
				"need",
				"4 basic",
				"components",
				"Bitmap",
		};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strs);
		mList.setAdapter(adapter);
		mStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editText = (EditText)messageLayout.findViewById(R.id.foodDBtext);
				foodCalorieDBHandle.findByName(editText.getText().toString());
				mRefreshLayout.finishRefreshing();
			}
		});
		mRefreshLayout.setOnRefreshListener(
				new CircleRefreshLayout.OnCircleRefreshListener() {
					@Override
					public void refreshing() {
						// do something when refresh starts
					}

					@Override
					public void completeRefresh() {
						// do something when refresh complete
					}
				});
		//init DB
		foodCalorieDBHandle = new FoodCalorieDBHandle(getContext());
//		foodCalorieDBHandle.add("apple",100,100,1,"slack");
//		foodCalorieDBHandle.add("banana",110,110,1,"slack");
//		foodCalorieDBHandle.add("hamberger",120,130,1,"main course");
//		foodCalorieDBHandle.add("pork",150,200,1,"main course");
//		foodCalorieDBHandle.add("beaf",200,300,1,"main course");
		fileRead();
		foodCalorieDBHandle.setFoodHandler(mt2Handler);
		return messageLayout;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_main,menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public Handler mt2Handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.e("mmmmmmmmmmmmmmmmmmm"," "+msg.obj);
		}
	};

	public void fileRead(){
		foodCalorieDBHandle.deleteDB(getContext());
		try {
			InputStreamReader inputReader = new InputStreamReader( getResources().openRawResource(R.raw.food));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line="";
			while((line = bufReader.readLine()) != null) {
//				Log.e("aaaaaa","aaaaaaaaa"+line);
				String [] temp = null;
				line = line.trim();
				temp = line.split("\\s+");
//				for(String each:temp) {
//					Log.e("-----",""+each);
//				}
				if(temp.length != 3){
					continue;
				}
				Log.e("==========",temp[0]+"-"+temp[1]+"-"+temp[2]);
				Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
				Matcher m2 = p.matcher(temp[2]);
				Matcher m3 = p.matcher(temp[1]);
				Integer cal=0,wei=0;
				if(m3.find()) {
					cal = Integer.parseInt(m3.group());
				}
				if(m2.find()) {
					wei = Integer.parseInt(m2.group());
				}
//				while ( m.find() ) {
//					System.out.println( m.group() );
//				}
//				Log.e("aaaaaaaa",temp[0]+"-"+cal+"-"+ wei);
				foodCalorieDBHandle.add(temp[0], cal, wei, 1, "主食");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
