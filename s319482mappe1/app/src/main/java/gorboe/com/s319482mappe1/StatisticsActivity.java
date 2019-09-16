package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext()); //set language to what shared preferences is
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ListView statisticsList = findViewById(R.id.statisticsList);

        //new string list
        List<GameStatistic> statistics;
        statistics = Database.getInstance().getPrevious_games_collection();


        ArrayAdapter<GameStatistic> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, statistics);

        statisticsList.setAdapter(arrayAdapter);

        statisticsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Database.getInstance().selectedStat(position); 
                startActivity(new Intent(StatisticsActivity.this, PopUpDeleteActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
