package gorboe.com.mindb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText navninn;
    private EditText telefoninn;
    private EditText idinn;
    private TextView utskrift;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navninn = (EditText) findViewById(R.id.navn);
        telefoninn = (EditText) findViewById(R.id.telefon);
        idinn = (EditText) findViewById(R.id.min_id);
        utskrift = (TextView) findViewById(R.id.utskrift);
        db = new DBHandler(this);

    }


    public void oppdater(View view) {
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navninn.getText().toString());
        kontakt.setTelefon(telefoninn.getText().toString());
        kontakt.set_ID(Long.parseLong(idinn.getText().toString()));
        db.oppdaterKontakt(kontakt);

    }

    public void visalle(View view) {
        String tekst = "";
        List<Kontakt> kontakter = db.finnAlleKontakter();
        for (Kontakt kontakt : kontakter) {
            tekst = tekst + "Id: " + kontakt.get_ID() + ",Navn: " +
                    kontakt.getNavn() + " ,Telefon: " +
                    kontakt.getTelefon();
            Log.d("Navn: ", tekst);
        }
        utskrift.setText(tekst);
    }

    public void slett(View view) {
        Long kontaktid = (Long.parseLong(idinn.getText().toString()));
        db.slettKontakt(kontaktid);
    }

    public void leggtil(View view) {
        Kontakt kontakt = new Kontakt(navninn.getText().toString(),
                telefoninn.getText().toString());
        db.leggTilKontakt(kontakt);
        Log.d("Legg inn: ", "legger til kontakter");
    }
}
