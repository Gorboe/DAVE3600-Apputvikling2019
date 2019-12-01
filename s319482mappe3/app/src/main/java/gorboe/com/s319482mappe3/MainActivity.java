package gorboe.com.s319482mappe3;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
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
        for(gorboe.com.s319482mappe3.enteties.Marker marker: Database.getInstance().getMarkers()){
            if(marker == null) continue;
            LatLng pos = new LatLng(marker.getCoordinateY(), marker.getCoordinateX());
            Marker m = mMap.addMarker(new MarkerOptions().position(pos));
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
        Intent intent = new Intent(this, MarkerDetailsActivity.class);
        intent.putExtra("markerID", id);
        startActivity(intent);
        finish();
        return false;
    }

    public void addMarker(View view) {
        if(TVx.getText().toString().isEmpty() || TVy.getText().toString().isEmpty()){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow)
                    .setMessage("Du må først trykke på kartet der du ønsker din markør for å hente ut koordinatene")
                    .show();
            return;
        }
        Database.getInstance().addMarker(x, y);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
