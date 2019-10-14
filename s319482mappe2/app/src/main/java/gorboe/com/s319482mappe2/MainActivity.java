package gorboe.com.s319482mappe2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Order;

public class MainActivity extends AppCompatActivity {

    //TODO: create icons vector in drawable...
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

        //TODO: TEMP!!
        Order order = db.getOrder(1);
        System.out.println("ID: " + order.get_orderID());
        System.out.println("DATE: " + order.getDate());
        System.out.println("TIME: " + order.getTime());
        System.out.println("RESTAURANT: " + order.getRestaurant());
        for(Friend friend: order.getFriends()){
            System.out.println("FRIEND: " + friend);
        }
    }

    public void initializeOrderList(){
        final List<Order> orders = new ArrayList<>(); //db.getAllOrders..

        ArrayAdapter<Order> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, orders);

        orderList.setAdapter(arrayAdapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                System.out.println("pressed: " + position);
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

    public void createOrder(View view) {
        startActivity(new Intent(this, CreateOrderActivity.class));
    }
}
