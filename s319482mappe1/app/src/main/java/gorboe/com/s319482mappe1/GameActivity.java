package gorboe.com.s319482mappe1;

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

        initializeButtons();
        loadRandomQuestion();
        System.out.println("This is the pref amount: " + Database.getInstance().getPreferred_amount_of_questions());
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
        btn_answer.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }


    //TODO: MSG TO USER IF ALL QUESTIONS USED
    //TODO: LAYOUT!!
    //TODO: CREATE ICON
    //TODO: SAVE STATE WHEN SCREEN ROTATES. RIKTIG OG GALE SVAR + QUESTIONCOUNT
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
            case R.id.btn_answer:
                checkAnswer();
                break;
            case R.id.btn_back:
                removeLast();
        }
        inputField.setText(answer);
    }

    private void checkAnswer(){
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
            startActivity(new Intent(this, PopUpGameCompleteActivity.class));
            return;
        }

        loadRandomQuestion();
    }

    private void storeStatistics(String lang){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //TODO: LOADS....
        //preferences.edit().putString("language_key", lang).apply(); //stores current language
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
}
