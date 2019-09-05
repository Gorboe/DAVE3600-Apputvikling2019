package gorboe.com.s319482mappe1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_start_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext()); //set language to what shared preferences is
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //buttons
        btn_start_game = findViewById(R.id.btn_start_game);
        Button btn_statistics = findViewById(R.id.btn_statistics);
        Button btn_preferences = findViewById(R.id.btn_preferences);

        //Start game listener
        btn_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                gameActivity();
            }
        });

        //Start statistics listener
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                statisticsActivity();
            }
        });

        //Start preferences activity
        btn_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                preferencesActivity();
            }
        });
    }

    //start game activity
    public void gameActivity(){
        startActivity(new Intent(this, GameActivity.class));
    }

    //start statistics activity
    public void statisticsActivity(){
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    //start preferences activity
    public void preferencesActivity(){
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //Database.getInstance().setLang(savedInstanceState.getString("LANGUAGE"));
        //Database.getInstance().setPreferred_amount_of_questions(savedInstanceState.getInt("QUESTIONS"));
        //Database.getInstance().setLocale(getBaseContext());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putString("START_GAME_KEY", btn_start_game.getText().toString());
        //outState.putInt("QUESTIONS", Database.getInstance().getPreferred_amount_of_questions());
        //outState.putString("LANGUAGE", Database.getInstance().getLang());
        super.onSaveInstanceState(outState);
    }
}
