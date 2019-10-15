package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Order;
import gorboe.com.s319482mappe2.enteties.Restaurant;

public class CreateOrderActivity extends AppCompatActivity {

    private ListView order_friendList;
    private ArrayAdapter arrayAdapter;
    private Spinner order_friends;
    private Spinner order_restaurants;
    private EditText order_date;
    private EditText order_time;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        order_friendList = findViewById(R.id.order_friendList);
        order_friends = findViewById(R.id.order_friends);
        order_restaurants = findViewById(R.id.order_restaurants);
        order_date = findViewById(R.id.order_date);
        order_time = findViewById(R.id.order_time);
        db = new DBHandler(this);
        initializeRestaurantDropDown();
        initializeFriendList();
        initializeFriendDropDown();
    }

    public void initializeRestaurantDropDown(){
        List<Restaurant> restaurants = db.getAllRestaurants();
        ArrayAdapter<Restaurant> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurants);
        order_restaurants.setAdapter(adapter);
    }

    public void initializeFriendDropDown(){
        List<Friend> friends = db.getAllFriends();
        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, friends);
        order_friends.setAdapter(adapter);
    }

    public void initializeFriendList(){
        final List<Friend> friends = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, friends);

        order_friendList.setAdapter(arrayAdapter);

        order_friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                System.out.println("pressed: " + position);
            }
        });
    }
    public void addFriend(View view) {
        arrayAdapter.add(order_friends.getSelectedItem());
    }

    public void saveOrder(View view) {
        List<Friend> friends = new ArrayList<>();

        //add all selected friends
        for(int i = 0; i < arrayAdapter.getCount(); i++){
            friends.add((Friend)arrayAdapter.getItem(i));
        }

        Order order = new Order((Restaurant)order_restaurants.getSelectedItem(), order_date.getText().toString(),
                order_time.getText().toString(), friends);

        db.addOrder(order);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void deleteOrder(View view) {

    }
}
