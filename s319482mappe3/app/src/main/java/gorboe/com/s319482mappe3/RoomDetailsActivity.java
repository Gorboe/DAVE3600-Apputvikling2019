package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import gorboe.com.s319482mappe3.enteties.Reservation;
import gorboe.com.s319482mappe3.enteties.Room;

public class RoomDetailsActivity extends AppCompatActivity {

    private Room selected;
    private ListView reservationList;
    private TextView TVDate;
    private EditText ETdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        reservationList = findViewById(R.id.reservationList);
        TVDate = findViewById(R.id.txt_date);
        ETdescription = findViewById(R.id.desc);

        tryGetRoom();
        initializeReservationList();
    }

    private void tryGetRoom(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("roomID", -1);

        if(id != -1){
            selected = Database.getInstance().getRoom(id);
            ETdescription.setText(selected.getDescription());
        }

        String currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                            (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + //+1 because Calender MONTH start at 0. so January = 0
                             Calendar.getInstance().get(Calendar.YEAR);

        TVDate.setText(currentDate);
    }

    private void initializeReservationList(){
        List<Reservation> res = selected.getReservations();

        //REMOVE OLD RESERVATIONS
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            System.out.println("TEST: " + res);
            if(res != null && !res.isEmpty())
            for(Reservation reservation: res){
                System.out.println("TESTYY: " + reservation);
                Date reservationDate;
                try {
                    reservationDate = sdf.parse(reservation.getDate() + " " + reservation.getTimeTo());
                } catch (ParseException e) {
                    continue;
                }
                if(reservationDate.before(todayDate)){
                    System.out.println("THIS HAPPENS: " + todayDate + " " + reservation);
                    Database.getInstance().deleteReservation(reservation.getReservationID()); //delete reservation from database and not list
                }
                System.out.println("DONE WITH: " + reservation.getName());
            }
        }
        Database.getInstance().getAllItems();
        final List<Reservation> reservations = selected.getReservations();

        //SORT RESERVATIONS
        Collections.sort(reservations, new DateComparator());

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

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                //VALIDATION OF SELECTED DATE
                if(year < currentYear){
                    new AlertDialog.Builder(RoomDetailsActivity.this)
                            .setTitle("Advarsel")
                            .setIcon(R.drawable.ic_warning_yellow)
                            .setMessage("Året du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                            .show();
                    return;
                }
                if(year == currentYear){
                    if(month < currentMonth){
                        new AlertDialog.Builder(RoomDetailsActivity.this)
                                .setTitle("Advarsel")
                                .setIcon(R.drawable.ic_warning_yellow)
                                .setMessage("Måneden du valgte er i fortiden, venligst velg dagens dato eller en dato som ikke har vært enda")
                                .show();
                        return;
                    }
                    if(month == currentMonth){
                        if(day < currentDayOfMonth){
                            new AlertDialog.Builder(RoomDetailsActivity.this)
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
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", selected.getMarkerID());
        startActivity(intent);
        finish();
    }

    public void addReservation(View view) {
        Intent intent = new Intent(RoomDetailsActivity.this, CreateReservationActivity.class);
        intent.putExtra("roomID", selected.getRoomID());
        intent.putExtra("date", TVDate.getText().toString());
        startActivity(intent);
    }

    public void save(View view) {
        if(!Validator.validateNotEmpty(ETdescription.getText().toString(), this)){
            return;
        }
        new AlertDialog.Builder(RoomDetailsActivity.this)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage("Dette vil endre beskrivelsen til rommet du har valgt, er du sikker på at du ønsker å gjøre dette?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RoomDetailsActivity.this, RoomDetailsActivity.class);
                        intent.putExtra("roomID", selected.getRoomID());
                        Database.getInstance().updateRoom(selected.getRoomID(), ETdescription.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Nei", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void delete(View view) {
        new AlertDialog.Builder(RoomDetailsActivity.this)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage("Dette vil slette rommet du har valgt, er du sikker på at du ønsker å gjøre dette?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RoomDetailsActivity.this, MarkerDetailsActivity.class);
                        intent.putExtra("markerID", selected.getMarkerID());
                        Database.getInstance().deleteRoom(selected.getMarkerID());
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Nei", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
