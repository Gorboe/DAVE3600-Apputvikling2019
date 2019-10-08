package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe2.enteties.Restaurant;

public class CreateRestaurantActivity extends AppCompatActivity {

    private EditText navn_field;
    private EditText address_field;
    private EditText number_field;
    private EditText type_field;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        navn_field = findViewById(R.id.restaurant_name);
        address_field = findViewById(R.id.restaurant_address);
        number_field = findViewById(R.id.restaurant_number);
        type_field = findViewById(R.id.restaurant_type);
        db = new DBHandler(this);
    }

    public void addRestaurantToDatabase(View view) {
        Restaurant restaurant = new Restaurant(navn_field.getText().toString(),
                                               address_field.getText().toString(),
                                               number_field.getText().toString(),
                                               type_field.getText().toString());
        db.addRestaurant(restaurant);
        System.out.println("RESTAURANT ADDED TO DB");
    }
}
