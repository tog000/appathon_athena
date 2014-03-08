package com.athena.broncobattle;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatsFragment extends Fragment {

	int nextLevelThreshold = 0;
	int totalExperiencePoints = 184;
	int experiencePoints = 0;
	int level = 0;
	
	int timerTime = 5000;

	private final int experienceFuntionX = 100;
	private final double experienceFunctionY = 1.2;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stats_fragment_layout, container, false);

		ProgressBar experienceBar = (ProgressBar) view.findViewById(R.id.experience_bar);

		level = calculateLevel();
		
		TextView levelText = (TextView) view.findViewById(R.id.level_title);
		levelText.setText("Level " + level);

		
		timer =  new CountDownTimer(timerTime, 50) {
			
			public void onTick(long millisUntilFinished) {
				//updateBar(millisUntilFinished / (double)timerTime);
				updateBar(millisUntilFinished);
			}

			public void onFinish() {
				updateBar(0f);
				close();
			}
		};
		
		return view;
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		startTimeout();
	}

	private int calculateLevel() {
		int sumThreshold = 0;
		int rval = 0;

		for (int loopLevel = 1;; loopLevel++) {
			int previousLevelThreshold = (int) (experienceFuntionX * Math.pow(loopLevel - 1, experienceFunctionY));
			nextLevelThreshold = (int) (experienceFuntionX * Math.pow(loopLevel, experienceFunctionY)); 
			sumThreshold += nextLevelThreshold;
			if (sumThreshold > totalExperiencePoints) {
				rval = loopLevel - 1;
				experiencePoints = totalExperiencePoints - previousLevelThreshold;
				break;
			}
		}

		return rval;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		timer.cancel();
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onPause();
	}
	
	public void updateBar(double progress) {
		ProgressBar p = (ProgressBar) getView().findViewById(R.id.experience_bar);
		if(progress==0){
			p.setProgress(100);
		}else{
			p.setProgress(100-(int)((progress/timerTime)*100));
		}
		//Log.i("",(int) ((experiencePoints / nextLevelThreshold) * (100 - (progress/timerTime))));
	}

	public void close() {
		timer.cancel();
		//ProgressBar p = (ProgressBar) getView().findViewById(R.id.experience_bar);
		//p.setProgress(experiencePoints / nextLevelThreshold);
	}

	private CountDownTimer timer;

	public void startTimeout() {

		// final AchievementFragment instance = this;

		timer.cancel();
		timer.start();
	}
}
