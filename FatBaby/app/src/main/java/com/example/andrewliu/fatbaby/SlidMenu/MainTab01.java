package com.example.andrewliu.fatbaby.SlidMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrewliu.fatbaby.BodyCirleShow.BodyProgress;
import com.example.andrewliu.fatbaby.R;
import com.example.andrewliu.fatbaby.progressbar.CircleProgressBar;

public class MainTab01 extends Fragment
{
	private View view;
	CircleProgressBar pb1,pb2,pb3,pb4,pb5;
	BodyProgress mBodyProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view =  inflater.inflate(R.layout.main_tab_01, container, false);
		initProgress(view);
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
		setBodyProgress(mBodyProgress,60,"加油哦");
	}
}
