package com.athena.broncobattle;

import java.util.Arrays;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LeaderboardFragment extends  ListFragment {
	
	ArrayAdapter<User> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.leaderboard_fragment_layout, container, false);
		User[] users = new User[]{
				new User("id1", "Doctor", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id2", "Spock", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id3", "Tim", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id4", "Amit", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id5", "Elena", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id1", "Doctor", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id2", "Spock", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id3", "Tim", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id4", "Amit", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id5", "Elena", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id1", "Doctor", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id2", "Spock", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id3", "Tim", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id4", "Amit", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000),
				new User("id5", "Elena", "http://www.gravatar.com/avatar/9032743fd412323m4b23?d=retro&f=y", 10000)
				
		};
		    adapter = new LeaderboardListAdapter(getActivity().getApplicationContext(), 0, 0, Arrays.asList(users));
		    
		    //view.setEnabled(false);
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
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
}
