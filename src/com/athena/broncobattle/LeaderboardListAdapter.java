package com.athena.broncobattle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LeaderboardListAdapter extends ArrayAdapter<User> {

	Context mContext;
	ArrayList<User> users = new ArrayList<User>();

	public LeaderboardListAdapter(Context context, int resource,
			int textViewResourceId, List<User> objects) {
		super(context, resource, textViewResourceId);
		mContext = context;
		for (int i = 0; i < objects.size(); i++) {
			users.add(objects.get(i));
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.leaderboard_list_item, parent,
					false);
		}
		User user = users.get(position);

		TextView nameTextView = (TextView) rowView.findViewById(R.id.user_name);
		nameTextView.setText(user.name);

		TextView experienceTextView = (TextView) rowView
				.findViewById(R.id.level);
		experienceTextView.setText("Experience " + user.experience);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		ProgressBar progressBar = (ProgressBar) rowView
				.findViewById(R.id.gravatar_load_spinner);
		ImageDownloader downloader = new ImageDownloader(imageView, progressBar);
		downloader.execute(user.avatar);
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
