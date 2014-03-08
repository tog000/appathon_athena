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
		jsonReader = new AthenaJsonReader(mContext);
		
	}
	
	public static QuestionController get(){
		if (sActiveQuestion == null){
			sActiveQuestion = new QuestionController(null); 
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
		jsonReader.execute(new String[]{questionReadRequest});
	}

}
