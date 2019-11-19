package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateReservationActivity extends AppCompatActivity {

    private int roomID;
    private EditText ETDate;
    private EditText ETTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        ETDate = findViewById(R.id.date);
        ETTime = findViewById(R.id.time);

        tryGetID();
    }

    private void tryGetID(){
        Intent intent = getIntent();
        roomID = intent.getIntExtra("roomID", -1);
    }

    public void addReservation(View view) {
        //todo: if reservationID not existing!!! then only update values
        System.out.println("BIGS" + roomID + " " + ETDate.getText().toString() + " " + ETTime.getText().toString());
        Database.getInstance().AddReservation(roomID, ETDate.getText().toString(), ETTime.getText().toString());

        Intent intent = new Intent(this, RoomDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }

    public void delete(View view) {
        //delete/exit; delete hvis den har reservationID
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
