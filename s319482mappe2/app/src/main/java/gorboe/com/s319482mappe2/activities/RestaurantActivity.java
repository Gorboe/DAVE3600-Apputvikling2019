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

import java.util.List;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.create.CreateRestaurantActivity;
import gorboe.com.s319482mappe2.enteties.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private DBHandler db;
    private ListView restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mymenu);
        setActionBar(toolbar);
        db = new DBHandler(this);
        restaurantList = findViewById(R.id.restaurantList);
        initializeRestaurantList();
    }

    public void initializeRestaurantList(){
        final List<Restaurant> restaurants = db.getAllRestaurants();

        ArrayAdapter<Restaurant> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, restaurants);
        restaurantList.setAdapter(arrayAdapter);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Intent intent = new Intent(RestaurantActivity.this, CreateRestaurantActivity.class);
                intent.putExtra("selected_restaurant_ID", restaurants.get(position).getRestaurantID());
                startActivity(intent);
            }
        });
    }

    public void startCreateRestaurantActivity(View view) {
        startActivity(new Intent(this, CreateRestaurantActivity.class));
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
}
