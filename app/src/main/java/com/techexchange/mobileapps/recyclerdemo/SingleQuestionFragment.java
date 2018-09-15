package com.techexchange.mobileapps.recyclerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    interface OnQuestionAnsweredListener {
        void onQuestionAnswered(String selectedAnswer, int questionId);
    }
    private OnQuestionAnsweredListener answerListener;
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
        questionView = rootView.findViewById(R.id.question_text);
        leftButton = rootView.findViewById(R.id.left_button);
        rightButton = rootView.findViewById(R.id.right_button);
        correctTextView = rootView.findViewById(R.id.correct_incorrect_text);
        Bundle args = getArguments();
        questionIndex = args.getInt(ARG_QUESTION_INDEX);
        if (args != null) {
            questionView.setText(args.getString(ARG_QUESTION_TEXT));
            if (Math.random() < 0.5) {
                leftButton.setText(args.getString(ARG_CORRECT_ANSWER));
                leftButton.setOnClickListener(v -> answerListener.onQuestionAnswered(args.getString(ARG_CORRECT_ANSWER), questionIndex));
                rightButton.setText(args.getString(ARG_WRONG_ANSWER));
                rightButton.setOnClickListener(v -> answerListener.onQuestionAnswered(args.getString(ARG_WRONG_ANSWER), questionIndex));
            } else {
                rightButton.setText(args.getString(ARG_CORRECT_ANSWER));
                rightButton.setOnClickListener(v -> answerListener.onQuestionAnswered(args.getString(ARG_CORRECT_ANSWER), questionIndex));
                leftButton.setText(args.getString(ARG_WRONG_ANSWER));
                leftButton.setOnClickListener(v -> answerListener.onQuestionAnswered(args.getString(ARG_WRONG_ANSWER), questionIndex));
            };
            correctTextView.setText(R.string.initial_correct_incorrect);
        }
        return rootView;
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



