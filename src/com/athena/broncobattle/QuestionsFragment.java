package com.athena.broncobattle;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionsFragment extends Fragment implements JsonEventListener<Object>{

	int correctAnswer = 0;
	boolean isSubmit=false;
	private Question currentQuestion;
	private boolean isInitial=true;
	private final int UPDATE_ANIMATION_TIME=2500;
	private final int UPDATE_ANIMATION_INTERVAL=20;
	boolean noMoreQuestions=true;
	
	private static final String NEW_QUESTION= "newQuestion";
	private static final String SAVE_ANSWER = "saveQuestion";
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.questions_fragment_layout, container, false);

		return view;

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Button submitAnswerButton = (Button) view.findViewById(R.id.submit_answer_button);
		submitAnswerButton.setText("Next");
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		answers.setVisibility(RadioGroup.GONE);
		
		isInitial=true;
		QuestionController.getInstance(view.getContext()).getNextQuestion(this,NEW_QUESTION);

		submitAnswerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSubmit){
						isSubmit=false;
						submitAnswer(v);
				}
				else{
					if(noMoreQuestions){
						RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
						answers.setVisibility(RadioGroup.GONE);
						TextView question = (TextView) getView().findViewById(R.id.question);
						question.setText(getString(R.string.question_text));
						ImageView image = (ImageView) getView().findViewById(R.id.imageView1);
						//image.setImageResource(R.drawable.sorry);
						Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
						submitAnswerButton.setText("Next");
						QuestionController.getInstance(getView().getContext()).getNextQuestion(QuestionsFragment.this, NEW_QUESTION);
						if(noMoreQuestions)
							toastSomething("Sorry, there are no more questions.");
					}
					else{
						isSubmit=true;
						changeQuestion(v);
					}
				}
			}
		});
		
 		RelativeLayout layout=(RelativeLayout)getView().findViewById(R.id.hidden_view);
 		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		 		RelativeLayout layout=(RelativeLayout)getView().findViewById(R.id.hidden_view);
		 		layout.setVisibility(RelativeLayout.GONE);
			}
		});
		
		
	    answers.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
				Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
				submitAnswerButton.setEnabled(true);
				submitAnswerButton.setVisibility(Button.VISIBLE);	
	        }
	    });
		
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	protected void changeQuestion(View v) {
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		if (answers.getCheckedRadioButtonId() != -1) {
			initializeQuestion(v);
		}
	}
	private void initializeQuestion(View v){
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		TextView question = (TextView) getView().findViewById(R.id.question);
		question.setText(currentQuestion.question);

		answers.clearCheck();
		
		for (int i = 0; i < answers.getChildCount(); i++) {
			View obj = answers.getChildAt(i);
			if (obj instanceof RadioButton) {
				RadioButton button = (RadioButton) obj;
				((RadioButton)answers.getChildAt(i)).setText(currentQuestion.answers.get(i));
				button.setEnabled(true);
				button.setTextColor(Color.WHITE);
			}
		}

//		Button nextQuestion = (Button) getView().findViewById(R.id.next_question_button);
//		nextQuestion.setEnabled(false);

		Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
		submitAnswerButton.setText("Submit");
		isSubmit=true;
	}

	@Override
	public void onJsonFinished(Object object, String type) {
		
		if(type.equals(NEW_QUESTION)){

			try {
				JSONObject job = new JSONObject((String)object);

					if(job.isNull("server_message")){
						RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
						answers.setVisibility(RadioGroup.VISIBLE);
						noMoreQuestions=false;
					}
				Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
				
				submitAnswerButton.setEnabled(true);
				submitAnswerButton.setVisibility(Button.VISIBLE);
				if(!job.isNull("server_message")){
					RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
					answers.setVisibility(RadioGroup.GONE);
					TextView question = (TextView) getView().findViewById(R.id.question);
					question.setText(getString(R.string.question_text));
					ImageView image = (ImageView) getView().findViewById(R.id.imageView1);
					//image.setImageResource(R.drawable.sorry);
					submitAnswerButton.setText("Next");
					isSubmit=false;
					noMoreQuestions=true;
					return;
				}

				Question q = new Question(new JSONObject((String)object));
						
				currentQuestion=q;
				RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);

				correctAnswer=((RadioButton)answers.getChildAt(currentQuestion.correctAnswerIndex)).getId();
				if(isInitial){
					initializeQuestion(getView());
					isInitial=false;
				}

				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(type.equals(SAVE_ANSWER)){
			QuestionController.getInstance(getView().getContext()).getNextQuestion(this, NEW_QUESTION);
		}
	}
	
	private void submitAnswer(View v){
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);

		if (answers.getCheckedRadioButtonId() != -1) {
			Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
			submitAnswerButton.setText("Next");
			submitAnswerButton.setEnabled(false);
			submitAnswerButton.setVisibility(Button.INVISIBLE);	

			
			int selectedAnswer = answers.getCheckedRadioButtonId();

			if (selectedAnswer != correctAnswer) {
				((RadioButton) getView().findViewById(selectedAnswer)).setTextColor(Color.RED);
			}
			else{
				displayCorrect();
			}

			((RadioButton) getView().findViewById(correctAnswer)).setTextColor(Color.GREEN);

			int selectedIndex=-1;
			for (int i = 0; i < answers.getChildCount(); i++) {
				RadioButton answer=((RadioButton) answers.getChildAt(i));
				if(answer.getId()==selectedAnswer){
					selectedIndex=i;
				}
				answer.setEnabled(false);
			}
			QuestionController.getInstance(v.getContext()).questionAnswered(currentQuestion, selectedIndex, this, SAVE_ANSWER);

//			Button nextQuestionButton = (Button) getView().findViewById(R.id.next_question_button);
//			nextQuestionButton.setEnabled(true);

		}
	}
	public void toastSomething(String toastString){
		Context context = getView().getContext();
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, toastString, duration);
		toast.show();
	}
	
	static final int[]color={Color.BLUE,Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
	private void displayCorrect(){
 		RelativeLayout layout=(RelativeLayout)getView().findViewById(R.id.hidden_view);
 		layout.setVisibility(RelativeLayout.VISIBLE);
		TextView expView = (TextView) getView().findViewById(R.id.hidden_experience);
		Typeface tf = Typeface.createFromAsset(getView().getContext().getAssets(), "fonts/American Captain.ttf");
		expView.setTypeface(tf);
		expView.setText("+"+0);
		
		expView = (TextView) getView().findViewById(R.id.hidden_value);
		expView.setTypeface(tf);
		
		UserController.getInstance(getView().getContext()).currentUser.experience+=currentQuestion.experience;
		
 		new CountDownTimer(UPDATE_ANIMATION_TIME, UPDATE_ANIMATION_INTERVAL) {
			long experience=0;
			long experienceIncrement=2*currentQuestion.experience/(UPDATE_ANIMATION_TIME/UPDATE_ANIMATION_INTERVAL);
			long maxExperience=currentQuestion.experience;
			
		     public void onTick(long millisUntilFinished) {
		 		if(experience<maxExperience){
		 			experience+=experienceIncrement;
		 			TextView expView = (TextView) getView().findViewById(R.id.hidden_experience);
		 			expView.setText("+"+experience);
		 		}
		 		else{
		 			TextView expView = (TextView) getView().findViewById(R.id.hidden_experience);
		 			expView.setText("+"+maxExperience);
		 		}
		     }

		     public void onFinish() {
		  		RelativeLayout layout=(RelativeLayout)getView().findViewById(R.id.hidden_view);
		 		layout.setVisibility(RelativeLayout.GONE);
		     }
		  }.start();
	}
}
