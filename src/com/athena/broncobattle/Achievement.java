/**
 * 
 */
package com.athena.broncobattle;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * @author Athena
 *
 */
public class Achievement implements Serializable{
	private static final long serialVersionUID = 123L;
	String id;
	String icon;
	String name;
	String description;
	String color;
	
	public static final String JSON_ID = "id";
	public static final String JSON_ICON = "icon";
	public static final String JSON_COLOR = "color";
	public static final String JSON_NAME = "name";
	public static final String JSON_DESCRIPTION = "description";
	
	public Achievement (String id, String icon, String color, String name, String description){
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.color = color;
		this.description = description;
	}
	
	public Achievement(JSONObject json, Context context){
	        try {
	        	id = json.getString(JSON_ID);	
	        	icon = json.getString(JSON_ICON);
	        	color = json.getString(JSON_COLOR);
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
			json.put(JSON_COLOR, color);
			json.put(JSON_NAME, name);
			json.put(JSON_DESCRIPTION, description);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
