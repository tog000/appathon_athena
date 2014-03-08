package com.athena.broncobattle;

import android.content.Context;

public class QuestionController {
	
	private static QuestionController sActiveQuestion;
	Question currentQuestion;
	Context mContext;
	String questionReadRequest = "question_for_user";
	AthenaJsonReader jsonReader;
	
	private QuestionController(Context context){
		mContext = context;
		jsonReader = new AthenaJsonReader(context);
	}
	
	public static QuestionController get(Context context){
		if (sActiveQuestion == null){
			return new QuestionController(context);
		}
		return sActiveQuestion;
	}
	
	public void getNextQuestion(){
		jsonReader.execute(new String[]{questionReadRequest,"1"});
	}

}
