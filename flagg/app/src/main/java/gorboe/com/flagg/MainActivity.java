package gorboe.com.flagg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Locale locale;
    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNorsk = findViewById(R.id.btnNorsk);
        Button btnTysk = findViewById(R.id.btnTysk);
        temp = findViewById(R.id.temp);



        //temp.setText(locale.getLanguage());

        btnNorsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("CHANGE TO NO");
                setLocale("nb");
                recreate();
            }
        });

        btnTysk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("CHANGE TO DE");
                setLocale("de");
                recreate();
            }
        });
    }

    private void setLocale(String lang){
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //temp
        //locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0); //current language on phone
        temp.setText(locale.getLanguage());
    }
}
