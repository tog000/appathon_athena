package com.athena.broncobattle;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionsFragment extends Fragment implements JsonEventListener<Question>{

	int correctAnswer = 0;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.questions_fragment_layout, container, false);

		QuestionController.getInstance(view.getContext()).addJsonEventListener(this);
		QuestionController.getInstance(view.getContext()).getNextQuestion();
		
		correctAnswer = R.id.answer_one;

		Button submitAnswerButton = (Button) view.findViewById(R.id.submit_answer_button);
		submitAnswerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);

				if (answers.getCheckedRadioButtonId() != -1) {
					int selectedAnswer = answers.getCheckedRadioButtonId();

					if (selectedAnswer != correctAnswer) {
						((RadioButton) getView().findViewById(selectedAnswer)).setTextColor(Color.RED);
					}

					((RadioButton) getView().findViewById(correctAnswer)).setTextColor(Color.GREEN);

					for (int i = 0; i < answers.getChildCount(); i++) {
						((RadioButton) answers.getChildAt(i)).setEnabled(false);
					}

					Button nextQuestionButton = (Button) getView().findViewById(R.id.next_question_button);
					nextQuestionButton.setEnabled(true);

					Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
					submitAnswerButton.setEnabled(false);

				}
			}
		});

		Button nextQuestionButton = (Button) view.findViewById(R.id.next_question_button);
		nextQuestionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				changeQuestion(v);

			}
		});

		nextQuestionButton.setEnabled(false);

		return view;

	}

	protected void changeQuestion(View v) {
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		if (answers.getCheckedRadioButtonId() != -1) {
			TextView question = (TextView) getView().findViewById(R.id.question);

			question.setText("What's next?");

			answers.clearCheck();

			for (int i = 0; i < answers.getChildCount(); i++) {
				View obj = answers.getChildAt(i);
				if (obj instanceof RadioButton) {
					RadioButton button = (RadioButton) obj;
					button.setText("yay " + i);
					button.setEnabled(true);
					button.setTextColor(button.getTextColors().getDefaultColor());
				}
			}

			Button nextQuestion = (Button) getView().findViewById(R.id.next_question_button);
			nextQuestion.setEnabled(false);

			Button submitAnswerButton = (Button) getView().findViewById(R.id.submit_answer_button);
			submitAnswerButton.setEnabled(true);

		}
	}

	@Override
	public void onReadFinished(Question object) {
		
		TextView question = (TextView) getView().findViewById(R.id.question);
		question.setText(object.question);
		
		RadioGroup answers = (RadioGroup) getView().findViewById(R.id.answers);
		for(int i=0;i<object.answers.size();i++){
			((RadioButton)answers.getChildAt(i)).setText(object.answers.get(i));
		}
		
		correctAnswer = ((RadioButton) answers.getChildAt(object.correctAnswerIndex)).getId();
	}
}