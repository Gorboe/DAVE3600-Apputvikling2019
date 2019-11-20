package gorboe.com.s319482mappe3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gorboe.com.s319482mappe3.enteties.Reservation;
import gorboe.com.s319482mappe3.enteties.Room;

public class RoomDetailsActivity extends AppCompatActivity {

    private Room selected;
    private TextView details;
    private ListView reservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        details = findViewById(R.id.details);
        reservationList = findViewById(R.id.reservationList);

        tryGetID();
        initializeReservationList();
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

    public void initializeReservationList(){
        final List<Reservation> reservations = selected.getReservations();
        ArrayAdapter<Reservation> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, reservations);

        reservationList.setAdapter(arrayAdapter);

        reservationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Intent intent = new Intent(RoomDetailsActivity.this, CreateReservationActivity.class);
                intent.putExtra("roomID", reservations.get(position).getRoomID());
                intent.putExtra("reservationID", reservations.get(position).getReservationID());
                startActivity(intent);
                finish();
            }
        });
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
        finish();
    }

    public void deleteRoom(View view) {
        Database.getInstance().deleteRoom(selected.getRoomID());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
