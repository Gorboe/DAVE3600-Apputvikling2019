package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import gorboe.com.s319482mappe3.enteties.Reservation;

public class CreateReservationActivity extends AppCompatActivity {

    private int roomID;
    private String date;
    private Spinner STimeTo;
    private Spinner STimeFrom;
    private EditText ETDescription;
    private EditText ETName;
    private Reservation existing_reservation;
    private List<String> times_in_existing_reservation;
    private String fromTime;
    private boolean isInitialTimeFrom = true;
    private boolean isInitialTimeTo = true;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);


        STimeFrom = findViewById(R.id.timefrom);
        STimeTo = findViewById(R.id.timeto);
        ETDescription = findViewById(R.id.reservationdescription);
        ETName = findViewById(R.id.name);

        tryGetID();
        tryGetReservation();
        initializeTimeFromDropdown();
        populateTimeFromDropdown();
    }

    private void initializeTimeFromDropdown(){
        STimeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("PRESSED");
                if(position != null && position != i) {
                    System.out.println("TEST:::" + adapterView.getItemAtPosition(i).toString());
                    populateTimeToDropdown(adapterView.getItemAtPosition(i).toString());
                }
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void populateTimeFromDropdown(){
        List<String> availableFromTimes = Database.getInstance().getAvailableTimes(date, roomID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableFromTimes);
        STimeFrom.setAdapter(adapter);
        if(existing_reservation != null){
            for(int i = 0; i < times_in_existing_reservation.size()-1; i++){
                availableFromTimes.add(times_in_existing_reservation.get(i));
            }
            Collections.sort(availableFromTimes, new TimeComparator());
            if(isInitialTimeFrom){
                isInitialTimeFrom = false;
                //go thru available and find tier.get(0)
                for(int i = 0; i < availableFromTimes.size(); i++){
                    if(availableFromTimes.get(i).equals(times_in_existing_reservation.get(0))){
                        STimeFrom.setSelection(i);
                    }
                }
            }
            fromTime = (String)STimeFrom.getSelectedItem();
            System.out.println(fromTime);
        }else {
            fromTime = (String)STimeFrom.getSelectedItem();
        }
        if(availableFromTimes.isEmpty()){
            //TODO: new dialog!
        }

        populateTimeToDropdown(fromTime);
    }

    private void populateTimeToDropdown(String fromTime){
        List<String> availableToTimes = new ArrayList<>();
        int id = -1;
        if(existing_reservation != null){
            id = existing_reservation.getReservationID();
        }
        availableToTimes.addAll(Database.getInstance().getAvailableToTimes(date, fromTime, roomID, id));
        Collections.sort(availableToTimes, new TimeComparator());
        System.out.println("TEEEEST: " + availableToTimes);

        if(availableToTimes.isEmpty()){
            //TODO: new dialog!
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableToTimes);
        STimeTo.setAdapter(adapter);
        if(isInitialTimeTo && existing_reservation != null){
            isInitialTimeTo = false;
            int counter = 0;
            for(String time: availableToTimes){
                if(time.equals(existing_reservation.getTimeTo())){
                    STimeTo.setSelection(counter);
                }
                counter++;
            }
        }
    }

    private void tryGetReservation(){
        Intent intent = getIntent();
        int reservationID = intent.getIntExtra("reservationID", -1);

        if(reservationID != -1){
            existing_reservation = Database.getInstance().getReservation(reservationID);
            date = existing_reservation.getDate();
            ETDescription.setText(existing_reservation.getDescription());
            ETName.setText(existing_reservation.getName());
            times_in_existing_reservation = Database.getInstance().getTimesInReservation(existing_reservation.getReservationID());
        }
    }

    private void tryGetID(){
        Intent intent = getIntent();
        roomID = intent.getIntExtra("roomID", -1);
        date = intent.getStringExtra("date");
    }

    public void addReservation(View view) {
        //todo: validation?

        Reservation reservation = new Reservation(roomID,
                date,
                (String)STimeFrom.getSelectedItem(),
                (String)STimeTo.getSelectedItem(),
                ETName.getText().toString(),
                ETDescription.getText().toString());

        if(existing_reservation != null){
            reservation.setReservationID(existing_reservation.getReservationID());
            Database.getInstance().updateReservation(reservation);
        }else{
            Database.getInstance().addReservation(reservation);
        }

        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }

    public void delete(View view) {
        //delete/exit; delete hvis den har reservationID
        if(existing_reservation != null){
            Database.getInstance().deleteReservation(existing_reservation.getReservationID());
        }

        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }
}
