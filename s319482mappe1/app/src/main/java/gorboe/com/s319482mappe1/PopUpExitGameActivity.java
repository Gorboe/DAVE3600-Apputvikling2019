package gorboe.com.s319482mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class PopUpExitGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_exit_game);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int)(displayMetrics.widthPixels * 0.8);
        int height = (int)(displayMetrics.heightPixels * 0.3);

        getWindow().setLayout(width, height);
    }

    public void mainMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void closePopUp(View view) {
        finish();
    }
}
