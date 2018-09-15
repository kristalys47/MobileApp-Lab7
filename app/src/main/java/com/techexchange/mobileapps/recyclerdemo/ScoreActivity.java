package com.techexchange.mobileapps.recyclerdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    static final String KEY_RESTART_QUIZ = "RetakeQuiz";
    static final String HIGHSCORE = "HIGHSCORE";
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button againButton = findViewById(R.id.again_button);
        againButton.setOnClickListener(v -> onAgainButtonPressed());
        TextView scoreText = findViewById(R.id.score_text);
        score = getIntent().getIntExtra(MainActivity.KEY_SCORE, -1);
        scoreText.setText("Quiz Score: " + score);
        Button emailButton = findViewById(R.id.email_button);
        emailButton.setOnClickListener(v -> onEmailButtonPressed());


        SharedPreferences shared_pref = getSharedPreferences("Shared_Pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_pref.edit();
        int sp_score = shared_pref.getInt(HIGHSCORE, -1);
        if (sp_score < score) {
            editor.putInt(HIGHSCORE, score);
            editor.commit();
            Toast.makeText(this, "You have a new highscore! " + score, Toast.LENGTH_LONG).show();
        }
    }


    private void onAgainButtonPressed() {
        Intent data = new Intent();
        data.putExtra(KEY_RESTART_QUIZ, true);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void onEmailButtonPressed() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String message = "Here is your score: " + score + "\nPlay more to improve your score.";
        intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your score is here!");
        intent.putExtra(Intent.EXTRA_TEXT, message); // Enter a string for the email body
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    "The activity could not be resolved.", Toast.LENGTH_SHORT).show();
        }
    }

}