package com.athena.broncobattle;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LeaderboardFragment extends  ListFragment implements JsonEventListener<String> {
	
	ArrayList<User> users = new ArrayList<User>();
	
	ArrayAdapter<User> adapter;

	AthenaJsonReader reader;

	private final String VALUES_BUNDLE_KEY = "listValues";
	
	private final String GET_USERS = "get_scoreboard";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(savedInstanceState != null){
			Log.w("onCreate","bundle contains it? "+savedInstanceState.containsKey(VALUES_BUNDLE_KEY));
		}else{
			Log.w("onCreate","bundle=null");
		}
		
		if(savedInstanceState != null && savedInstanceState.containsKey(VALUES_BUNDLE_KEY) && savedInstanceState.get(VALUES_BUNDLE_KEY)!=null){
			users = savedInstanceState.getParcelableArrayList(VALUES_BUNDLE_KEY);
			Log.w("onCreate","ALREADY HAD IT!!");
		}else if(users.size()==0){
			reader = new AthenaJsonReader(getActivity().getApplicationContext());
			reader.addJsonEventListener(this, GET_USERS);
			reader.execute(new String[]{GET_USERS});
			Log.w("onCreate","Firing away (JSON)!");
		}
		
		adapter = new LeaderboardListAdapter(getActivity().getApplicationContext(), 0, 0, users);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.leaderboard_fragment_layout, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		//super.onViewCreated(view, savedInstanceState);
		((ListView)view.findViewById(R.id.leaderboard_list)).setAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(VALUES_BUNDLE_KEY, users);
		Log.w("onSaveInstanceState","Saving list to bundle!");
	}

	@Override
	public void onJsonFinished(String object, String eventType) {
		
		Log.w("onJsonFinished","JSON finished!");
		
		UsersFactory.parseUsers(users, object, getActivity().getApplicationContext());
		((ListView)getView().findViewById(R.id.leaderboard_list)).setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		((View)getView().findViewById(R.id.leaderbord_loading)).setVisibility(View.GONE);
		ListView list = (ListView)getView().findViewById(R.id.leaderboard_list);
		list.setAlpha(0);
		list.setVisibility(View.VISIBLE);
		list.animate().setDuration(500).alpha(1);
		
	}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
}
