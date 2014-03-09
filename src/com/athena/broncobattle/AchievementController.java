package com.athena.broncobattle;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AchievementController {
	
	private static AchievementController sAchievement;
//	Question currentQuestion;
	Context mContext;
	private final String GET_ACHIEVEMENTS = "achievements";
//	private final String QUESTION_ANSWERED = "question_answered";
	private AthenaJsonReader jsonReader;
	private AthenaJsonWriter jsonWriter;
//	
	private AchievementController(Context context){
		mContext = context;
	}
	
	public static AchievementController getInstance(Context context){
		if (sAchievement == null){
			sAchievement = new AchievementController(context); 
		}
		return sAchievement;
	}
//	
//	public static AchievementController get(Context context){
//		if (sActiveQuestion == null){
//			sActiveQuestion = new AchievementController(context);
//		}
//		return sActiveQuestion;
//	}
//	
	public void getAchievements(JsonEventListener<?> listener, String type){
		try{
			jsonReader = new AthenaJsonReader(mContext);
			jsonReader.addJsonEventListener(listener,type);
			jsonReader.execute(new String[]{GET_ACHIEVEMENTS,UserController.getInstance(mContext).currentUser.id});
		}catch(Exception e){
			Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.error_fetching_achievements), Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
