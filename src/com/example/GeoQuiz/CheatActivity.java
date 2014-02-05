package com.example.GeoQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by christerhansen on 29.01.14.
 */
public class CheatActivity extends Activity {

    Button showAnswerButton;
    private TextView answerTextView;
    private boolean answerIsTrue;
    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown";

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheat_layout);



        setAnswerShownResult(false);
        answerTextView = (TextView)findViewById(R.id.answerTextView);
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        showAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerIsTrue){
                    answerTextView.setText(R.string.true_button);
                }
                else{
                    answerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }
}