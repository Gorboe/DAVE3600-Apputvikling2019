package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreferencesActivity extends AppCompatActivity {

    //preferences buttons
    private Button btn_5_questions;
    private Button btn_10_questions;
    private Button btn_25_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);



        btn_5_questions = findViewById(R.id.btn_5_questions);
        btn_10_questions = findViewById(R.id.btn_10_questions);
        btn_25_questions = findViewById(R.id.btn_25_questions);

        //TODO: 5, 10, 25 questions
        //TODO: norwegian, german

        btn_5_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Database.getInstance().setPreferred_amount_of_questions(5);
                loadCurrentPreferences();
            }
        });

        btn_10_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Database.getInstance().setPreferred_amount_of_questions(10);
                loadCurrentPreferences();
            }
        });

        btn_25_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Database.getInstance().setPreferred_amount_of_questions(25);
                loadCurrentPreferences();
            }
        });

        loadCurrentPreferences();
    }

    private void loadCurrentPreferences(){
        //amount of questions
        int question_amount = Database.getInstance().getPreferred_amount_of_questions();
        if(question_amount == 5){
            setButtonColors(btn_5_questions);
        }else if(question_amount == 10){
            setButtonColors(btn_10_questions);
        }else{
            setButtonColors(btn_25_questions);
        }


        //preferred language
    }

    private void setButtonColors(Button preferred_amount){
        //reset colors
        btn_5_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_10_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_25_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        //preferred
        preferred_amount.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }
}
