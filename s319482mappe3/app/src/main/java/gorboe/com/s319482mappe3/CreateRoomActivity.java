package gorboe.com.s319482mappe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText ETx;
    private EditText ETy;
    private EditText ETdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        ETx = findViewById(R.id.xfield);
        ETy = findViewById(R.id.yfield);
        ETdescription = findViewById(R.id.description);

        tryGetCoordinates();
    }

    private void tryGetCoordinates(){
        Intent intent = getIntent();
        double coordinateX = intent.getDoubleExtra("keyX", -9999);
        double coordinateY = intent.getDoubleExtra("keyY", -9999);

        if(coordinateX != -9999 && coordinateY != -9999){
            String x = coordinateX + "";
            String y = coordinateY + "";
            ETx.setText(x);
            ETy.setText(y);
        }
    }

    public void addRoom(View view) {
        //todo: validate here?? also check x and y if exist marker at that loc?
        String description = ETdescription.getText().toString();
        double x = Double.parseDouble(ETx.getText().toString());
        double y = Double.parseDouble(ETy.getText().toString());
        Database.getInstance().AddRoom(description, x, y);
        startActivity(new Intent(this, MainActivity.class));
    }
}
