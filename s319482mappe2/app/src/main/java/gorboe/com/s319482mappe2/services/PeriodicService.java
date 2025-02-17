package gorboe.com.s319482mappe2.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int hour = preferences.getInt("prefHour", 17); //17 base
        int minutes = preferences.getInt("prefMinutes", 0); //0

        //Periodic checker
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);

        Intent i = new Intent(this, ServiceManager.class);
        pintent = PendingIntent.getService(this, 0, i, 0);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, pintent); //1day = 86 400 000ms

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        alarm.cancel(pintent);
        super.onDestroy();
    }
}
