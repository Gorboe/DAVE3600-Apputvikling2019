package gorboe.com.s319482mappe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_start_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext()); //set language to what shared preferences is
        Database.getInstance().retrieveStatistics(getBaseContext());
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
}
