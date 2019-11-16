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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText input;
    private TextView output;
    public boolean isTaskRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);

        getJSON task = new getJSON();
        task.execute(
                "http://student.cs.hioa.no/~s319482/jsonin.php/?Name=coolstuff",
                "http://student.cs.hioa.no/~s319482/jsonout.php");

        //task.execute("http://student.cs.hioa.no/~s319482/jsonin.php/?Name=detduleggerinn");

        //task.execute("http://student.cs.hioa.no/~s319482/jsonout.php");
        //Add data: http://student.cs.hioa.no/~s319482/jsonin.php/?Name=detduleggerinn
        //Get data: http://student.cs.hioa.no/~s319482/jsonout.php
    }

    private class getJSON extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... urls){
            StringBuilder output = new StringBuilder();
            for(int i = 0; i < urls.length; i++){
                try{
                    URL url = new URL(urls[i]); //get the url your working with
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    if(connection.getResponseCode() != 200){
                        throw new RuntimeException("Failed : HTTP error code : "
                                + connection.getResponseCode());
                    }
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);

                    String temp;
                    while((temp = reader.readLine()) != null){
                        output.append(temp);
                    }
                    connection.disconnect();
                }catch (Exception e){
                    return "noe gikk feil";
                }
            }

            return output.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            output.setText(result);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void sendData(View view) {
        String text = input.getText().toString();
    }

    private void getData(){

    }
}
