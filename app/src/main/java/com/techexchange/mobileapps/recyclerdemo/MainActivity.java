package com.techexchange.mobileapps.recyclerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Preconditions;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.techexchange.mobileapps.recyclerdemo.SingleQuestionFragment.OnQuestionAnsweredListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnQuestionAnsweredListener {

    static final String KEY_SCORE = "Score";
    private int currentScore = 0;
    private List<Question> questions;
    private int attempt = 0;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        questions = initQuestionList();
        viewPager = findViewById(R.id.question_pager);
        viewPager.setAdapter(new QuestionFragmentAdapter(fm, questions));
    }

    private List<Question> initQuestionList() {
        Resources res = getResources();
        String[] questions = res.getStringArray(R.array.questions);
        String[] correctAnswers = res.getStringArray(R.array.correct_answers);
        String[] wrongAnswers = res.getStringArray(R.array.incorrect_answers);


        List<Question> qList = new ArrayList<>();
        for (int i = 0; i < questions.length; ++i) {
            qList.add(new Question(questions[i], correctAnswers[i], wrongAnswers[i]));
        }
        return qList;
    }
    @Override
    public void onQuestionAnswered(String selectedAnswer, int questionId) {
        System.out.println("The " + selectedAnswer + " button was pressed!");
        System.out.println("Current answer: " + questionId);
        TextView mTextView;

        if (questions.get(questionId).getSelectedAnswer() == null) {
            if (selectedAnswer.contentEquals(questions.get(questionId).getCorrectAnswer())) {
                currentScore++;
                System.out.println(currentScore);
                questions.get(questionId).setSelectedAnswer(selectedAnswer);
            }
            attempt++;


            if (attempt == questions.size()) {
                System.out.println("Enters the intent");
                Intent scoreIntent = new Intent(this, ScoreActivity.class);
                scoreIntent.putExtra(KEY_SCORE, currentScore);
                startActivityForResult(scoreIntent, 0);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean repeat = data.getBooleanExtra(ScoreActivity.KEY_RESTART_QUIZ, true);
        if (resultCode != Activity.RESULT_OK || requestCode != 0 || data == null) {
            finish();
        }
        else if(repeat){
            currentScore = 0;
            attempt = 0;
            for(Question q: questions){
                q.setSelectedAnswer(null);
            }
            viewPager.setAdapter(new QuestionFragmentAdapter(getSupportFragmentManager(), questions));

        }
        else
            finish();
    }

    private static final class QuestionFragmentAdapter
            extends FragmentStatePagerAdapter {
        private List<Question> questions;


        public QuestionFragmentAdapter(FragmentManager fm, List<Question> questions) {
            super(fm);
            this.questions = questions;
        }

        @Override
        public Fragment getItem(int position) {
            return SingleQuestionFragment.createFragmentFromQuestion(questions.get(position), position);
        }


        @Override
        public int getCount() {
            return questions.size();

        }
    }
}