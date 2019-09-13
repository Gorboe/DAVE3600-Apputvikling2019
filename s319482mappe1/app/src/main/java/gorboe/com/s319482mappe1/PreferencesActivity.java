package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class PreferencesActivity extends AppCompatActivity {

    //preferences buttons
    private Button btn_5_questions;
    private Button btn_10_questions;
    private Button btn_25_questions;

    //language buttons
    private Button btn_norwegian;
    private Button btn_german;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext()); //set language to what shared preferences is

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);



        btn_5_questions = findViewById(R.id.btn_5_questions);
        btn_10_questions = findViewById(R.id.btn_10_questions);
        btn_25_questions = findViewById(R.id.btn_25_questions);

        btn_norwegian = findViewById(R.id.btn_norwegian);
        btn_german = findViewById(R.id.btn_german);

        setSelectedLanguage(Database.getInstance().isGerman(getBaseContext()));
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

        btn_german.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("PRESSING GERMAN BUTTON");
                setLocale("de");
                recreate();
            }
        });

        btn_norwegian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("PRESSING NORWEGIAN BUTTON");
                setLocale("nb");
                recreate();
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

    private void setSelectedLanguage(boolean isGerman){
        if(isGerman){
            //tysk active
            btn_german.setBackground(getBaseContext().getDrawable(R.drawable.german_button_activated));
        }else{
            //norsk active
            btn_norwegian.setBackground(getBaseContext().getDrawable(R.drawable.norwegian_button_activated));
        }
    }

    private void setButtonColors(Button preferred_amount){
        //reset colors
        btn_5_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_10_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_25_questions.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        //preferred
        preferred_amount.setBackgroundColor(getResources().getColor(R.color.selected));
    }

    private void setLocale(String lang){
        storePreferredLanguage(lang);
        Database.getInstance().setLocale(getBaseContext());
        //temp
        //locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0); //current language on phone
        //temp.setText(locale.getLanguage());
    }

    private void storePreferredLanguage(String lang){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.edit().putString("language_key", lang).apply(); //stores current language
    }

    @Override
    public void onBackPressed() {
        finish(); //not on stack
        startActivity(new Intent(this, MainActivity.class));
    }
}
