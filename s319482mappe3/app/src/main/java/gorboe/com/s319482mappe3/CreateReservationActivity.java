package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
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
    private List<String> availableFromTimes;
    private List<String> availableToTimes;
    private String fromTime;
    private String toTime;

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
        initializeTimeToDropdown();
    }

    private void initializeTimeFromDropdown(){
        if(existing_reservation != null){
            //todo: get correct time
        }
        availableFromTimes = Database.getInstance().getAvailableTimes(TVDate.getText().toString());

        if(availableFromTimes.isEmpty()){
            //TODO: dialog box error msg take back to room
        }else{
            populateTimeFromDropdown();
        }
    }

    private void populateTimeFromDropdown(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableFromTimes);
        STimeFrom.setAdapter(adapter);
        fromTime = (String)STimeFrom.getSelectedItem();
    }

    private void initializeTimeToDropdown(){
        if(existing_reservation != null){
            //todo: get correct time
        }

        availableToTimes = Database.getInstance().getAvailableToTimes(TVDate.getText().toString(), fromTime);

        if(availableToTimes.isEmpty()){
            //TODO: DIALOG BOX
        }else {
            populateTimeToDropdown();
        }
    }

    private void populateTimeToDropdown(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableToTimes);
        STimeTo.setAdapter(adapter);
    }

    private void tryGetReservation(){
        Intent intent = getIntent();
        int reservationID = intent.getIntExtra("reservationID", -1);

        if(reservationID != -1){
            existing_reservation = Database.getInstance().getReservation(reservationID);
            TVDate.setText(existing_reservation.getDate());
            ETDescription.setText(existing_reservation.getDescription());
            ETName.setText(existing_reservation.getName());
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
        finish();
    }
}
