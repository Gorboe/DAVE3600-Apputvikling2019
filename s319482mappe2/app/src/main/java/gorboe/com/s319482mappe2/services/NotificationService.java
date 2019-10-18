package gorboe.com.s319482mappe2.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.SettingsActivity;

public class NotificationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I NotificationService", Toast.LENGTH_SHORT).show();
        System.out.println("I NotificationService");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, SettingsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle("My Notification")
                .setContentText("Blablalba this is text")
                .setSmallIcon(R.mipmap.ic_launcher) //TODO: add own icon
                .setContentIntent(pIntent).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifikasjon);
        return super.onStartCommand(intent, flags, startId);
    }
}
