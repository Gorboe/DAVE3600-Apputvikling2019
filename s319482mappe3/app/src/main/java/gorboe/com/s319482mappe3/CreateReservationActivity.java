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
    private TextView TVDate;
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

        TVDate = findViewById(R.id.txt_date);
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
        List<String> availableFromTimes = Database.getInstance().getAvailableTimes(TVDate.getText().toString(), roomID);
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
        availableToTimes.addAll(Database.getInstance().getAvailableToTimes(TVDate.getText().toString(), fromTime, roomID, id));
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
            TVDate.setText(existing_reservation.getDate());
            ETDescription.setText(existing_reservation.getDescription());
            ETName.setText(existing_reservation.getName());
            times_in_existing_reservation = Database.getInstance().getTimesInReservation(existing_reservation.getReservationID());
        }else{
            String currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + //+1 because Calender MONTH start at 0. so January = 0
                    Calendar.getInstance().get(Calendar.YEAR);

            TVDate.setText(currentDate);
        }
    }

    private void tryGetID(){
        Intent intent = getIntent();
        roomID = intent.getIntExtra("roomID", -1);
    }

    public void addReservation(View view) {
        //todo: validation?

        Reservation reservation = new Reservation(roomID,
                TVDate.getText().toString(),
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

        Intent intent = new Intent(this, RoomDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }

    public void delete(View view) {
        //delete/exit; delete hvis den har reservationID
        if(existing_reservation != null){
            Database.getInstance().deleteReservation(existing_reservation.getReservationID());
        }

        Intent intent = new Intent(this, RoomDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }

    public void openDatePicker(View view) {
        //TODO: when selected new date reinitialize dropdowns..
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                //VALIDATION OF SELECTED DATE
                if(year < currentYear){
                    new AlertDialog.Builder(CreateReservationActivity.this)
                            .setTitle("Advarsel")
                            .setIcon(R.drawable.ic_warning_yellow)
                            .setMessage("Året du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                            .show();
                    return;
                }
                if(year == currentYear){
                    if(month < currentMonth){
                        new AlertDialog.Builder(CreateReservationActivity.this)
                                .setTitle("Advarsel")
                                .setIcon(R.drawable.ic_warning_yellow)
                                .setMessage("Måneden du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                .show();
                        return;
                    }
                    if(month == currentMonth){
                        if(day < currentDayOfMonth){
                            new AlertDialog.Builder(CreateReservationActivity.this)
                                    .setTitle("Advarsel")
                                    .setIcon(R.drawable.ic_warning_yellow)
                                    .setMessage("Dagen du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                    .show();
                            return;
                        }
                    }
                }

                String date = day + "/" + (month + 1) + "/" + year; //month start at 0
                TVDate.setText(date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RoomDetailsActivity.class);
        intent.putExtra("roomID", roomID);
        startActivity(intent);
        finish();
    }
}
