package com.athena.broncobattle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatsFragment extends ListFragment implements JsonEventListener<Object> {

	int nextLevelThreshold = 0;
	long experiencePoints = 0;
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

		experiencePoints = user.experience;

		AchievementController.getInstance(view.getContext()).getAchievements(this, GET_ACHIEVEMENT);
		
		
		level = calculateLevel();

		TextView levelText = (TextView) view.findViewById(R.id.level_title);
		levelText.setText("Level " + level);


	};

	private boolean listInitialized = false;
	
	@Override
	public void onJsonFinished(Object object, String type) {
		if (type.equals(GET_ACHIEVEMENT)) {
			
			try {
				
				JSONArray ja = new JSONArray((String)object);
			
				if(!listInitialized){
					
					achievements = new ArrayList<Achievement>(); 
					
					for(int i=0;i<ja.length();i++){
						achievements.add(new Achievement(ja.getJSONObject(i), getView().getContext())); 
					}
					
					ArrayAdapter<Achievement> adapter = new StatsListAdapter(getActivity().getApplicationContext(), 0, 0, achievements);
					((ListView) getView().findViewById(R.id.stats_list)).setAdapter(adapter);
					
					listInitialized = true;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		timer.start();
	}

	private int calculateLevel() {
		int rval = 0;

		for (int loopLevel = 1;; loopLevel++) {
			nextLevelThreshold = (int) (experienceFuntionX * Math.pow(loopLevel, experienceFunctionY));
			if (nextLevelThreshold < experiencePoints) {
				rval = loopLevel;
				experiencePoints -= nextLevelThreshold;
				continue;
			}
			break;
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
	}
}
