package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe2.enteties.Restaurant;

public class CreateRestaurantActivity extends AppCompatActivity {

    private EditText name_field;
    private EditText address_field;
    private EditText number_field;
    private EditText type_field;
    private DBHandler db;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        name_field = findViewById(R.id.restaurant_name);
        address_field = findViewById(R.id.restaurant_address);
        number_field = findViewById(R.id.restaurant_number);
        type_field = findViewById(R.id.restaurant_type);
        db = new DBHandler(this);

        tryGetRestaurant();
    }

    public void tryGetRestaurant(){
        Intent intent = getIntent();
        long restaurant_id = intent.getLongExtra("selected_restaurant_ID", -1);

        if(restaurant_id != -1){
            restaurant = db.getRestaurant(restaurant_id);
            name_field.setText(restaurant.getName());
            address_field.setText(restaurant.getAddress());
            number_field.setText(restaurant.getNumber());
            type_field.setText(restaurant.getType());
        }
    }

    public void addRestaurantToDatabase(View view) {
        Restaurant restaurant = new Restaurant(name_field.getText().toString(),
                                               address_field.getText().toString(),
                                               number_field.getText().toString(),
                                               type_field.getText().toString());
        db.addRestaurant(restaurant);
        System.out.println("RESTAURANT ADDED TO DB");
    }
}
