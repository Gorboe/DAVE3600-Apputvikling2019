package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe3.enteties.Room;

public class CreateRoomActivity extends AppCompatActivity {

    //TODO: maybe as a popup? because all we need is description
    private EditText ETdescription;
    private Room current;
    private int markerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        ETdescription = findViewById(R.id.description);

        tryGetRoom();
    }

    private void tryGetRoom(){
        Intent intent = getIntent();
        int mid = intent.getIntExtra("markerID", -1);
        int id = intent.getIntExtra("roomID", -1);

        //if old room
        if(id != -1){
            current = Database.getInstance().getRoom(id);
            ETdescription.setText(current.getDescription());
        }

        //if new room
        if(mid != -1){
            markerID = mid;
        }
    }

    //(Save)
    public void addRoom(View view) {
        //TODO: validate
        if(current != null){
            //UPDATE
            markerID = current.getMarkerID();
            current.setDescription(ETdescription.getText().toString());
            Database.getInstance().updateRoom(current);
        }else{
            //ADD
            Database.getInstance().addRoom(ETdescription.getText().toString(), markerID);
        }
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", markerID);
        startActivity(intent);
        finish();
    }

    //(Exit)
    public void delete(View view) {
        if(current != null){
            //DELETE
            markerID = current.getMarkerID();
            Database.getInstance().deleteRoom(current.getRoomID());
        }

        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", markerID);
        startActivity(intent);
        finish();
    }
}
