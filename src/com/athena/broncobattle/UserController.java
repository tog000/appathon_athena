package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.Settings.Secure;

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
		
		String android_id = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
		
		currentUser = new User(android_id, "PLACEHOLDER", "http://www.gravatar.com/avatar/"+android_id+"?d=retro&f=y", 0);
	}
	
	public static UserController getInstance(Context context){
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
