package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import gorboe.com.s319482mappe3.enteties.Marker;
import gorboe.com.s319482mappe3.enteties.Room;

public class MarkerDetailsActivity extends AppCompatActivity {

    private Marker selected;
    private ListView roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);

        roomList = findViewById(R.id.roomList);

        tryGetID();
        initializeRoomList();
    }

    private void tryGetID(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("markerID", -1);

        if(id != -1){
            System.out.println("TEST: " + id);
            selected = Database.getInstance().getMarker(id);
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
}
