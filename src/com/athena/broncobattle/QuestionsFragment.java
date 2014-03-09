package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionsFragment extends Fragment implements JsonEventListener<Object>, ImageEventListener {

	int correctAnswerRadioButtonId = 0;
	boolean isSubmit = false;
	private Question currentQuestion;
	private boolean isInitial = true;
	private final int UPDATE_ANIMATION_TIME = 3500;
	private final int UPDATE_ANIMATION_INTERVAL = 20;
	boolean noMoreQuestions = true;

	private static final String FIRST_QUESTION = "firstQuestion";
	private static final String NEW_QUESTION = "newQuestion";
	private static final String SAVE_ANSWER = "saveQuestion";

	private Typeface experienceTypeface = null;
	private MediaPlayer mPlayer;
	private SoundPool sp;
	private int soundId;
	ViewPropertyAnimator viewPAn=null;
		Animation _translateAnimation;

	// Question
	private View questionLoading;
	private LinearLayout questionLayout;
	private Button submitAnswerButton;
	private RadioGroup answers;
	private TextView questionTextView;
	private ImageView imageView;

	// Experience overlay
	private RelativeLayout experienceOverlay;
	private TextView experienceTextView;
	private AchievementView achievementView;
	private TextView achievementTitle, achievementDescription;
	private LinearLayout achievementLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.questions_fragment_layout, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		// Get references and hide everything until a question is retrieved
		questionLoading = (LinearLayout) getView().findViewById(R.id.question_loading);
		questionLayout = (LinearLayout) getView().findViewById(R.id.question_layout);
		submitAnswerButton = (Button) view.findViewById(R.id.submit_answer_button);
		submitAnswerButton.setText("Next");
		answers = (RadioGroup) getView().findViewById(R.id.answers);
		answers.setVisibility(RadioGroup.GONE);
		questionTextView = (TextView) getView().findViewById(R.id.question);
		questionTextView.setText("");
		imageView = (ImageView) getView().findViewById(R.id.imageView1);
		imageView.setVisibility(View.GONE);

		experienceOverlay = (RelativeLayout) getView().findViewById(R.id.hidden_view);
		achievementView = ((AchievementView) getView().findViewById(R.id.achievement_dialog_icon));
		achievementTitle = ((TextView) getView().findViewById(R.id.achievement_dialog_name));
		achievementDescription = ((TextView) getView().findViewById(R.id.achievement_dialog_description));
		achievementLayout = ((LinearLayout) getView().findViewById(R.id.achievement_layout));

		// Prepare the typography for the experience view
		experienceTypeface = Typeface.createFromAsset(getView().getContext().getAssets(), "fonts/American Captain.ttf");
		experienceTextView = (TextView) getView().findViewById(R.id.hidden_experience);
		experienceTextView.setTypeface(experienceTypeface);
		experienceTextView.setText("+" + 0 + " XP");
		experienceTextView = (TextView) getView().findViewById(R.id.hidden_value);
		experienceTextView.setTypeface(experienceTypeface);

		// Initialize sound
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundId = sp.load(view.getContext(), R.raw.coin, 1);
		// mPlayer = MediaPlayer.create(getView().getContext(), R.raw.coin);

		isInitial = true;
		noMoreQuestions = false;

		QuestionController.getInstance(view.getContext()).getNextQuestion(this, NEW_QUESTION);

		submitAnswerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isSubmit) {
					isSubmit = false;
					submitAnswer(v);
				} else {
					if (noMoreQuestions) {
						showNoMoreQuestions();
						// QuestionController.getInstance(getView().getContext()).getNextQuestion(QuestionsFragment.this,
						// NEW_QUESTION);
						// toastSomething("Sorry, there are no more questions.");
					} else {
						isSubmit = true;
						changeQuestion(v);
					}
				}
			}
		});

		// Click to hide the overlay
		experienceOverlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				countdownTimer.cancel();
				if(viewPAn!=null)
					viewPAn.cancel();
				
				achievementLayout.animate().cancel();

				experienceOverlay.animate().setDuration(200).setListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						experienceOverlay.setVisibility(RelativeLayout.GONE);
						experienceOverlay.setAlpha(1);
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						experienceOverlay.setVisibility(RelativeLayout.GONE);
						experienceOverlay.setAlpha(1);
					}
				}).alpha(0);
			}
		});

		// Selecting a radio enables submit
		answers.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				submitAnswerButton.setEnabled(true);
				submitAnswerButton.setVisibility(Button.VISIBLE);
			}
		});

		super.onViewCreated(view, savedInstanceState);
	}

	protected void changeQuestion(View v) {
		if (answers.getCheckedRadioButtonId() != -1) {
			initializeQuestion(v);
		}
	}

	private void initializeQuestion(View v) {
		ImageView imageView = (ImageView) getView().findViewById(R.id.imageView1);
		ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.gravatar_load_spinner);
		ImageDownloader downloader = new ImageDownloader(imageView, progressBar);
		downloader.execute(currentQuestion.image);
		downloader.setImageEventListener(this);
		



		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		TextView question = (TextView) getView().findViewById(R.id.question);
		question.setText(currentQuestion.question);

		answers.clearCheck();

		for (int i = 0; i < answers.getChildCount(); i++) {
			View obj = answers.getChildAt(i);
			if (obj instanceof RadioButton) {
				RadioButton button = (RadioButton) obj;
				((RadioButton) answers.getChildAt(i)).setText(currentQuestion.answers.get(i));
				button.setEnabled(true);
				button.setTextColor(Color.WHITE);
			}
		}

		// Button nextQuestion = (Button)
		// getView().findViewById(R.id.next_question_button);
		// nextQuestion.setEnabled(false);

		Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
		submitAnswerButton.setText("Submit");
		isSubmit = true;
	}

	private void showNoMoreQuestions() {
		hideProgressShowQuestion();
		answers.setVisibility(RadioGroup.GONE);
		questionTextView.setText(getString(R.string.question_text));
		imageView.setImageResource(R.drawable.sorry);
		imageView.setVisibility(View.VISIBLE);
		submitAnswerButton.setText("Retry");
		submitAnswerButton.setEnabled(true);
	}

	private void showConnectionError() {
		hideProgressShowQuestion();
		answers.setVisibility(RadioGroup.GONE);
		questionTextView.setText(getString(R.string.error_fetching_question));
		imageView.setImageResource(R.drawable.sorry);
		imageView.setVisibility(View.VISIBLE);
		submitAnswerButton.setText("Retry");
		submitAnswerButton.setEnabled(true);
	}

	private void hideProgressShowQuestion() {
		questionLoading.setVisibility(View.GONE);
		questionLayout.setAlpha(0);
		questionLayout.setVisibility(View.VISIBLE);
		questionLayout.animate().setDuration(500).alpha(1);
	}
	
	@Override
	public void onImageLoaded(ImageView imageView) {
		
//		int imageWidth = imageView.getMaxWidth();
//		int imageHeight = imageView.getMaxHeight();
//		
//		ScrollView scroll = ((ScrollView) getView().findViewById(R.id.scroll_view));
//		
//		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
//		int width = params.width;
//		int height = params.height;
		
//		int scrollWidth = .getMeasuredWidth();
//		int scrollHeight = ((ScrollView) getView().findViewById(R.id.scroll_view)).getMeasuredHeight();
		
		_translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.ABSOLUTE, -((float) (Math.random()*300)));
		_translateAnimation.setDuration(8000);
		_translateAnimation.setRepeatCount(-1);
		_translateAnimation.setRepeatMode(Animation.REVERSE); // REVERSE
		_translateAnimation.setInterpolator(new LinearInterpolator());
		imageView.startAnimation(_translateAnimation);
	}

	@Override
	public void onJsonFinished(Object object, String type) {

		if (type.equals(NEW_QUESTION)) {

			try {
				JSONObject job = null;
				// IF not null
				if (object != null)
					job = new JSONObject((String) object);
				// IF no more questions
				else {
					showConnectionError();
					return;
				}

				if (!job.isNull("server_message")) {
					showNoMoreQuestions();
					return;
				}

				answers.setVisibility(RadioGroup.VISIBLE);
				noMoreQuestions = false;

				submitAnswerButton.setEnabled(true);
				submitAnswerButton.setVisibility(Button.VISIBLE);

				// Convert the incoming object to a Question
				currentQuestion = new Question(new JSONObject((String) object));

				correctAnswerRadioButtonId = ((RadioButton) answers.getChildAt(currentQuestion.correctAnswerIndex)).getId();

				hideProgressShowQuestion();

				if (isInitial) {
					initializeQuestion(getView());
					isInitial = false;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (type.equals(SAVE_ANSWER)) {

			try {
				Achievement a = new Achievement(new JSONObject((String) object), getView().getContext());

				achievementLayout.setVisibility(View.GONE);
				achievementLayout.setAlpha(0);
				achievementLayout.setScaleX(2);
				achievementLayout.setScaleY(2);
				achievementLayout.setRotationY(100);
				// achievementLayout.setRotation(1000);

				achievementView.setText(a.icon);
				achievementView.setColor(a.color);
				achievementView.initializeCanvas();
				achievementView.invalidate();

				achievementTitle.setText(a.name);
				achievementDescription.setText(a.description);
				achievementLayout.setVisibility(View.VISIBLE);
				achievementView.setVisibility(View.VISIBLE);
				viewPAn=achievementLayout.animate().setDuration(1000).scaleX(1).scaleY(1).rotationY(0).alpha(1);//.rotation(0);

				/**
				 * Bitmap b = Bitmap.createBitmap(achievementLayout.getWidth(),
				 * achievementLayout.getHeight(), Bitmap.Config.ARGB_8888);
				 * Canvas c = new Canvas(b); achievementLayout.draw(c);
				 * 
				 * BitmapDrawable bw = new BitmapDrawable(getResources(), b);
				 * 
				 * ImageView iv = new ImageView(getView().getContext());
				 * iv.setBackground(bw);
				 * 
				 * LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
				 * achievementLayout.addView(iv, params);
				 * iv.setMinimumWidth(100); iv.setMinimumHeight(100);
				 * imageView.animate().scaleX(3).scaleY(3); /
				 **/

			} catch (JSONException e) {
				e.printStackTrace();
			}

			QuestionController.getInstance(getView().getContext()).getNextQuestion(this, NEW_QUESTION);
		}
	}

	private void submitAnswer(View v) {
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);

		if (answers.getCheckedRadioButtonId() != -1) {
			submitAnswerButton.setText("Next");
			submitAnswerButton.setEnabled(false);
			submitAnswerButton.setVisibility(Button.INVISIBLE);

			int selectedAnswer = answers.getCheckedRadioButtonId();

			if (selectedAnswer != correctAnswerRadioButtonId) {
				((RadioButton) getView().findViewById(selectedAnswer)).setTextColor(Color.RED);
			} else {
				displayCorrect();
			}

			((RadioButton) getView().findViewById(correctAnswerRadioButtonId)).setTextColor(Color.GREEN);

			int selectedIndex = -1;
			for (int i = 0; i < answers.getChildCount(); i++) {
				RadioButton answer = ((RadioButton) answers.getChildAt(i));
				if (answer.getId() == selectedAnswer) {
					selectedIndex = i;
				}
				answer.setEnabled(false);
			}

			QuestionController.getInstance(v.getContext()).questionAnswered(currentQuestion, selectedIndex, this, SAVE_ANSWER);

			// Button nextQuestionButton = (Button)
			// getView().findViewById(R.id.next_question_button);
			// nextQuestionButton.setEnabled(true);

		}
	}

	public void toastSomething(String toastString) {
		Context context = getView().getContext();
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, toastString, duration);
		toast.show();
	}
	
	static final int[]color={Color.BLUE,Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
	CountDownTimer countdownTimer=null;
	int currentTick=0;
	private void displayCorrect(){
		achievementView.setVisibility(View.GONE);
		achievementTitle.setText("");
		achievementDescription.setText("");
		LinearLayout tempAch=(LinearLayout)getView().findViewById(R.id.achievement_layout);  
		tempAch.setVisibility(LinearLayout.GONE);
		
 		RelativeLayout layout=(RelativeLayout)getView().findViewById(R.id.hidden_view);
 		layout.setVisibility(RelativeLayout.VISIBLE);
		
		
		UserController.getInstance(getView().getContext()).currentUser.experience+=currentQuestion.experience;
		currentTick = 0;
 		countdownTimer = new CountDownTimer(UPDATE_ANIMATION_TIME, UPDATE_ANIMATION_INTERVAL) {
 			long experience=0;
			long experienceIncrement=3*currentQuestion.experience/(UPDATE_ANIMATION_TIME/UPDATE_ANIMATION_INTERVAL);
			long maxExperience=currentQuestion.experience;
			
		     public void onTick(long millisUntilFinished) {
		    	 currentTick++;
		 		if(experience<maxExperience){
		 			experience+=experienceIncrement;
		 			TextView expView = (TextView) getView().findViewById(R.id.hidden_experience);
		 			expView.setText("+"+experience+" XP");
		 			
		 			//TODO improve sound
		 			if(millisUntilFinished%4==0){
			 			sp.play(soundId,  (float)Math.random(), (float)Math.random(), 1, 0, 1);
			 		}
		 			
		 		}
		 		else{
		 			TextView expView = (TextView) getView().findViewById(R.id.hidden_experience);
		 			expView.setText("+"+maxExperience+" XP");
		 		}
		     }

		     public void onFinish() {

		    	achievementLayout.animate().setDuration(200).x(400).alpha(0);
		    	experienceOverlay.animate().setDuration(500).setListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						experienceOverlay.setVisibility(RelativeLayout.GONE);
						experienceOverlay.setAlpha(1);
						achievementLayout.setX(0);
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						experienceOverlay.setVisibility(RelativeLayout.GONE);
						experienceOverlay.setAlpha(1);
						achievementLayout.setX(0);
					}
				}).alpha(0);
		     }
		  }.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (countdownTimer != null) {
			countdownTimer.cancel();
			if(viewPAn!=null)
				viewPAn.cancel();
		}
	}

	@Override
	public void onStop() {
		super.onPause();
		if (countdownTimer != null) {
			countdownTimer.cancel();
			if(viewPAn!=null)
				viewPAn.cancel();
		}
	}

	@Override
	public void onDestroyView() {
		super.onPause();
		if (countdownTimer != null) {
			countdownTimer.cancel();
			if(viewPAn!=null)
				viewPAn.cancel();
		}
	}


}
