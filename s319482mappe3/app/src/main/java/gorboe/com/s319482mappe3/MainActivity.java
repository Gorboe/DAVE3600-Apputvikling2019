package gorboe.com.s319482mappe3;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private TextView xcord;
    private TextView ycord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        xcord = findViewById(R.id.xcord);
        ycord = findViewById(R.id.ycord);


        //task = new Server();
        /*try{
            String result = task.execute("http://student.cs.hioa.no/~s319482/jsonout.php").get();
            output.setText(result);
        }catch (Exception e){
            //catch
            System.out.println("noe gikk veldig galt");
        }*/


        //mMap.setOnMarkerClickListener();
        //Add data: http://student.cs.hioa.no/~s319482/jsonin.php/?Name=detduleggerinn
        //Get data: http://student.cs.hioa.no/~s319482/jsonout.php
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String xcoordinates = "x: " + latLng.longitude + "";
        String ycoordinates = "y: " + latLng.latitude + "";
        xcord.setText(xcoordinates);
        ycord.setText(ycoordinates);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        LatLng sydney = new LatLng(-45, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
