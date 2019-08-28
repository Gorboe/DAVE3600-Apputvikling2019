package gorboe.com.flagg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNorsk = findViewById(R.id.btnNorsk);
        Button btnTysk = findViewById(R.id.btnTysk);

        btnNorsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("CHANGE TO NO");
                setLocale("no");
            }
        });

        btnTysk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println("CHANGE TO DE");
            }
        });
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
}
