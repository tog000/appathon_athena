package com.athena.broncobattle;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class StatsFragment extends ListFragment implements JsonEventListener {

	int nextLevelThreshold = 0;
	long totalExperiencePoints = 200;
	int experiencePoints = 0;
	int level = 0;

	private CountDownTimer timer;

	int timerTime = 750;
	int TIMER_REFRESH = 60; // 60 Hz

	private final int experienceFuntionX = 100;
	private final double experienceFunctionY = 1.2;
	
	private static final String GET_ACHIEVEMENT = "getAchievement";
	
	ArrayList<Achievement> achievements;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stats_fragment_layout, container, false);

		timer = new CountDownTimer(timerTime, timerTime / TIMER_REFRESH) {

			public void onTick(long millisUntilFinished) {
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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		User user = UserController.getInstance(getView().getContext()).currentUser;
		
		totalExperiencePoints = user.experience;
		
		AchievementController.getInstance(view.getContext()).getAchievements(this, GET_ACHIEVEMENT);
		
		level = calculateLevel();

		TextView levelText = (TextView) view.findViewById(R.id.level_title);
		levelText.setText("Level " + level);

		ArrayAdapter<Achievement> adapter = new StatsListAdapter(getActivity().getApplicationContext(), 0, 0, achievements);

		((ListView) getView().findViewById(R.id.stats_list)).setAdapter(adapter);
	};
	
	@Override
	public void onJsonFinished(Object object, String type) {
		if(type.equals(GET_ACHIEVEMENT)){
			Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
			submitAnswerButton.setEnabled(true);
			
			achievements = (ArrayList<Achievement>) object;
			
			
			
		}
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		timer.start();
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
				experiencePoints = (int) (totalExperiencePoints - previousLevelThreshold);
				break;
			}
		}

		return rval;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onPause();
	}

	public void updateBar(double progress) {
		ProgressBar p = (ProgressBar) getView().findViewById(R.id.experience_bar);
		if (progress == 0) {
			p.setProgress((int) (100 * (experiencePoints / (double) nextLevelThreshold)));
		} else {
			p.setProgress((int) ((100 * (experiencePoints / (double) nextLevelThreshold)) * (1 - (progress / timerTime))));
		}
	}

	public void close() {
		timer.cancel();
		// ProgressBar p = (ProgressBar)
		// getView().findViewById(R.id.experience_bar);
		// p.setProgress(experiencePoints / nextLevelThreshold);
	}
}
