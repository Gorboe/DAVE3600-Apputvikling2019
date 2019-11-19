package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText xfield;
    private EditText yfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        xfield = findViewById(R.id.xfield);
        yfield = findViewById(R.id.yfield);

        tryGetCoordinates();
    }

    private void tryGetCoordinates(){
        Intent intent = getIntent();
        double coordinateX = intent.getDoubleExtra("keyX", -1);
        double coordinateY = intent.getDoubleExtra("keyY", -1);

        if(coordinateX != -1 && coordinateY != -1){
            String x = coordinateX + "";
            String y = coordinateY + "";
            xfield.setText(x);
            yfield.setText(y);
        }
    }
}
