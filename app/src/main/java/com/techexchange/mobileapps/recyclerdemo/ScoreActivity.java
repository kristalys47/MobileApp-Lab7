package com.techexchange.mobileapps.recyclerdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    static final String KEY_RESTART_QUIZ = "RetakeQuiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button againButton = findViewById(R.id.again_button);
        againButton.setOnClickListener(v -> onAgainButtonPressed());
        TextView scoreText = findViewById(R.id.score_text);
        int score = getIntent().getIntExtra(MainActivity.KEY_SCORE, 0);
        scoreText.setText("Quiz Score: " + score);


        SharedPreferences shared_pref = getSharedPreferences("Shared_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_pref.edit();
        int sp_score = shared_pref.getInt("Score", -1);
        if(sp_score==-1 ){
            editor.putInt("Score", score);
            editor.commit();
        }
        else {
            if (sp_score < score) {
                editor.putInt("Score", score);
                editor.commit();
                Toast.makeText(this, "You have a new highscore! " + score, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onAgainButtonPressed() {
        Intent data = new Intent();
        data.putExtra(KEY_RESTART_QUIZ, true);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

}