package com.athena.broncobattle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

public class StatsFragment extends ListFragment implements JsonEventListener<Object> {

	int nextLevelThreshold = 0;
	long experiencePoints = 0;
	int level = 0;

	private CountDownTimer timer;

	int timerTime = 750;
	int TIMER_REFRESH = 60; // 60 Hz



	private static final String GET_ACHIEVEMENT = "getAchievement";
	private static final String BUNDLE_ACHIEVEMENTS = "bundleAchievement";

	ArrayList<Achievement> achievements;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		
		achievements = new ArrayList<Achievement>();
		
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

		
//		Toast toast = Toast.makeText(getView().getContext(), "VIEW CREATED", Toast.LENGTH_SHORT);
//		toast.show();
		
		User user = UserController.getInstance(getView().getContext()).currentUser;

		experiencePoints = user.experience;

		if(savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_ACHIEVEMENTS)){
			
			achievements = (ArrayList<Achievement>) savedInstanceState.getSerializable(BUNDLE_ACHIEVEMENTS);
			
//			Toast toast2 = Toast.makeText(getView().getContext(), "RESTORED!", Toast.LENGTH_SHORT);
//			toast2.show();
			
		}else{
			AchievementController.getInstance(getView().getContext()).getAchievements(this, GET_ACHIEVEMENT);
		}
		
		level = calculateLevel();

		TextView levelText = (TextView) view.findViewById(R.id.level_title);
		levelText.setText("Level " + level);
	};

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_ACHIEVEMENTS, achievements);
//		Toast toast = Toast.makeText(getView().getContext(), "SAVED!", Toast.LENGTH_SHORT);
//		toast.show();
	}
	
	@Override
	public void onJsonFinished(Object object, String type) {
		if (type.equals(GET_ACHIEVEMENT)) {
			
			try {
				
				JSONArray ja = new JSONArray((String)object);
				
				for(int i=0;i<ja.length();i++){
					achievements.add(new Achievement(ja.getJSONObject(i), getView().getContext())); 
				}
				
				ArrayAdapter<Achievement> adapter = new StatsListAdapter(getActivity().getApplicationContext(), 0, 0, achievements);
				((ListView) getView().findViewById(R.id.stats_list)).setAdapter(adapter);
				
				((View)getView().findViewById(R.id.stats_achievements_loading)).setVisibility(View.GONE);
				ListView list = (ListView)getView().findViewById(R.id.stats_list);
				list.setAlpha(0);
				list.setVisibility(View.VISIBLE);
				list.animate().setDuration(500).alpha(1);
				
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		
		
		
		timer.start();
	}

	private int calculateLevel() {
		int rval = 0;

		for (int loopLevel = 1;; loopLevel++) {
			nextLevelThreshold = (int) (User.experienceFuntionX * Math.pow(loopLevel, User.experienceFunctionY));
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
