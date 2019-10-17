package gorboe.com.testavbokcp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String PROVIDER="gorboe.com.s319482mappe2";
    public static final Uri CONTENT_URI= Uri.parse("content://"+ PROVIDER + "/restaurant");

    public static final String RESTAURANT_NAME = "name";
    private static String RESTAURANT_ADDRESS = "address";
    private static String RESTAURANT_NUMBER = "number";
    private static String RESTAURANT_TYPE = "type";
    public static final String ID="_restaurantID";
    EditText name;
    private EditText address;
    private EditText number;
    private EditText type;
    TextView visbok;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        type = findViewById(R.id.type);
        Button leggtil=(Button)findViewById(R.id.leggtil);
        edit = findViewById(R.id.edit);
        visbok=(TextView)findViewById(R.id.vis);
    }
    public void leggtil(View v){
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, name.getText().toString());
        values.put(RESTAURANT_ADDRESS, address.getText().toString());
        values.put(RESTAURANT_NUMBER, number.getText().toString());
        values.put(RESTAURANT_TYPE, type.getText().toString());
        Uri uri = getContentResolver().insert( CONTENT_URI, values);
        name.setText("");
        address.setText("");
        number.setText("");
        type.setText("");
        visalle(null);
    }

    public void visalle(View v) {
        String tekst;
        tekst = "";
        Cursor cur =getContentResolver().query(CONTENT_URI, null, null, null, null);

        if(cur == null){
            System.out.println("CURSOR IS NULL!!");
            return;
        }

        if (cur.moveToFirst()) {
            do {
                tekst = tekst + (cur.getString(0)) + " " + (cur.getString(1)) + " " + (cur.getString(2)) + " " +
                        (cur.getString(3)) + " " + "\r\n";
                System.out.println("BEVEGER SEG!");
            }
            while (cur.moveToNext());
            cur.close();
            visbok.setText(tekst);
        }
    }

    public void oppdater(View view) {
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, name.getText().toString());
        values.put(RESTAURANT_ADDRESS, address.getText().toString());
        values.put(RESTAURANT_NUMBER, number.getText().toString());
        values.put(RESTAURANT_TYPE, type.getText().toString());
        getContentResolver().update(Uri.parse(CONTENT_URI + "/" + edit.getText().toString()), values, null, null);
        visalle(null);
    }

    public void slett(View view) {
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + edit.getText().toString()), null, null);
        visalle(null);
    }
}
