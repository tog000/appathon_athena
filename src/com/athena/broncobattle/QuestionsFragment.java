package com.athena.broncobattle;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionsFragment extends Fragment implements JsonEventListener<Object>{

	int correctAnswer = 0;
	boolean isSubmit=true;
	private Question currentQuestion;
	private boolean isInitial=true;
	
	private static final String NEW_QUESTION= "newQuestion";
	private static final String SAVE_ANSWER = "saveQuestion";
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.questions_fragment_layout, container, false);

		return view;

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		isInitial=true;
		QuestionController.getInstance(view.getContext()).getNextQuestion(this,NEW_QUESTION);

		Button submitAnswerButton = (Button) view.findViewById(R.id.submit_answer_button);
		submitAnswerButton.setEnabled(false);
		submitAnswerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSubmit){
					isSubmit=false;
					submitAnswer(v);
				}
				else{
					isSubmit=true;
					changeQuestion(v);
				}
			}
		});
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		
	    answers.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
				Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
				submitAnswerButton.setEnabled(true);	        }
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
			Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
			submitAnswerButton.setEnabled(true);
			
			currentQuestion=(Question)object;
			RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
			correctAnswer=((RadioButton)answers.getChildAt(currentQuestion.correctAnswerIndex)).getId();
			if(isInitial){
				initializeQuestion(getView());
				isInitial=false;
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
			//submitAnswerButton.setEnabled(false);
			
			int selectedAnswer = answers.getCheckedRadioButtonId();

			if (selectedAnswer != correctAnswer) {
				((RadioButton) getView().findViewById(selectedAnswer)).setTextColor(Color.RED);
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
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, toastString, duration);
		toast.show();
	}
}
