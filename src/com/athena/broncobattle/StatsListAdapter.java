package com.athena.broncobattle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatsListAdapter extends ArrayAdapter<Achievement> {

	Context mContext;
	ArrayList<Achievement> achievements = new ArrayList<Achievement>();

	public StatsListAdapter(Context context, int resource, int textViewResourceId, List<Achievement> objects) {
		super(context, resource, textViewResourceId);
		mContext = context;
		for (int i = 0; i < objects.size(); i++) {
			achievements.add(objects.get(i));
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.stats_list_item, parent, false);

		Achievement achievement = achievements.get(position);

		AchievementView achView = (AchievementView) rowView.findViewById(R.id.achievement_icon);
		achView.setColor(achievement.color);
		achView.setText(achievement.icon);
		
		TextView nameTextView = (TextView) rowView.findViewById(R.id.achievement_name);
		nameTextView.setText(achievement.name);

		TextView experienceTextView = (TextView) rowView.findViewById(R.id.achievement_description);
		experienceTextView.setText(achievement.description);

		// ImageView imageView = (ImageView)
		// rowView.findViewById(R.id.achievement_icon);
		// ImageDownloader downloader = new ImageDownloader(imageView);
		// downloader.execute(achievement.icon);

		return rowView;
	}

	@Override
	public int getCount() {
		return achievements.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Achievement getItem(int position) {
		return achievements.get(position);
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
