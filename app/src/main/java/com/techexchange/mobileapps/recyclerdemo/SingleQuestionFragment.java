package com.techexchange.mobileapps.recyclerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SingleQuestionFragment extends Fragment {
    private static final String TAG = SingleQuestionFragment.class.getSimpleName();
    private static final String ARG_QUESTION_TEXT = "ARG_QUESTION_TEXT";
    private static final String ARG_CORRECT_ANSWER = "ARG_CORRECT_ANSWER";
    private static final String ARG_WRONG_ANSWER = "ARG_WRONG_ANSWER";
    private static final String ARG_QUESTION_INDEX = "ARG_QUESTION_INDEX";
    private static final String ARG_SELECTED_ANSWER = "ARG_SELECTED_ANSWER";
    private static int questionIndex;
    private TextView questionView, leftButton, rightButton, selectedAnswerView, correctTextView;
    private Question question;
    private OnQuestionAnsweredListener answerListener;

    interface OnQuestionAnsweredListener {
        void onQuestionAnswered(String selectedAnswer, int questionId);
    }

    public SingleQuestionFragment() {}

    static SingleQuestionFragment createFragmentFromQuestion(
            Question question, int questionIndex) {
        Bundle fragArgs = new Bundle();
        fragArgs.putString(ARG_QUESTION_TEXT, question.getQuestion());
        fragArgs.putString(ARG_CORRECT_ANSWER, question.getCorrectAnswer());
        fragArgs.putString(ARG_WRONG_ANSWER, question.getWrongAnswer());
        fragArgs.putInt(ARG_QUESTION_INDEX, questionIndex);
        fragArgs.putString(ARG_SELECTED_ANSWER, question.getSelectedAnswer());

        SingleQuestionFragment frag = new SingleQuestionFragment();
        frag.setArguments(fragArgs);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        //
        questionView = rootView.findViewById(R.id.question_text);
        leftButton = rootView.findViewById(R.id.left_button);
        rightButton = rootView.findViewById(R.id.right_button);
        correctTextView = rootView.findViewById(R.id.correct_incorrect_text);
        Bundle args = getArguments();
        System.out.println("El index del questions es: " + questionIndex);

        if (args != null) {

            correctTextView.setText(R.string.initial_correct_incorrect);
            questionIndex = args.getInt(ARG_QUESTION_INDEX);
            String q, ca, ia;

            q = args.getString(ARG_QUESTION_TEXT);
            ca = args.getString(ARG_CORRECT_ANSWER);
            ia = args.getString(ARG_WRONG_ANSWER);

            question = new Question(q, ca, ia);
        }
        updateView();
        return rootView;
    }

    private void updateView() {

        leftButton.setOnClickListener(this::onAnswerButtonPressed);
        rightButton.setOnClickListener(this::onAnswerButtonPressed);

        questionView.setText(question.getQuestion());

        if (Math.random() < 0.5) {
            leftButton.setText(question.getCorrectAnswer());
            rightButton.setText(question.getWrongAnswer());
        } else {
            rightButton.setText(question.getCorrectAnswer());
            leftButton.setText(question.getWrongAnswer());
        }
        correctTextView.setText(R.string.initial_correct_incorrect);
    }

    private void onAnswerButtonPressed(View v) {
        Button selectedButton = (Button) v;
        Question ques;
        if (question.getCorrectAnswer().contentEquals(selectedButton.getText())) {
            correctTextView.setText("Correct!");
        } else {
            correctTextView.setText("Incorrect! Swipe Left.");
        }
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        answerListener.onQuestionAnswered(selectedButton.getText().toString(), questionIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            answerListener = (OnQuestionAnsweredListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(
                    "The Context did not implement OnQuestionAnsweredListener!");
        }
    }
}



