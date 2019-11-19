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
        int roomID = intent.getIntExtra("roomID", -1);
    }

    public void addReservation(View view) {
        //todo: if reservationID not existing!!! then only update values
        Database.getInstance().AddReservation(roomID, ETDate.getText().toString(), ETTime.getText().toString());
        finish();
    }

    public void delete(View view) {
        //delete/exit; delete hvis den har reservationID
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
