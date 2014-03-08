package com.athena.broncobattle;

import android.content.Context;

public class QuestionController {
	
	private static QuestionController sActiveQuestion;
	Question currentQuestion;
	Context mContext;
	String questionReadRequest = "questionRead";
	AthenaJsonReader jsonReader;
	
	private QuestionController(Context context){
		mContext = context;
		
	}
	
	public static QuestionController get(Context context){
		if (sActiveQuestion == null){
			return new QuestionController(context);
		}
		return sActiveQuestion;
	}
	
	public void setReader(AthenaJsonReader reader){
		this.jsonReader = reader;
	}
	
	public void getNextQuestion(){
		jsonReader.doInBackground(new String[]{questionReadRequest});
	}

}
