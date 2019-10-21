package gorboe.com.s319482mappe2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import java.util.Calendar;

import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.services.NotificationService;
import gorboe.com.s319482mappe2.services.PeriodicService;
import gorboe.com.s319482mappe2.services.SMSService;
import gorboe.com.s319482mappe2.services.ServiceManager;

public class SettingsActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    private TextView settings_time;
    private EditText messageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toggleButton = findViewById(R.id.toggleButton);
        settings_time = findViewById(R.id.settings_time);
        messageField = findViewById(R.id.messageField);
        toolbar.inflateMenu(R.menu.mymenu);
        setActionBar(toolbar);

        checkToggleState();
        checkPreferredTime();
        checkSMSMessage();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSMSMessage();
                if (isChecked) {
                    // The toggle is enabled
                    toggleButton.setBackgroundResource(R.drawable.toggle_button_on);
                    startService();
                } else {
                    // The toggle is disabled
                    toggleButton.setBackgroundResource(R.drawable.toggle_button_off);
                    stopService();
                }
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                preferences.edit().putBoolean("toggleState", isChecked).apply(); //always store state permanently
            }
        });
    }

    public void checkSMSMessage(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultSMSMessage = "Hei, dette er en påminnelse på din restaurant avtale idag!";
        String message = preferences.getString("smsmessage", defaultSMSMessage);
        messageField.setText(message);
    }

    public void saveSMSMessage(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString("smsmessage", messageField.getText().toString()).apply();
    }

    public void checkPreferredTime(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int hour = preferences.getInt("prefHour", 17); //17 base
        int minutes = preferences.getInt("prefMinutes", 0); //0
        String time = String.format("%02d:%02d", hour, minutes); //17:00 base
        settings_time.setText(time);
    }

    public void checkToggleState(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean toggleState = preferences.getBoolean("toggleState", true); //default = false
        toggleButton.setChecked(toggleState);

        if(toggleState){
            toggleButton.setBackgroundResource(R.drawable.toggle_button_on);
        }else {
            toggleButton.setBackgroundResource(R.drawable.toggle_button_off);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        saveSMSMessage(); //always saves
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

    public void startService() {
        Intent intent = new Intent();
        intent.setAction("gorboe.com.s319482mappe2.mybroadcast");
        sendBroadcast(intent);
    }

    public void stopService() {
        Toast.makeText(getApplicationContext(), "I STOP SERVICE", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, PeriodicService.class));
        stopService(new Intent(this, ServiceManager.class));
        stopService(new Intent(this, NotificationService.class));
        stopService(new Intent(this, SMSService.class));
    }

    public void openTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                preferences.edit().putInt("prefHour", hour).apply();
                preferences.edit().putInt("prefMinutes", minutes).apply(); //need to store permanently for service
                String time = String.format("%02d:%02d", hour, minutes); //secures format hh:mm
                settings_time.setText(time);

                //restart service so it get new time preference
                saveSMSMessage();
                stopService();
                startService();
            }

        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        saveSMSMessage();
        super.onBackPressed();
    }
}
