package gorboe.com.servicebroadcastsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    public void stoppPeriodisk(View v) {
        Toast.makeText(getApplicationContext(), "I STOP SERVICE", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, SMSService.class));
        //Intent i = new Intent(this, SMSService.class);
        //PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        //AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /*if (alarm!= null) {
            alarm.cancel(pintent);
        }*/
    }
}
