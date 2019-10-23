package gorboe.com.s319482mappe2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.create.CreateOrderActivity;
import gorboe.com.s319482mappe2.core.DateComparator;
import gorboe.com.s319482mappe2.enteties.Order;

public class MainActivity extends AppCompatActivity {

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
    }

    public void initializeOrderList(){
        List<Order> ordersList = db.getAllOrders();

        //REMOVE OLD ORDERS
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            for(Order order: ordersList){
                Date orderDate;
                try {
                    orderDate = sdf.parse(order.getDate() + " " + order.getTime());
                } catch (ParseException e) {
                    continue;
                }
                if(orderDate.before(todayDate)){
                    db.deleteOrder(order.get_orderID()); //delete order from database and not list
                }
            }
        }
        final List<Order> orders = db.getAllOrders();

        //SORT ORDERS
        Collections.sort(orders, new DateComparator());

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
