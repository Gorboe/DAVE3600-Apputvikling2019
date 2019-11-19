package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gorboe.com.s319482mappe3.enteties.Room;

public class RoomDetailsActivity extends AppCompatActivity {

    private Room selected;
    private TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        details = findViewById(R.id.details);

        tryGetID();
    }

    private void tryGetID(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("roomID", -1);

        if(id != -1){
            System.out.println("TEST: " + id);
            selected = Database.getInstance().getRoom(id);
            details.setText(selected.getDescription());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void addReservation(View view) {
        Intent intent = new Intent(this, CreateReservationActivity.class);
        intent.putExtra("roomID", selected.getRoomID());
        startActivity(intent);
    }
}
