package com.athena.broncobattle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ProgressBar;

public class AchievementFragment extends DialogFragment {

	private Achievement achievement = null;
	private CountDownTimer timer;
	int timerTime = 3000;
	int TIMER_REFRESH = 60; // 60 Hz

	/** The system calls this only when creating the layout in a dialog. */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// The only reason you might override this method when using
		// onCreateView() is
		// to modify any dialog characteristics. For example, the dialog
		// includes a
		// title by default, but your custom layout might not need it. So here
		// you can
		// remove the dialog title, but you must call the superclass to get the
		// Dialog.
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setView(inflater.inflate(R.layout.dialog_fragment, null))
		// Add action buttons
				.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						AchievementFragment.this.close();
					}
				});

		timer = new CountDownTimer(timerTime, timerTime / TIMER_REFRESH) {

			public void onTick(long millisUntilFinished) {
				updateBar(millisUntilFinished);
			}

			public void onFinish() {
				updateBar(0f);
				close();
			}
		};
		timer.start();

		return builder.create();

	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onPause();
	}


	// @Override
	// public void onViewCreated(View view, Bundle savedInstanceState) {
	//
	// // TextView levelText = (TextView) view.findViewById(R.id.level_title);
	// // levelText.setText("Level " + level);
	// //
	// // TextView levelText = (TextView) view.findViewById(R.id.level_title);
	// // levelText.setText("Level " + level);
	//
	// achievement = new Achievement("id1", "f006","ffffff", "Foo Bar",
	// "asdfasdfasdfasd");
	//
	//
	// ((AchievementView)
	// view.findViewById(R.id.achievement_dialog_icon)).setText(achievement.icon);
	// }

	public void close() {
		timer.cancel();
		ProgressBar p = (ProgressBar) this.getDialog().findViewById(R.id.progressBar1);
		p.setProgress(0);
		this.getDialog().cancel();
	}

	public void updateBar(double progress) {
		ProgressBar p = (ProgressBar) this.getDialog().findViewById(R.id.progressBar1);
		p.setProgress((int) ((progress / timerTime) * 100));
	}
}
