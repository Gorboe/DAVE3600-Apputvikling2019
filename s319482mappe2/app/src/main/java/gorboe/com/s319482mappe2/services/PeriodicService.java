package gorboe.com.s319482mappe2.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class PeriodicService extends Service {

    private AlarmManager alarm;
    private PendingIntent pintent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: temp
        Toast.makeText(getApplicationContext(), "I PeriodicService", Toast.LENGTH_SHORT).show();
        System.out.println("I PeriodicService");

        //Periodic checker
        java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, ServiceManager.class);
        pintent = PendingIntent.getService(this, 0, i, 0);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent); //TODO: Check once per day, early morning probably

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Periodisk onDestroy()", Toast.LENGTH_SHORT).show();
        alarm.cancel(pintent);
        super.onDestroy();
    }
}
