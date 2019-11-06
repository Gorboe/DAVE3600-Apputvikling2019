package gorboe.com.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    EditText lokasjon;
    TextView koordinater;
    String minadresse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lokasjon = (EditText) findViewById(R.id.lokasjon);
        koordinater = (TextView) findViewById(R.id.koordinater);
        Button knapp = (Button) findViewById(R.id.hentkoordinater);
    }
    public void hentkoordinater(View v) {
        minadresse = lokasjon.getText().toString();
        GetLocationTask hentadresser = new GetLocationTask(minadresse);
        hentadresser.execute();
    }

    private class GetLocationTask extends AsyncTask<Void, Void, String> {
        JSONObject jsonObject;
        String address;
        String lokasjon;
        public GetLocationTask(String a) {
            this.address = a;
        }
        @Override
        protected String doInBackground(Void... params) {
            String s = "";
            String output = "";
            String query = "https://maps.googleapis.com/maps/api/geocode/json?address="
                    + address.replaceAll(" ", "%20") + "&key=AIzaSyDhdyUPI8PLaHGoWVOfC36oNGar8DMSKeU";
            try {
                URL urlen = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new
                        InputStreamReader((conn.getInputStream())));
                while ((s = br.readLine()) != null) {
                    output = output + s;
                }
                jsonObject = new JSONObject(output.toString());
                conn.disconnect();
                Double lon = Double.valueOf(0);
                Double lat = Double.valueOf(0);
                lon =
                        ((JSONArray)
                                jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                lat = ((JSONArray)
                        jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                lokasjon = String.valueOf(lon) + " : " + String.valueOf(lat);
                return lokasjon;
            }
            catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return lokasjon;
        }
        @Override
        protected void onPostExecute(String resultat) {
            koordinater.setText(resultat);
        }
    }
}