package com.example.GeoQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.model.TrueFalse;

public class GeoActivity extends Activity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;

    private boolean isCheater;

    private TextView questionTextView;
    private int currentIndex = 0;

    private TrueFalse[] questionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_ocean, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private void updateQuestion(){
        int question = questionBank[currentIndex].getmQuestion();
        questionTextView.setText(question);
    }

    private void updatePrevButton(){
        if(currentIndex == 0){
            prevButton.setVisibility(View.GONE);
        }
        else{
            prevButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer(boolean userPressed){
        boolean isAnswerTrue = questionBank[currentIndex].ismTrueQuestion();

        int messageResId = 0;

        if(isCheater){
         messageResId = R.string.judgement_toast;
        }
        else{
            if(userPressed == isAnswerTrue){
                messageResId = R.string.correct_toast;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }

        isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        questionTextView = (TextView) findViewById(R.id.question_text_view);

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
                updatePrevButton();
                isCheater = false;
            }
        });

        prevButton = (Button) findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex - 1) % questionBank.length;
                updateQuestion();
                updatePrevButton();
                isCheater = false;
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GeoActivity.this, CheatActivity.class);
                boolean answerIsTrue = questionBank[currentIndex].ismTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        updateQuestion();
        updatePrevButton();
    }
}
