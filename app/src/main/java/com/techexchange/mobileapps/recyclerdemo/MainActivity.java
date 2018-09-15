package com.techexchange.mobileapps.recyclerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.techexchange.mobileapps.recyclerdemo.SingleQuestionFragment.OnQuestionAnsweredListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnQuestionAnsweredListener {

    static final String KEY_SCORE = "Score";
    private int currentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        ViewPager viewPager = findViewById(R.id.contact_pager);
        viewPager.setAdapter(new QuestionFragmentAdapter(fm, MainActivity.this));
    }
    @Override
    public void onQuestionAnswered(String selectedAnswer, int questionId) {
        String[] temp = getResources().getStringArray(R.array.correct_answers);

        System.out.println("The " + selectedAnswer + " button was pressed!");
        System.out.println("Current answer: " + questionId);


        if (selectedAnswer.equals(temp[questionId-1])) {
            currentScore++;
            System.out.println("Current Score: " + currentScore);
        }
        if (questionId == 3){
            System.out.println("Enters the intent");
            Intent scoreIntent = new Intent(this, ScoreActivity.class);
            scoreIntent.putExtra(KEY_SCORE, currentScore);
            startActivityForResult(scoreIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean repeat = getIntent().getBooleanExtra(ScoreActivity.KEY_RESTART_QUIZ, true);
        if (resultCode != Activity.RESULT_OK || requestCode != 0 || data == null) {
            finish();
        }
        else if(repeat){
            currentScore = 0;
        }
        else
            finish();
    }

    private static final class QuestionFragmentAdapter
            extends FragmentStatePagerAdapter {
        private Context context;
        private Resources res;
        private String[] q;
        private String[] ca;
        private String[] ia;

        public QuestionFragmentAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            this.res = context.getResources();
            this.q = res.getStringArray(R.array.questions);
            this.ca = res.getStringArray(R.array.correct_answers);
            this.ia = res.getStringArray(R.array.incorrect_answers);
        }

        @Override
        public Fragment getItem(int position) {
            Question question = new Question(q[position], ca[position], ia[position]);
            return SingleQuestionFragment.createFragmentFromQuestion(question, position);
        }



        @Override
        public int getCount() {
            return q.length;

        }
    }
}