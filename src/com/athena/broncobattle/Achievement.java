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
public class Achievement {
	String id;
	String icon;
	String name;
	String description;
	
	public static final String JSON_ID = "id";
	public static final String JSON_ICON = "icon";
	public static final String JSON_NAME = "name";
	public static final String JSON_DESCRIPTION = "description";
	
	public Achievement (String id, String icon, String name, String description){
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.description = description;
	}
	
	public Achievement(JSONObject json, Context context){
	        try {
	        	id = json.getString(JSON_ID);
	        	icon = json.getString(JSON_ICON);
	        	name = json.getString(JSON_NAME);
	        	description = json.getString(JSON_DESCRIPTION);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	}
	
	public String toJSON(){
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_ID, id);
			json.put(JSON_ICON, icon);
			json.put(JSON_NAME, name);
			json.put(JSON_DESCRIPTION, description);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
