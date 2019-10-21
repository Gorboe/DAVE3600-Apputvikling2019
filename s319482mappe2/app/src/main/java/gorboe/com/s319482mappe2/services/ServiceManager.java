package gorboe.com.s319482mappe2.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.enteties.Order;

public class ServiceManager extends Service {

    private DBHandler db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        db = new DBHandler(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I ServiceManager checking orders", Toast.LENGTH_SHORT).show();

        String currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                            (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + //because calendar start at 0
                             Calendar.getInstance().get(Calendar.YEAR);

        for(Order order: db.getAllOrders()){
            String orderDate = order.getDate();
            System.out.println("Matchin: " + orderDate + " against " + currentDate);
            if(currentDate.equals(orderDate)){
                //this order is today!!
                Toast.makeText(getApplicationContext(), "THIS ORDER IS TODAY!", Toast.LENGTH_SHORT).show();

                //add order_id to shared pref so we can access it in service
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putLong("current_order_ID", order.get_orderID()).apply();

                //start notification service
                startService(new Intent(this, NotificationService.class));

                //start sms service (mellomservice for å håndtere tid?)
                startService(new Intent(this, SMSService.class));
            }else{
                Toast.makeText(getApplicationContext(), "ORDER NOT TODAY! " + order.get_orderID(), Toast.LENGTH_SHORT).show();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "ServiceManager onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
