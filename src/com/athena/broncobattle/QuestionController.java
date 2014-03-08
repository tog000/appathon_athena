package com.athena.broncobattle;

import android.content.Context;
import android.widget.Toast;

public class QuestionController {
	
	private static QuestionController sActiveQuestion;
	Question currentQuestion;
	Context mContext;
	String questionReadRequest = "question_for_user";
	AthenaJsonReader jsonReader;
	
	private QuestionController(Context context){
		mContext = context;
		jsonReader = new AthenaJsonReader(mContext);
	}
	
	public static QuestionController getInstance(Context context){
		if (sActiveQuestion == null){
			sActiveQuestion = new QuestionController(context); 
		}
		return sActiveQuestion;
	}
	
	public static QuestionController get(Context context){
		if (sActiveQuestion == null){
			sActiveQuestion = new QuestionController(context);
		}
		return sActiveQuestion;
	}
	
	public void addJsonEventListener(JsonEventListener listener){
		this.jsonReader.addJsonEventListener(listener);
	}
	
	public void getNextQuestion(){
		try{
			jsonReader.execute(new String[]{questionReadRequest,UserController.getInstance(mContext).currentUser.id});
		}catch(Exception e){
			Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.error_fetching_question), Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	public void questionAnswered(Question q, int selectedAnswer){
		
	}

}
