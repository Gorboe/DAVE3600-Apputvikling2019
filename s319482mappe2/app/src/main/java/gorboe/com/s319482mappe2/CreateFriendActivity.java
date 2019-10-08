package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe2.enteties.Friend;

public class CreateFriendActivity extends AppCompatActivity {

    private EditText navn_field;
    private EditText number_field;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend);

        navn_field = findViewById(R.id.friend_name);
        number_field = findViewById(R.id.friend_number);
        db = new DBHandler(this);
    }

    public void addFriendToDatabase(View view) {
        Friend friend = new Friend(navn_field.getText().toString(), number_field.getText().toString());
        db.addFriend(friend);
        System.out.println("FRIEND ADDED TO DB");
    }
}
