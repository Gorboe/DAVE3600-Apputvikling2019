package gorboe.com.s319482mappe2.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;

import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Order;

public class SMSService extends Service {

    private DBHandler db;

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        db = new DBHandler(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //get order_id from sharedpref
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long order_id = preferences.getLong("current_order_ID", -1);
        Order order = db.getOrder(order_id);

        //get msg from sharedpref
        String defaultSMSMessage = "Hei, dette er en påminnelse på din restaurant avtale idag!";
        String message = preferences.getString("smsmessage", defaultSMSMessage);

        for(Friend friend: order.getFriends()){
            try{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(friend.getNumber(), null, message, null, null);
            }catch (Exception e){
                //should never throw exception here as i validate all phone numbers. But have the try catch just in case
                new AlertDialog.Builder(this)
                        .setTitle("Advarsel")
                        .setIcon(R.drawable.ic_warning_yellow)
                        .setMessage("Noe gikk galt når vi prøvde å sende ut SMS.")
                        .show();
            }
        }
        //+15 555 21 5554
        //+15 555 21 5556
        //...\platform-tools>adb devices

        return super.onStartCommand(intent, flags, startId);
    }
}
