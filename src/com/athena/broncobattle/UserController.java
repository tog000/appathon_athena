package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class UserController {
	
	private static UserController sActiveUser;
	public User currentUser;
	Context mContext;
	AthenaJsonReader jsonReader;
	AthenaJsonWriter jsonWriter;
	String userWriteRequest = "userWrite";
	String userReadRequest = "userRead";
	
	private UserController(Context context){
		mContext = context;
		jsonReader = new AthenaJsonReader(context);
		jsonWriter = new AthenaJsonWriter(context);
		currentUser = new User("A", "B", "C", 0);
	}
	
	public static UserController get(Context context){
		if (sActiveUser == null){
			sActiveUser = new UserController(context);
		}
		return sActiveUser;
	}
	
	public void answeredQuestionCorrectly(int questionID){
		JSONObject correctAnswerUser = new JSONObject();
		try {
			correctAnswerUser.put(User.JSON_ID, currentUser.id);
			correctAnswerUser.put(Question.JSON_QUESTION_ID, questionID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		jsonWriter.execute(new String[]{userWriteRequest, "1"});//correctAnswerUser.toString()
	}

	public void userLoggedIn(String userName){
		readActiveUser(userName);
	}
	
	public void readActiveUser(String userName){
		jsonReader.execute(new String[]{userReadRequest, userName});
	}
}
