package com.example.andrewliu.fatbaby.SlidMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class MainTab01 extends Fragment
{
	private View view;
	CircleProgressBar pb1,pb2,pb3,pb4,pb5;
	BodyProgress mBodyProgress;
	private int running, fitness;
	private int weight;
	private UserInfoDB userInfoDB;
	private FitnessInfoDB fitnessInfoDB;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view =  inflater.inflate(R.layout.main_tab_01, container, false);
		initProgress(view);
		init_userinfo(getContext());
		init_fitnessinfo(getContext());
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
		pb1=(CircleProgressBar)view.findViewById(R.id.roundProgressBar1);
		setProgressBarProgress(pb1,30,"day 1");
		pb2=(CircleProgressBar)view.findViewById(R.id.roundProgressBar2);
		setProgressBarProgress(pb2,40,"day 2");
		pb3=(CircleProgressBar)view.findViewById(R.id.roundProgressBar3);
		setProgressBarProgress(pb3,50,"day 3");
		pb4=(CircleProgressBar)view.findViewById(R.id.roundProgressBar4);
		setProgressBarProgress(pb4,60,"day 4");
		pb5 = (CircleProgressBar)view.findViewById(R.id.roundProgressBar5);
		setProgressBarProgress(pb5,70,"day 5");

		mBodyProgress=(BodyProgress)view.findViewById(R.id.bodyProgress);
//		setBodyProgress(mBodyProgress,60,"加油哦");
	}
	public void init_userinfo(Context context){
		userInfoDB = new UserInfoDB(context);
	}
	public void init_fitnessinfo(Context context){
		fitnessInfoDB = new FitnessInfoDB(context);
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
}
