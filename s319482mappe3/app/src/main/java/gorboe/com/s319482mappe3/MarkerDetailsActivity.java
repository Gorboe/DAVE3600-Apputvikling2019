package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import gorboe.com.s319482mappe3.enteties.Marker;
import gorboe.com.s319482mappe3.enteties.Room;

public class MarkerDetailsActivity extends AppCompatActivity {

    private Marker selected;
    private ListView roomList;
    private EditText ETx;
    private EditText ETy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);

        roomList = findViewById(R.id.roomList);
        ETx = findViewById(R.id.tvx);
        ETy = findViewById(R.id.tvy);

        tryGetID();
        initializeRoomList();
    }

    private void tryGetID(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("markerID", -1);

        if(id != -1){
            selected = Database.getInstance().getMarker(id);
            String x = selected.getCoordinateX() + "";
            String y = selected.getCoordinateY() + "";
            ETx.setText(x);
            ETy.setText(y);
        }
    }

    public void initializeRoomList(){
        final List<Room> rooms = selected.getRooms();
        ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, rooms);

        roomList.setAdapter(arrayAdapter);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Intent intent = new Intent(MarkerDetailsActivity.this, RoomDetailsActivity.class);
                intent.putExtra("roomID", rooms.get(position).getRoomID());
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

    public void addRoom(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("markerID", selected.getMarkerID());
        startActivity(intent);
        finish();
    }

    public void save(View view) {
        if(!Validator.validateCoordinate(ETx.getText().toString(), this) ||
           !Validator.validateCoordinate(ETy.getText().toString(), this)){
            return;
        }

        new AlertDialog.Builder(MarkerDetailsActivity.this)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage("Dette vil endre koordinatene til markøren du har valgt, er du sikker på at du ønsker å gjøre dette?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database.getInstance().updateMarker(ETx.getText().toString(), ETy.getText().toString(), selected.getMarkerID());
                        Intent intent = new Intent(MarkerDetailsActivity.this, MarkerDetailsActivity.class);
                        intent.putExtra("markerID", selected.getMarkerID());
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
        new AlertDialog.Builder(MarkerDetailsActivity.this)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage("Dette vil slette markøren du har valgt, er du sikker på at du ønsker å gjøre dette?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database.getInstance().deleteMarker(selected.getMarkerID());
                        startActivity(new Intent(MarkerDetailsActivity.this, MainActivity.class));
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
