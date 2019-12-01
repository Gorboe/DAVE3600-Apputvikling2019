package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe3.enteties.Room;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText ETdescription;
    private int markerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        ETdescription = findViewById(R.id.description);

        tryGetMarkerID();
    }

    public void tryGetMarkerID(){
        Intent intent = getIntent();
        int mid = intent.getIntExtra("markerID", -1);
        if(mid != -1){
            markerID = mid;
        }
    }

    //(Save)
    public void addRoom(View view) {
        if(!Validator.validateNotEmpty(ETdescription.getText().toString(), this)){
            return;
        }
        //ADD
        Database.getInstance().addRoom(ETdescription.getText().toString(), markerID);
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", markerID);
        startActivity(intent);
        finish();
    }

    //(Exit)
    public void delete(View view) {
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", markerID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", markerID);
        startActivity(intent);
        finish();
    }
}
