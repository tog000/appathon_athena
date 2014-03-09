package com.athena.broncobattle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UsersFactory {

	public static void parseUsers(ArrayList<User> users, String usersJson, Context context) {

		users.clear();
		
		try {
			JSONArray jsonArray = new JSONArray((String) usersJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				users.add(new User(jsonArray.getJSONObject(i), context));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
