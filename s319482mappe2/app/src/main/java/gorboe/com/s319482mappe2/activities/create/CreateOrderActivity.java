package gorboe.com.s319482mappe2.activities.create;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.MainActivity;
import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Order;
import gorboe.com.s319482mappe2.enteties.Restaurant;

public class CreateOrderActivity extends AppCompatActivity {

    private ListView order_friendList;
    private Spinner order_friends;
    private Spinner order_restaurants;
    private TextView order_date;
    private TextView order_time;
    private DBHandler db;
    private Order existing_order;
    private List<Friend> listFriends = new ArrayList<>();
    private List<Friend> spinnerFriends = new ArrayList<>();

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
        }else {
            String currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                                (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + //+1 because Calender MONTH start at 0. so January = 0
                                 Calendar.getInstance().get(Calendar.YEAR);

            String currentTime = String.format("%02d:%02d",
                                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                        Calendar.getInstance().get(Calendar.MINUTE)); //secures format hh:mm

            order_date.setText(currentDate);
            order_time.setText(currentTime);
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
        List<Friend> allFriends = db.getAllFriends();

        if(existing_order != null){
            boolean isSelected = false;
            for(int i = 0; i < allFriends.size(); i++){
                for(int j = 0; j < existing_order.getFriends().size(); j++){
                    if(allFriends.get(i).getFriendID() == existing_order.getFriends().get(j).getFriendID()){
                        j = existing_order.getFriends().size();
                        isSelected = true;
                    }
                }
                if(!isSelected){
                    spinnerFriends.add(allFriends.get(i));
                }
                isSelected = false;
            }
        }else {
            spinnerFriends = allFriends;
        }
        populateFriendSpinner();
    }

    private void populateFriendSpinner(){
        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerFriends);
        order_friends.setAdapter(adapter);
    }

    public void initializeFriendList(){
        if(existing_order != null){
            listFriends = existing_order.getFriends();
        }
        populateFriendList();

        order_friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Friend friend = listFriends.get(position);
                listFriends.remove(friend);
                spinnerFriends.add(friend);
                populateFriendList();
                populateFriendSpinner();
            }
        });
    }

    private void populateFriendList(){
        ArrayAdapter<Friend> arrayAdapter = new ArrayAdapter<>
                (this, R.layout.simple_list_with_delete, listFriends);

        order_friendList.setAdapter(arrayAdapter);
    }

    public void addFriend(View view) {
        if(order_friends.getSelectedItem() == null){
            new AlertDialog.Builder(CreateOrderActivity.this)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow)
                    .setMessage("Du har allerede valgt alle vennene dine")
                    .show();
            return;
        }
        Friend friend = (Friend)order_friends.getSelectedItem();
        listFriends.add(friend);
        spinnerFriends.remove(friend);
        populateFriendList();
        populateFriendSpinner();
    }

    public void saveOrder(View view) {
        if(order_restaurants.getSelectedItem() == null){
            new AlertDialog.Builder(CreateOrderActivity.this)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow)
                    .setMessage("Du må legge til en restaurant før du kan lage en ordre")
                    .show();
            return;
        }

        Order order = new Order((Restaurant)order_restaurants.getSelectedItem(), order_date.getText().toString(),
                order_time.getText().toString(), listFriends);

        if(existing_order != null){
            order.set_orderID(existing_order.get_orderID());
            db.updateOrder(order);
        }else {
            db.addOrder(order);
        }

        startActivity(new Intent(this, MainActivity.class));
    }

    public void deleteOrder(View view) {
        if(existing_order != null){
            db.deleteOrder(existing_order.get_orderID());
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                //VALIDATION OF SELECTED DATE
                if(year < currentYear){
                    new AlertDialog.Builder(CreateOrderActivity.this)
                                   .setTitle("Advarsel")
                                   .setIcon(R.drawable.ic_warning_yellow)
                                   .setMessage("Året du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                   .show();
                    return;
                }
                if(year == currentYear){
                    if(month < currentMonth){
                        new AlertDialog.Builder(CreateOrderActivity.this)
                                .setTitle("Advarsel")
                                .setIcon(R.drawable.ic_warning_yellow)
                                .setMessage("Måneden du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                .show();
                        return;
                    }
                    if(month == currentMonth){
                        if(day < currentDayOfMonth){
                            new AlertDialog.Builder(CreateOrderActivity.this)
                                    .setTitle("Advarsel")
                                    .setIcon(R.drawable.ic_warning_yellow)
                                    .setMessage("Dagen du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                    .show();
                            return;
                        }
                    }
                }

                String date = day + "/" + (month + 1) + "/" + year; //month start at 0
                System.out.println(date);
                order_date.setText(date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void openTimePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                String time = String.format("%02d:%02d", hour, minutes); //secures format hh:mm

                System.out.println(time);
                order_time.setText(time);
            }

        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}
