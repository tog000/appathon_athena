/**
 * 
 */
package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * @author Athena
 *
 */
public class User {
	String id;
	String name;
	String avatar;
	long experience;
	
	public static final String JSON_ID = "id";
	public static final String JSON_NAME = "name";
	public static final String JSON_AVATAR = "avatar";
	public static final String JSON_EXPERIENCE = "experience";
	
	public User (String id, String name, String avatar, long experience){
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.experience = experience;
	}
	
	public User(JSONObject json, Context context){
	        try {
	        	id = json.getString(JSON_ID);
	        	name = json.getString(JSON_NAME);
	        	avatar = json.getString(JSON_AVATAR);
	        	experience = json.getLong(JSON_EXPERIENCE);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	}
	
	public String toJSON(){
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_ID, id);
			json.put(JSON_NAME, name);
			json.put(JSON_AVATAR, avatar);
			json.put(JSON_EXPERIENCE, experience);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
}