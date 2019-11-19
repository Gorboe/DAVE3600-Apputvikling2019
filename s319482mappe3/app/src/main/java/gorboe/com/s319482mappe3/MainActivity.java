package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private EditText input;
    private TextView output;
    private TextView xcord;
    private TextView ycord;
    private Server task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        xcord = findViewById(R.id.xcord);
        ycord = findViewById(R.id.ycord);

        Database.getInstance().GetAllItems();
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
        String xcoordinates = latLng.longitude + "";
        String ycoordinates = latLng.latitude + "";
        xcord.setText(xcoordinates);
        ycord.setText(ycoordinates);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void sendData(View view) {
        String text = input.getText().toString();
        task = new Server();
        task.execute(
                "http://student.cs.hioa.no/~s319482/jsonin.php/?Name=" + text,
                "http://student.cs.hioa.no/~s319482/jsonout.php");
    }
}
