package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PopUpDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_delete);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int orientation = getResources().getConfiguration().orientation;
        int width;
        int height;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            width = (int)(displayMetrics.widthPixels * 0.5);
            height = (int)(displayMetrics.heightPixels * 0.5);
        } else {
            // In portrait
            width = (int)(displayMetrics.widthPixels * 0.8);
            height = (int)(displayMetrics.heightPixels * 0.3);
        }

        getWindow().setLayout(width, height);

        Button delete = findViewById(R.id.btn_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                deleteStat();
            }
        });
    }

    private void deleteStat(){
        Database.getInstance().deleteStat();
        Database.getInstance().storeStatistics(getBaseContext());
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    public void closePopUp(View view) {
        finish();
    }
}
