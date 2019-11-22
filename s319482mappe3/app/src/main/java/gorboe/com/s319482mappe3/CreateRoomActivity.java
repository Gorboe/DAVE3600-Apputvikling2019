package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe3.enteties.Marker;
import gorboe.com.s319482mappe3.enteties.Room;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText ETx;
    private EditText ETy;
    private Marker selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        ETx = findViewById(R.id.cordx);
        ETy = findViewById(R.id.cordy);

        tryGetCoordinates();
        tryGetRoom();
    }

    private void tryGetRoom(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("roomID", -1);

        if(id != -1){
            selected = Database.getInstance().getMarker(id);
            String x = selected.getCoordinateX() + "";
            String y = selected.getCoordinateY() + "";
            ETx.setText(x);
            ETy.setText(y);
        }
    }

    private void tryGetCoordinates(){
        Intent intent = getIntent();
        double coordinateX = intent.getDoubleExtra("keyX", -9999);
        double coordinateY = intent.getDoubleExtra("keyY", -9999);

        if(coordinateX != -9999 && coordinateY != -9999){
            String x = coordinateX + "";
            String y = coordinateY + "";
            ETx.setText(x);
            ETy.setText(y);
        }
    }

    public void addRoom(View view) {
        //todo: validate here?? also check x and y if exist marker at that loc?

        double x = Double.parseDouble(ETx.getText().toString());
        double y = Double.parseDouble(ETy.getText().toString());
        //Database.getInstance().addRoom(description, x, y);
        startActivity(new Intent(this, MainActivity.class));
    }

    //TODO: DELETE ROOM REMEMBER TO ALSO DELETE ALL RESERVATIONS UNDER THAT ROOM.

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void delete(View view) {
        if(selected != null){
            //Database.getInstance().deleteRoom(selected.getRoomID());
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
