package com.athena.broncobattle;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AchievementController {
	
	private static AchievementController sAchievement;
//	Question currentQuestion;
	Context mContext;
//	private final String READ_QUESTION = "question_for_user";
//	private final String QUESTION_ANSWERED = "question_answered";
	private AthenaJsonReader jsonReader;
	private AthenaJsonWriter jsonWriter;
//	
	private AchievementController(Context context){
		mContext = context;
		jsonReader = new AthenaJsonReader(mContext);
		jsonWriter = new AthenaJsonWriter(mContext);
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
			jsonReader.execute(new String[]{READ_QUESTION,UserController.getInstance(mContext).currentUser.id});
		}catch(Exception e){
			Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.error_fetching_question), Toast.LENGTH_SHORT);
			toast.show();
		}
	}
//	
//	public void questionAnswered(Question q, int selectedAnswer, JsonEventListener listener, String type){
//		try{
//			
//			jsonWriter = new AthenaJsonWriter(mContext);
//			
//			jsonWriter.addJsonEventListener(listener,type);
//			
//			jsonWriter.addNamedParameter("user_id", UserController.getInstance(mContext).currentUser.id);
//			jsonWriter.addNamedParameter("question_id", q.id+"");
//			jsonWriter.addNamedParameter("answer", selectedAnswer+"");
//			jsonWriter.addNamedParameter("correct", (q.correctAnswerIndex == selectedAnswer)?"1":"0");
//			jsonWriter.execute(new String[]{QUESTION_ANSWERED});
//		}catch(Exception e){
//			Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.error_answering_question), Toast.LENGTH_SHORT);
//			toast.show();
//			Log.e("questionAnswered",e.toString());
//			e.printStackTrace();
//		}
//	}

}
