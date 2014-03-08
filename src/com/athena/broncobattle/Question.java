package com.athena.broncobattle;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Question {
	String question;
	int experience;
	String image;
	LinkedList<String> answers;
	int correctAnswerIndex;
	
	public static final String JSON_QUESTION_ID = "question_id";
	public static final String JSON_QUESTION = "question";
	public static final String JSON_EXPERIENCE = "experience";
	public static final String JSON_IMAGE = "image";
	public static final String JSON_ANSWERS = "answers";
	public static final String JSON_CORRECT = "correct";
	
	public Question(String question, int experience, String image, LinkedList<String> answers, int correntAnswerIndex){
		this.question = question;
		this.experience = experience;
		this.image= image;
		this.answers = answers;
		this.correctAnswerIndex = correntAnswerIndex;
	}
	
	public Question(JSONObject json, Context context){
        try {
        	question = json.getString(JSON_QUESTION);
        	experience = json.getInt(JSON_EXPERIENCE);
        	image = json.getString(JSON_IMAGE);
        	correctAnswerIndex = json.getInt(JSON_CORRECT);
        	JSONArray answerJSON = json.getJSONArray(JSON_ANSWERS);
        	answers = new LinkedList<String>();
        	for (int i=0; i<answerJSON.length(); i++){   	
        		answers.add(answerJSON.get(i).toString());
        	}
        } catch (JSONException e) {
            e.printStackTrace();
        }
}

}
