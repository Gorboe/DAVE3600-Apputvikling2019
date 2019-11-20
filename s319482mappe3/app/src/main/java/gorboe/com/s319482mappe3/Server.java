package gorboe.com.s319482mappe3;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server extends AsyncTask<String, Void,String> {
    @Override
    protected String doInBackground(String... urls){
        StringBuilder output = new StringBuilder();
        for (String s : urls) {
            try {
                URL url = new URL(s); //get the url your working with
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + connection.getResponseCode());
                }
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);

                String temp;
                while ((temp = reader.readLine()) != null) {
                    output.append(temp);
                }
                connection.disconnect();
            } catch (Exception e) {
                return "noe gikk feil";
            }
        }

        return output.toString();
    }
}
