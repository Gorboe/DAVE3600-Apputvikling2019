package gorboe.com.s319482mappe2.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I Receiver", Toast.LENGTH_SHORT).show();
        System.out.println("I Receiver");
        Intent i = new Intent(context, ServiceManager.class);
        context.startService(i);
    }
}
