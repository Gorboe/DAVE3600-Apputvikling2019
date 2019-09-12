package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class PopUpGameCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.getInstance().setLocale(getBaseContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_game_complete);

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
    }

    public void mainMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void startNewGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
