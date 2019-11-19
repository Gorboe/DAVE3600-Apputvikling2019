package gorboe.com.s319482mappe3;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import gorboe.com.s319482mappe3.enteties.Room;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private TextView TVx;
    private TextView TVy;
    private double x;
    private double y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TVx = findViewById(R.id.xcord);
        TVy = findViewById(R.id.ycord);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

        //todo: get all rooms and add them to map
        //todo: zoom in on oslo
        for(Room room: Database.getInstance().getRooms()){
            System.out.println("Adding marker: " + room.getCoordinateX() + " " + room.getCoordinateY());
            LatLng marker = new LatLng(room.getCoordinateY(), room.getCoordinateX());
            mMap.addMarker(new MarkerOptions().position(marker).title(room.getDescription()));
        }

        LatLng sydney = new LatLng(-45, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        x = latLng.longitude;
        y = latLng.latitude;
        String sx = "x: " + x;
        String sy = "y: " + y;
        TVx.setText(sx);
        TVy.setText(sy);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println("works");
        return false;
    }

    public void addRoom(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("keyX", x);
        intent.putExtra("keyY", y);
        startActivity(intent);
    }
}
