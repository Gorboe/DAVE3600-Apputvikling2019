package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }

    public void startCreateRestaurantActivity(View view) {
        startActivity(new Intent(this, CreateRestaurantActivity.class));
    }
}
