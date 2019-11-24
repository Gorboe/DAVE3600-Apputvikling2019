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

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private HashMap<Marker, Integer> markerHashMap;
    private TextView TVx;
    private TextView TVy;
    private double x;
    private double y;

    //TODO: DESIGN STUFF
    //TODO: SORT RESERVATION BY DATE->HOUR
    //TODO: ROOM DETAILS, SHOW ROOM NAME AND MAKE IT POSSIBLE TO EDIT ROOM/DELETE
    //TODO: VALIDATION (VERY IMPORTANT FOR ANY INPUT FIELD DO NOT ALLOW & AS THIS WILL FUCK UP PHP)
    //TODO: MENTION IN BRIEF THAT IN MARKERS WE CAN HAVE MANY ROOMS BUT THAT ROOM NAMES DETERMINE WHAT FLOOR THEY ARE ON AND NO VALIDATION ON THIS BECAUSE OF LITTLE TIME.
    //TODO: ICON
    //TODO: DELETE AND UPDATE MARKERS.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerHashMap = new HashMap<>();
        TVx = findViewById(R.id.xcord);
        TVy = findViewById(R.id.ycord);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        System.out.println("STUFF: " + Database.getInstance().getMarkers());
        for(gorboe.com.s319482mappe3.enteties.Marker marker: Database.getInstance().getMarkers()){
            if(marker == null) continue;
            LatLng pos = new LatLng(marker.getCoordinateY(), marker.getCoordinateX());
            Marker m = mMap.addMarker(new MarkerOptions().position(pos));
            System.out.println("ID: " + marker.getMarkerID());
            markerHashMap.put(m, marker.getMarkerID());
        }

        mMap.setMinZoomPreference(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(59.91943817362552, 10.7355922879354954), 17));
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
        Integer id = markerHashMap.get(marker);
        System.out.println(id);
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", id);
        startActivity(intent);
        finish();
        return false;
    }

    public void addMarker(View view) {
        if(TVx.getText().toString().isEmpty() || TVy.getText().toString().isEmpty()){
            //TODO: MSG BOX TO USER THAT YOU NEED TO PRESS SOMEWHERE FIRST
        }
        Database.getInstance().addMarker(x, y);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
