package gorboe.com.s319482mappe2.activities.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.RestaurantActivity;
import gorboe.com.s319482mappe2.core.Validator;
import gorboe.com.s319482mappe2.enteties.Restaurant;

public class CreateRestaurantActivity extends AppCompatActivity {
    private EditText name_field;
    private EditText address_field;
    private EditText number_field;
    private EditText type_field;
    private DBHandler db;
    private Restaurant existing_restaurant;

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
            existing_restaurant = db.getRestaurant(restaurant_id);
            name_field.setText(existing_restaurant.getName());
            address_field.setText(existing_restaurant.getAddress());
            number_field.setText(existing_restaurant.getNumber());
            type_field.setText(existing_restaurant.getType());
        }
    }

    public void saveRestaurant(View view) {
        //VALIDATE PHONE NR
        if(!Validator.validatePhoneNumber(number_field.getText().toString(), this)){
            return;
        }

        Restaurant restaurant = new Restaurant(name_field.getText().toString(),
                address_field.getText().toString(),
                number_field.getText().toString(),
                type_field.getText().toString());

        if(existing_restaurant != null){
            restaurant.setRestaurantID(existing_restaurant.getRestaurantID());
            db.updateRestaurant(restaurant);
            System.out.println("RESTAURANT WAS EDITED");
        }else{
            db.addRestaurant(restaurant);
            System.out.println("RESTAURANT ADDED TO DB");
        }

        startActivity(new Intent(this, RestaurantActivity.class));
    }

    public void deleteRestaurant(View view) {
        if(existing_restaurant != null){
            db.deleteRestaurant(existing_restaurant.getRestaurantID());
        }
        startActivity(new Intent(this, RestaurantActivity.class));
    }
}
