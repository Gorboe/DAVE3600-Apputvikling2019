package gorboe.com.servicebroadcastsample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class SettPeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private AlarmManager alarm;
    private PendingIntent pintent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "I PeriodiskService", Toast.LENGTH_SHORT).show();
        java.util.Calendar cal = Calendar.getInstance();
        //Intent i = new Intent(this, MinService.class);
        Intent i = new Intent(this, SMSService.class);
        pintent = PendingIntent.getService(this, 0, i, 0);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Periodisk onDestroy()", Toast.LENGTH_SHORT).show();
        alarm.cancel(pintent);
        super.onDestroy();
    }
}
