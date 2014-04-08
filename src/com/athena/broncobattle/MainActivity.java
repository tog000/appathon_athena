/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.athena.broncobattle;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	// private String[] mPlanetTitles;
	private String[] mDrawerButtonTitles;

	private Fragment[] fragments;

	public static final String HOST = "http://162.243.93.212/codeigniter/";

	private final int NUM_FRAGMENTS = 3;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private TypedArray navMenuIcons;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mDrawerButtonTitles = getResources().getStringArray(R.array.drawer_array);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		User currentUser = UserController.getInstance(getApplicationContext()).getCurrentUser();
		View header = View.inflate(this, R.layout.drawer_header_layout, null);
		((TextView) header.findViewById(R.id.drawer_user_name)).setText(currentUser.name);
		header.findViewById(R.id.drawer_header_icon);
		mDrawerList.addHeaderView(header);
		ImageDownloader downloader = new ImageDownloader((ImageView) header.findViewById(R.id.drawer_header_icon), null);
		downloader.execute(currentUser.avatar);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(mDrawerButtonTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(mDrawerButtonTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(mDrawerButtonTitles[2], navMenuIcons.getResourceId(2, -1)));

		fragments = new Fragment[NUM_FRAGMENTS];

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
		
		
		TextView name = (TextView)findViewById(R.id.drawer_user_name);
		name.setText(Util.getEmail(getApplicationContext()));
		
		UserController.getInstance(getApplicationContext()).registerCurrentUser();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

//		// Handle action buttons
//		switch (item.getItemId()) {
//		case R.id.action_websearch:
//			/*
//			 * // create intent to perform web search for this planet Intent
//			 * intent = new Intent(Intent.ACTION_WEB_SEARCH);
//			 * intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//			 * // catch event that there's no activity to handle intent if
//			 * (intent.resolveActivity(getPackageManager()) != null) {
//			 * startActivity(intent); } else { Toast.makeText(this,
//			 * R.string.app_not_available, Toast.LENGTH_LONG).show(); }
//			 */
////			DialogFragment dialog = new AchievementFragment();
////			FragmentManager fragmentManager = getFragmentManager();
////
////			dialog.show(fragmentManager, "dialog");
//			return true;
//		default:
			return super.onOptionsItemSelected(item);
//		}

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (((ListView) parent).getHeaderViewsCount() > 0) {
				position--;
			}
			selectItem(position);
		}
	}

	int numMessages = 0;

	private void selectItem(int position) {
		// update the main content by replacing fragments

		Log.i("selectItem", "New item selected: " + position);

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// Sets an ID for the notification, so it can be updated
		int notifyID = 1;
		Builder mNotifyBuilder = new NotificationCompat.Builder(this).setContentTitle("New Message").setContentText("You've received new messages.")
				.setSmallIcon(android.R.drawable.ic_menu_compass);

		mNotifyBuilder.setContentText("Some text!").setNumber(++numMessages);
		// Because the ID remains unchanged, the existing notification is
		// updated.
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());

		if (!(position < 0)) {
			if (fragments[position] == null) {
				switch (position) {
				case 0:
					fragments[position] = new QuestionsFragment();
					break;
				case 1:
					fragments[position] = new LeaderboardFragment();
					break;
				case 2:
					fragments[position] = new StatsFragment();
					break;
				}
			}

			// Fragment fragment = new PlanetFragment();
			// Bundle args = new Bundle();
			// args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
			// fragment.setArguments(args);

			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragments[position]).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position + 1, true);
			setTitle(mDrawerButtonTitles[position]);
			// setTitle(mPlanetTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
