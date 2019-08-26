package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ListView statisticsList = findViewById(R.id.statisticsList);

        //new string list
        List<GameStatistic> statistics;
        statistics = Database.getInstance().getPrevious_games_collection();



        ArrayAdapter<GameStatistic> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1,
                        statistics);


        statisticsList.setAdapter(arrayAdapter);
        //TODO: DELETE PREVIOUS GAMES
    }
}
