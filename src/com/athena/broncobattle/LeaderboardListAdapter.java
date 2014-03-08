package com.athena.broncobattle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaderboardListAdapter extends ArrayAdapter<User> {

	
	Context mContext;
	ArrayList<User> users = new ArrayList<User>();

	public LeaderboardListAdapter(Context context, int resource, int textViewResourceId, List<User> objects) {
		super(context, resource, textViewResourceId);
		mContext = context;
		for (int i = 0; i < objects.size(); i++) {
			users.add(objects.get(i));
		}
	}
	
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.leaderboard_list_item, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.user_name);
	    textView.setText(users.get(position).name);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    //TODO add image

	    return rowView;
	  }
	  
	    @Override
	    public int getCount() {
	        return users.size();
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public User getItem(int position) {
	        return users.get(position);
	    }
}
