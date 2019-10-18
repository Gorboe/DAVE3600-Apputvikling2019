package gorboe.com.s319482mappe2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.services.NotificationService;
import gorboe.com.s319482mappe2.services.Receiver;
import gorboe.com.s319482mappe2.services.PeriodicService;
import gorboe.com.s319482mappe2.services.SMSService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mymenu);
        setActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_order:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.nav_restaurant:
                startActivity(new Intent(this, RestaurantActivity.class));
                break;
            case R.id.nav_friends:
                startActivity(new Intent(this, FriendsActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void startService(View view) {
        Intent intent = new Intent();
        intent.setAction("gorboe.com.s319482mappe2.mybroadcast");
        sendBroadcast(intent);
    }

    public void stopService(View view) {
        Toast.makeText(getApplicationContext(), "I STOP SERVICE", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, PeriodicService.class));
        //maybe stop service helper service
        stopService(new Intent(this, NotificationService.class));
        stopService(new Intent(this, SMSService.class));
    }
}
