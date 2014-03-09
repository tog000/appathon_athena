package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

public class UserController implements JsonEventListener {
	
	private static UserController instance;
	public User currentUser;
	Context mContext;
	
	private final static String REGISTER_USER = "register_user"; 
	
	AthenaJsonReader jsonReader;
	AthenaJsonWriter jsonWriter;
	
	private UserController(Context context){
		mContext = context;
		
		String android_id = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
		
		String username = Util.getEmail(context);
		
		String[] usernameParts = Util.getEmail(context).split("@");
		if(usernameParts.length==2){
			username=usernameParts[0];
		}
		
		currentUser = new User(android_id, username, "http://www.gravatar.com/avatar/"+android_id+"?d=retro&f=y&s=400", 0);
	}
	
	public static UserController getInstance(Context context){
		if (instance == null){
			instance = new UserController(context);
		}
		return instance;
	}
	
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	public void answeredQuestionCorrectly(int questionID){
		JSONObject correctAnswerUser = new JSONObject();
		try {
			correctAnswerUser.put(User.JSON_ID, currentUser.id);
			correctAnswerUser.put(Question.JSON_QUESTION_ID, questionID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//jsonWriter.execute(new String[]{userWriteRequest, "1"});//correctAnswerUser.toString()
	}
	
	

	public void registerCurrentUser(){
		try{
			
			jsonWriter = new AthenaJsonWriter(mContext);
			
			jsonWriter.addJsonEventListener(this, REGISTER_USER);
			
			jsonWriter.addNamedParameter("user_id", currentUser.id);
			jsonWriter.addNamedParameter("name", currentUser.name+"");
			jsonWriter.addNamedParameter("avatar", currentUser.avatar+"");
			
			jsonWriter.execute(new String[]{REGISTER_USER,currentUser.id});
			
		}catch(Exception e){
			Toast toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT);
			toast.show();
			e.printStackTrace();
		}
	}

	@Override
	public void onJsonFinished(Object object, String eventType) {
		if(object!=null){
			try {
				JSONObject user = new JSONObject((String)object);
				
				currentUser.experience = user.getInt("experience");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
