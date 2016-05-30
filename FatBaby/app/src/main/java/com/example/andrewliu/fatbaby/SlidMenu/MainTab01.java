package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.BodyCirleShow.BodyProgress;
import com.example.andrewliu.fatbaby.DataBase.FitnessInfoDB;
import com.example.andrewliu.fatbaby.DataBase.UserInfoDB;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.progressbar.CircleProgressBar;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainTab01 extends Fragment
{
	private View view;
	//CircleProgressBar pb1,pb2,pb3,pb4,pb5;
	List<CircleProgressBar> progressBars = new ArrayList<>();
	BodyProgress mBodyProgress;
	private int running, fitness;
	private int weight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view =  inflater.inflate(R.layout.main_tab_01, container, false);
		test();
		initProgress(view);
		init_weight_tab();
		return view;
	}
	public void setProgressBarProgress(CircleProgressBar pb,int progress,String text){
		pb.updateProgressText(progress, text);
	}
	public void setBodyProgress(BodyProgress bp, int progress, String text){
		bp.updateProgressText(progress, text);
	}
	public void initProgress(View view){
		CircleProgressBar pb1=(CircleProgressBar)view.findViewById(R.id.roundProgressBar1);
		CircleProgressBar pb2=(CircleProgressBar)view.findViewById(R.id.roundProgressBar2);
		CircleProgressBar pb3=(CircleProgressBar)view.findViewById(R.id.roundProgressBar3);
		CircleProgressBar pb4=(CircleProgressBar)view.findViewById(R.id.roundProgressBar4);
		CircleProgressBar pb5=(CircleProgressBar)view.findViewById(R.id.roundProgressBar5);
		progressBars.add(pb1);
		progressBars.add(pb2);
		progressBars.add(pb3);
		progressBars.add(pb4);
		progressBars.add(pb5);
		getFitnessRecords();
		mBodyProgress=(BodyProgress)view.findViewById(R.id.bodyProgress);
//		setBodyProgress(mBodyProgress,60,"加油哦");
	}
	public void init_weight_tab(){
		int weight[]={60,0,62,80,90,10,78,90};
		int i=0;
		for(i=0;i<weight.length;i++){
			set_weight_display(weight[i],i);
		}
	}
	public void set_weight_display(int weight,int i){
		ImageView iv;
		TextView tv;
		switch (i){
			case 1:
				iv = (ImageView)view.findViewById(R.id.day1_i);
				tv = (TextView)view.findViewById(R.id.day1_t);
				break;
			case 2:
				iv = (ImageView)view.findViewById(R.id.day2_i);
				tv = (TextView)view.findViewById(R.id.day2_t);
				break;
			case 3:
				iv = (ImageView)view.findViewById(R.id.day3_i);
				tv = (TextView)view.findViewById(R.id.day3_t);
				break;
			case 4:
				iv = (ImageView)view.findViewById(R.id.day4_i);
				tv = (TextView)view.findViewById(R.id.day4_t);
				break;
			case 5:
				iv = (ImageView)view.findViewById(R.id.day5_i);
				tv = (TextView)view.findViewById(R.id.day5_t);
				break;
			case 6:
				iv = (ImageView)view.findViewById(R.id.day6_i);
				tv = (TextView)view.findViewById(R.id.day6_t);
				break;
			case 7:
				iv = (ImageView)view.findViewById(R.id.day7_i);
				tv = (TextView)view.findViewById(R.id.day7_t);
				break;
			default:
				iv = (ImageView)view.findViewById(R.id.day7_i);
				tv = (TextView)view.findViewById(R.id.day7_t);
				break;
		}
		//set image height
		ViewGroup.LayoutParams para;
		para = iv.getLayoutParams();
		para.height=weight;
		iv.setLayoutParams(para);

		tv.setText(String.valueOf(weight));
	}
	public String getCurrentDate(){
		Calendar calendar =Calendar.getInstance();
		int year=calendar.get(calendar.YEAR);
		int month=calendar.get(calendar.MONTH);
		int day=calendar.get(calendar.DATE);
		int week=calendar.get(calendar.DAY_OF_WEEK);
		return ""+year+month+day+week;
	}
	public String getLastDate(int last){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,last);
		Date date = new Date();
		date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	public Integer getCurrentWeek(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(calendar.DAY_OF_WEEK);
	}
	public void getFitnessRecords(){
		Log.e("getFitnessRecords","start to get getFitnessRecords :"+progressBars.size());

		FitnessInfoDB fitnessInfoDB = new FitnessInfoDB(getContext());
		for(int i=0;i<progressBars.size();i++){
			final String date = getLastDate(0-i-1);
			final int finalI = i;
			Log.e("getFitnessRecords","date:"+date+":"+i);
			fitnessInfoDB.find(date, new FitnessInfoDB.FitnessCallback() {
				@Override
				public void Sucess(JSONObject s) {
					try {
						Log.e("Maintab01",""+s);
						TextView textView = new TextView(getContext());
						setProgressBarProgress(progressBars.get(finalI),s.getInt("progress"),s.getString("progress")+"%");
						switch (finalI) {
							case 0:
								textView = (TextView) view.findViewById(R.id.pb_day1);
								break;
							case 1:
								textView = (TextView) view.findViewById(R.id.pb_day2);
								break;
							case 2:
								textView = (TextView) view.findViewById(R.id.pb_day3);
								break;
							case 3:
								textView = (TextView) view.findViewById(R.id.pb_day4);
								break;
							case 4:
								textView = (TextView) view.findViewById(R.id.pb_day5);
								break;
							default:
								break;
						}
						if(textView != null){
							textView.setText(date.substring(4));
						}
					}catch (Exception e){
						Log.e("MainTab01","exception:"+e.getMessage());
					}
				}

				@Override
				public void fail(String s) {
					Log.e("MainTab01","this day has no record");
					try {
						setProgressBarProgress(progressBars.get(finalI),0,date);
					}catch (Exception e){
						Log.e("MainTab01","exception:"+e.getMessage());
					}
				}
			});
		}

	}
	public void test(){
		FitnessInfoDB fitnessInfoDB = new FitnessInfoDB(getContext());
//		fitnessInfoDB.delete("20160523");
		Log.e("aaaaa",""+getLastDate(0));
		if(fitnessInfoDB.find(getLastDate(0)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(0),80,10,30,60,0);
		}
		Log.e("aaaaa",""+getLastDate(-1));
		if(fitnessInfoDB.find(getLastDate(-1)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-1),70,6,30,59,0);
		}
		Log.e("aaaaa",""+getLastDate(-2));
		if(fitnessInfoDB.find(getLastDate(-2)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-2),0,0,0,60,0);
		}
		Log.e("aaaaa",""+getLastDate(-3));
		if(fitnessInfoDB.find(getLastDate(-3)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-3),100,20,60,60,0);
		}
		Log.e("aaaaa",""+getLastDate(-4));
		if(fitnessInfoDB.find(getLastDate(-4)) == false){
			Log.e("aaaaa","add"+getLastDate(-4));
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-4),80,5,60,55,0);
		}
		Log.e("aaaaa",""+getLastDate(-5));
		if(fitnessInfoDB.find(getLastDate(-5)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-5),80,10,30,60,0);
		}
		Log.e("aaaaa",""+getLastDate(-6));
		if(fitnessInfoDB.find(getLastDate(-6)) == false){
			fitnessInfoDB.insert_fitnessinfo(getLastDate(-6),80,10,30,60,0);
		}
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(0),80,10,30,60,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-1),70,6,30,59,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-2),85,15,30,58,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-3),0,0,0,60,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-4),100,20,60,60,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-5),80,5,60,55,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-6),80,10,30,60,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-7),80,10,30,60,0);
//		fitnessInfoDB.insert_fitnessinfo(getLastDate(-8),80,10,30,60,0);
	}
}
