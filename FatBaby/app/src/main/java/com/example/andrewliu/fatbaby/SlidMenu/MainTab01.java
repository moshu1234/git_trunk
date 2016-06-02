package com.example.andrewliu.fatbaby.SlidMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BabyView.gifView;
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
	private gifView gif_run,gif_dance,gif_fitness;
	private AlertDialog.Builder alertbBuilder;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view =  inflater.inflate(R.layout.main_tab_01, container, false);
		test();
		initProgress(view);
//		init_weight_tab();
		testGif();
		setStartSports();
		return view;
	}
	public void setStartSports(){
		ImageButton ib = (ImageButton)view.findViewById(R.id.start_run);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(gif_fitness.isPaused()==false || gif_dance.isPaused() == false){
					Toast.makeText(getContext(), "请先暂停其他运动", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("maintab01","Start sports now!");
				if(gif_run.isPaused()) {
					gif_run.setPaused(false);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("STOP");
				}else {
					gif_run.setPaused(true);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("START");
				}
			}
		});
		ib = (ImageButton)view.findViewById(R.id.start_dance);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(gif_fitness.isPaused()==false || gif_run.isPaused() == false){
					Toast.makeText(getContext(), "请先暂停其他运动", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("maintab01","Start sports now!");
				if(gif_dance.isPaused()) {
					gif_dance.setPaused(false);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("STOP");
				}else {
					gif_dance.setPaused(true);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("START");
				}
			}
		});
		ib = (ImageButton)view.findViewById(R.id.start_fitness);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(gif_run.isPaused()==false || gif_dance.isPaused() == false){
					Toast.makeText(getContext(), "请先暂停其他运动", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("maintab01","Start sports now!");
				if(gif_fitness.isPaused()) {
					gif_fitness.setPaused(false);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("STOP");
				}else {
					gif_fitness.setPaused(true);
					TextView tv = (TextView) view.findViewById(R.id.start_t);
					tv.setText("START");
				}
			}
		});
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
	public String getLastWeek(int last){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,last);
		Date date = new Date();
		date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("E");
		return format.format(date);
	}
	public void setWeightToday(){
		FitnessInfoDB fitnessInfoDB = new FitnessInfoDB(getContext());
		fitnessInfoDB.find(getLastDate(0), new FitnessInfoDB.FitnessCallback() {
			@Override
			public void Sucess(JSONObject s) {
				try {
					Log.e("setWeightToday","success:"+s);
					TextView tv = (TextView)view.findViewById(R.id.day6_t);
					ImageView iv = (ImageView)view.findViewById(R.id.day6_i);
					//set weight tab
					ViewGroup.LayoutParams para;
					para = iv.getLayoutParams();
					para.height=s.getInt("weight")*para.height/100;
//						if(para.height > 180){
//							para.height = para.height - 20;
//						}
					iv.setLayoutParams(para);
					tv.setText(s.getString("weight")+"kg");
				}catch (Exception e){
					Log.e("setWeightToday",e.getMessage());
				}
			}

			@Override
			public void fail(String s) {
				Log.e("setWeightToday","failed");
			}
		});
	}
	public void historyInfoShow(){
		CircleProgressBar pb= (CircleProgressBar)view.findViewById(R.id.roundProgressBar1);
		pb.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e("historyInfoShow","waht a fuck:"+event.getAction());
				if(event.getAction() == MotionEvent.ACTION_UP) {
					final EditText et = new EditText(getContext());
					new AlertDialog.Builder(getContext()).setTitle("体重").setView(
							et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							myToast("" + et.getText().toString());
						}
					})
							.setNegativeButton("取消", null).show();
				}


				return false;
			}
		});
		pb = (CircleProgressBar)view.findViewById(R.id.roundProgressBar2);
		pb.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					final Dialog dialog = new Dialog(getContext());
					dialog.setContentView(R.layout.dialog_view);
					Button bt = (Button)dialog.findViewById(R.id.diag_sure);
					final EditText et = (EditText)dialog.findViewById(R.id.diag_text);
					bt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							myToast(et.getText().toString());
						}
					});
					dialog.show();
				}
				return false;
			}
		});

	}
	public void manualUpdateWeight(){
		ImageView iv = new ImageView(getContext());
		iv = (ImageView)view.findViewById(R.id.day6_i);
		iv.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					final Dialog dialog = new Dialog(getContext());
					dialog.setContentView(R.layout.dialog_view);
					Button bt = (Button)dialog.findViewById(R.id.diag_sure);
					final EditText et = (EditText)dialog.findViewById(R.id.diag_text);
					bt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							myToast(et.getText().toString());
						}
					});
					dialog.show();
				}
				return true;
			}
		});
//		Button bt = (Button) view.findViewById(R.id.diag_sure);
//		bt.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				EditText et = (EditText) view.findViewById(R.id.diag_text);
//				myToast(et.getText().toString());
//			}
//		});
	}
	public void getFitnessRecords(){
		historyInfoShow();
		manualUpdateWeight();
		setWeightToday();
		Log.e("getFitnessRecords","start to get getFitnessRecords :"+progressBars.size());

		FitnessInfoDB fitnessInfoDB = new FitnessInfoDB(getContext());
		for(int i=0;i<progressBars.size();i++){
			final String date = getLastDate(0-i-1);
			final String week_s = getLastWeek(0-i-1);
			final int finalI = i;
			Log.e("getFitnessRecords","date:"+date+":"+i+":week:"+week_s);
			fitnessInfoDB.find(date, new FitnessInfoDB.FitnessCallback() {
				@Override
				public void Sucess(JSONObject s) {
					try {
						Log.e("Maintab01",""+s);
						TextView textView = new TextView(getContext());
						ImageView iv = new ImageView(getContext());
						TextView tv = new TextView(getContext());
						TextView week = new TextView(getContext());
						if(finalI < progressBars.size()) {
							setProgressBarProgress(progressBars.get(progressBars.size() - finalI - 1), s.getInt("progress"), s.getInt("progress") + "%");
						}
						switch (finalI) {
							case 4:
								textView = (TextView) view.findViewById(R.id.pb_day1);
								iv = (ImageView)view.findViewById(R.id.day1_i);
								tv = (TextView)view.findViewById(R.id.day1_t);
								week = (TextView)view.findViewById(R.id.week1_t);
								break;
							case 3:
								textView = (TextView) view.findViewById(R.id.pb_day2);
								iv = (ImageView)view.findViewById(R.id.day2_i);
								tv = (TextView)view.findViewById(R.id.day2_t);
								week = (TextView)view.findViewById(R.id.week2_t);
								break;
							case 2:
								textView = (TextView) view.findViewById(R.id.pb_day3);
								iv = (ImageView)view.findViewById(R.id.day3_i);
								tv = (TextView)view.findViewById(R.id.day3_t);
								week = (TextView)view.findViewById(R.id.week3_t);
								break;
							case 1:
								textView = (TextView) view.findViewById(R.id.pb_day4);
								iv = (ImageView)view.findViewById(R.id.day4_i);
								tv = (TextView)view.findViewById(R.id.day4_t);
								week = (TextView)view.findViewById(R.id.week4_t);
								break;
							case 0:
								textView = (TextView) view.findViewById(R.id.pb_day5);
								iv = (ImageView)view.findViewById(R.id.day5_i);
								tv = (TextView)view.findViewById(R.id.day5_t);
								week = (TextView)view.findViewById(R.id.week5_t);
								break;
							default:
								break;
						}
						if(textView != null){
							textView.setText(date.substring(4));
						}
						//set weight tab
						ViewGroup.LayoutParams para;
						para = iv.getLayoutParams();
						para.height=s.getInt("weight")*para.height/100;
//						if(para.height > 180){
//							para.height = para.height - 20;
//						}
						iv.setLayoutParams(para);
						tv.setText(s.getString("weight")+"kg");

						week.setText(week_s);
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
	public void testGif(){
		gif_run = (gifView)view.findViewById(R.id.gif_run);
		gif_run.setMovieResource(R.drawable.run);
		gif_run.setPaused(true);

		gif_dance = (gifView)view.findViewById(R.id.gif_dance);
		gif_dance.setMovieResource(R.drawable.dance);
		gif_dance.setPaused(true);

		gif_fitness = (gifView)view.findViewById(R.id.gif_fitness);
		gif_fitness.setMovieResource(R.drawable.fitness);
		gif_fitness.setPaused(true);
		TextView tv = (TextView)view.findViewById(R.id.start_t);
		tv.setText("START");
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
	public void myToast(String s){
		Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
	}
}
