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
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

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
    public void onMapClick(LatLng latLng) {
        x = latLng.longitude;
        y = latLng.latitude;
        String sx = "x: " + x;
        String sy = "y: " + y;
        TVx.setText(sx);
        TVy.setText(sy);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        LatLng sydney = new LatLng(-45, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void addRoom(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("keyX", x);
        intent.putExtra("keyY", y);
        startActivity(intent);
    }
}
