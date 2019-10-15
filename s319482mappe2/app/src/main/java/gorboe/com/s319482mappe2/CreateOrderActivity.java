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
    private Order existing_order;

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

        tryGetOrder();
        initializeFriendList();
        initializeRestaurantDropDown();
        initializeFriendDropDown();
    }

    public void tryGetOrder(){
        Intent intent = getIntent();
        long order_id = intent.getLongExtra("selected_order_ID", -1);

        if(order_id != -1){
            existing_order = db.getOrder(order_id);
            order_date.setText(existing_order.getDate());
            order_time.setText(existing_order.getTime());
        }
    }

    public void initializeRestaurantDropDown(){
        List<Restaurant> restaurants = db.getAllRestaurants();
        ArrayAdapter<Restaurant> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurants);
        order_restaurants.setAdapter(adapter);
        if(existing_order != null){
            //loops through restaurants in spinner to find the restaurant equal to the one in order
            for(int i = 0; i < order_restaurants.getAdapter().getCount(); i++){
                Restaurant currentRestaurant = restaurants.get(i);
                if(currentRestaurant.getRestaurantID() == existing_order.getRestaurant().getRestaurantID()){
                    order_restaurants.setSelection(i);
                    return;
                }
            }
        }
    }

    public void initializeFriendDropDown(){
        List<Friend> friends = db.getAllFriends();
        if(existing_order != null){
            //TODO: remove friends that are already selected?
        }
        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, friends);
        order_friends.setAdapter(adapter);
    }

    public void initializeFriendList(){
        List<Friend> friends;
        if(existing_order != null){
            friends = existing_order.getFriends();
        }else{
            friends = new ArrayList<>();
        }

        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, friends);

        order_friendList.setAdapter(arrayAdapter);

        order_friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                System.out.println("pressed: " + position);
                //TODO: add remove friends from list, but add them back to spinner..
            }
        });
    }
    public void addFriend(View view) {
        arrayAdapter.add(order_friends.getSelectedItem());
        //TODO: remove friend from spinner, so it cant be selected twice.

    }

    private void removeFromFriendSpinner(){

    }

    public void saveOrder(View view) {
        //TODO: make it possible to edit existing order..
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
        //TODO: create this, and the delete order method in db.
    }
}
