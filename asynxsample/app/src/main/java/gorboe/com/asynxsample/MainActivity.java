package gorboe.com.asynxsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.TextView01);
    }
    private class LastSide extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String s = "";
            String hele = "";
            for (String url : urls) {
                try {
                    URL minurl = new URL(urls[0]);
                    HttpURLConnection con = (HttpURLConnection) minurl.openConnection();
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(con.getInputStream()));
                    while ((s = in.readLine()) != null) {
                        hele = hele + s;
                    }
                    in.close();
                    con.disconnect();
                    return hele;
                } catch (Exception e) {
                    return "Noe gikk feil";
                }
            }
            return hele;
        }
        @Override
        protected void onPostExecute(String ss) {
            textView.setText(ss);
        }
    }
    public void readWebpage(View view) {
        LastSide task = new LastSide();
        task.execute(new String[]{"https://www.oslomet.no"});
    }
}
