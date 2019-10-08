package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import gorboe.com.s319482mappe2.enteties.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private DBHandler db;
    private ListView restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
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
}
