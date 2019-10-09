package gorboe.com.servicebroadcastsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View v) {
        //Intent intent = new Intent(this, MinService.class);
        //this.startService(intent);

        Intent intent = new Intent();
        intent.setAction("gorboe.com.servicebroadcastsample.mittbroadcast");
        sendBroadcast(intent);

    }

    public void stoppPeriodisk(View view) {
    }
}
