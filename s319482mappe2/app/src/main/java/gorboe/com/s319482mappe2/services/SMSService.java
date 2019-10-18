package gorboe.com.s319482mappe2.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        Toast.makeText(getApplicationContext(), "I SMS ONCREATE", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I SMS SERVICE", Toast.LENGTH_SHORT).show();

        long order_id = intent.getLongExtra("selected_order_ID", -1);
        Order order = db.getOrder(order_id);
        //TODO: get msg from shared pref
        for(Friend friend: order.getFriends()){
            try{
                SmsManager smsManager = SmsManager.getDefault(); //TODO: try catch this
                smsManager.sendTextMessage(friend.getNumber(), null, "test msg", null, null); //+15555215556 for emu
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "SMS EXCEPTION!!", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
