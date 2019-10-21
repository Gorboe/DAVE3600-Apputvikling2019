package gorboe.com.testavrestaurantcp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String PROVIDER="gorboe.com.s319482mappe2";
    public static final Uri CONTENT_URI= Uri.parse("content://"+ PROVIDER + "/restaurant");

    //DB columns
    public static final String RESTAURANT_NAME = "name";
    private static String RESTAURANT_ADDRESS = "address";
    private static String RESTAURANT_NUMBER = "number";
    private static String RESTAURANT_TYPE = "type";

    private EditText name;
    private EditText address;
    private EditText number;
    private EditText type;
    private TextView vis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name= findViewById(R.id.name);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        type = findViewById(R.id.type);
        vis = findViewById(R.id.vis);
    }

    public void leggtil(View view) {
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, name.getText().toString());
        values.put(RESTAURANT_ADDRESS, address.getText().toString());
        values.put(RESTAURANT_NUMBER, number.getText().toString());
        values.put(RESTAURANT_TYPE, type.getText().toString());
        Uri uri = getContentResolver().insert( CONTENT_URI, values);
        if(uri == null){
            new AlertDialog.Builder(this)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow)
                    .setMessage("Noe gikk galt, restauranten ble ikke lagt til. Sørg for at navn " +
                            "er fylt inn og består bare av store og små bokstaver. Sørg for at adresse " +
                            "feltet er fylt inn. Sørg for at nummer feltet er fylt inn og bare består " +
                            " 8 tall. Sørg for at type feltet er fylt inn.")
                    .show();
            return;
        }
        name.setText("");
        address.setText("");
        number.setText("");
        type.setText("");
        visalle(null);
    }

    public void visalle(View view) {
        String tekst;
        tekst = "";
        Cursor cur =getContentResolver().query(CONTENT_URI, null, null, null, null);

        if(cur == null){
            return;
        }

        if (cur.moveToFirst()) {
            do {
                tekst = tekst + (cur.getString(0)) + " " + (cur.getString(1)) + " " + (cur.getString(2)) + " " +
                        (cur.getString(3)) + " " + "\r\n";
            }
            while (cur.moveToNext());
            cur.close();
            vis.setText(tekst);
        }
    }
}
