package gorboe.com.s319482mappe1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private final StringBuilder answer = new StringBuilder();
    private TextView inputField;
    private TextView questionField;
    private TextView correctBox;
    private TextView wrongBox;
    private String[] questions;
    private int[] answers;
    private int questionCount = 0;
    private int correct_count = 0;
    private int wrong_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext()); //set language to what shared preferences is
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        inputField = findViewById(R.id.inputField);
        questionField = findViewById(R.id.questionField);
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getIntArray(R.array.answers);

        correctBox = findViewById(R.id.correctBox);
        wrongBox = findViewById(R.id.wrongBox);
        correctBox.setText("0");
        wrongBox.setText("0");

        initializeButtons();
        loadRandomQuestion();
    }

    private void initializeButtons(){
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_answer = findViewById(R.id.btn_answer);
        Button btn_back = findViewById(R.id.btn_back);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_answer.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    int random;
    private void loadRandomQuestion(){
        boolean uniqueQuestion = false;
        while (!uniqueQuestion){
            random = ThreadLocalRandom.current().nextInt(0,25); //[0, 24] t:[0,25)
            if(questions[random] != null){
                uniqueQuestion = true;
            }
        }

        questionField.setText(questions[random]);
        questions[random] = null;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                answer.append("1");
                break;
            case R.id.btn_2:
                answer.append("2");
                break;
            case R.id.btn_3:
                answer.append("3");
                break;
            case R.id.btn_4:
                answer.append("4");
                break;
            case R.id.btn_5:
                answer.append("5");
                break;
            case R.id.btn_6:
                answer.append("6");
                break;
            case R.id.btn_7:
                answer.append("7");
                break;
            case R.id.btn_8:
                answer.append("8");
                break;
            case R.id.btn_9:
                answer.append("9");
                break;
            case R.id.btn_0:
                answer.append("0");
                break;

            case R.id.btn_answer:
                checkAnswer();
                break;
            case R.id.btn_back:
                removeLast();
        }
        inputField.setText(answer);
    }

    private void checkAnswer(){
        if(answer.length() == 0){
            return; //no numbers given, dont check answer
        }
        questionCount++;
        if(answer.toString().equals(answers[random]+"")){
            String temp = ++correct_count + "";
            correctBox.setText(temp);
        }else{
            String temp = ++wrong_count + "";
            wrongBox.setText(temp);
        }

        if(Database.getInstance().getPreferred_amount_of_questions() == questionCount){
            Database.getInstance().addGameStatistic(correct_count, wrong_count, getBaseContext());
            Database.getInstance().storeStatistics(getBaseContext()); //permanent store
            startActivity(new Intent(this, PopUpGameCompleteActivity.class));
            return;
        }

        //empty the input box and ready it for next question
        answer.delete(0, answer.length());
        inputField.setText(answer);

        //next question
        loadRandomQuestion();
    }

    private void removeLast(){
        if(answer.length() == 0){
            return;
        }
        answer.deleteCharAt(answer.length()-1);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PopUpExitGameActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("correct_count", String.valueOf(correct_count));
        outState.putString("wrong_count", String.valueOf(wrong_count));
        outState.putString("question_count", String.valueOf(questionCount));
        outState.putString("input_field", inputField.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        correct_count = Integer.parseInt(savedInstanceState.getString("correct_count", "-1"));
        wrong_count = Integer.parseInt(savedInstanceState.getString("wrong_count", "-1"));
        questionCount = Integer.parseInt(savedInstanceState.getString("question_count", "-1"));
        answer.append(savedInstanceState.getString("input_field", "fail"));
        inputField.setText(answer);
        String correct = correct_count + "";
        String wrong = wrong_count + "";
        correctBox.setText(correct);
        wrongBox.setText(wrong);
    }
}
