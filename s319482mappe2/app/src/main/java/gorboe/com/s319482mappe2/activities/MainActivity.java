package gorboe.com.s319482mappe2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.List;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.create.CreateOrderActivity;
import gorboe.com.s319482mappe2.core.Navigation;
import gorboe.com.s319482mappe2.enteties.Order;

public class MainActivity extends AppCompatActivity {

    //TODO: create icons vector in drawable...
    //TODO: content://authority/path/id  ex: content://gorboe.com.s319842mappe2/restaurant/2

    //TODO: CHECK ONCE PER DAY FOR ORDERS AND GIVE NOTIFICATION TO USER AND SEND SMS TO FRIENDS (SMS STORED IN SHARED PREF)
    //TODO: SETTINGS. SET THE TIME FOR THE SMS SENDING. TURN ON AND OF SMS SERVICE.
    //TODO: IF PHONE TURNS OF, RESTART THE SERVICE THINGY
    //TODO: CONTENT PROVIDER TO SHARE RESTAURANT DATA
    //TODO: DESIGN AND LAYOUT.

    //TODO: sms-service
    //TODO: xml for settings, shared pref, fra timen
    //TODO: order check, må ha lagt inn rest
    //TODO: validation for phone number.
    //TODO: test project for content provider.
    //TODO: back-stack. (Navigation)
    //TODO: REMOVE PRINT LINES, LOOK OVER DATABASE CODE

    private ListView orderList;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderList = findViewById(R.id.orderList);
        db = new DBHandler(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mymenu);
        setActionBar(toolbar);
        initializeOrderList();

        System.out.println("YEAR: " + Calendar.getInstance().get(Calendar.YEAR));
        System.out.println("MONTH: " + (Calendar.getInstance().get(Calendar.MONTH) + 1)); //+1 fordi den starter på 0 altså february = 0
        System.out.println("DAY IN MONTH: " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        System.out.println("HOUR: " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        System.out.println("MINUTE: " + Calendar.getInstance().get(Calendar.MINUTE));
    }

    public void initializeOrderList(){
        final List<Order> orders = db.getAllOrders();

        ArrayAdapter<Order> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, orders);

        orderList.setAdapter(arrayAdapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Intent intent = new Intent(MainActivity.this, CreateOrderActivity.class);
                intent.putExtra("selected_order_ID", orders.get(position).get_orderID());
                startActivity(intent);
            }
        });
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
                //startActivity(new Intent(this, MainActivity.class));
                Navigation.getInstance().forward(this, new Intent(this, MainActivity.class));
                break;
            case R.id.nav_restaurant:
                //startActivity(new Intent(this, RestaurantActivity.class));
                Navigation.getInstance().forward(this, new Intent(this, RestaurantActivity.class));
                break;
            case R.id.nav_friends:
                //startActivity(new Intent(this, FriendsActivity.class));
                Navigation.getInstance().forward(this, new Intent(this, FriendsActivity.class));
                break;
            case R.id.nav_settings:
                //startActivity(new Intent(this, SettingsActivity.class));
                Navigation.getInstance().forward(this, new Intent(this, SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void createOrder(View view) {
        startActivity(new Intent(this, CreateOrderActivity.class));
    }
}
