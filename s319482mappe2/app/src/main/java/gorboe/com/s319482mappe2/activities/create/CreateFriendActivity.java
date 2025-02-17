package gorboe.com.s319482mappe2.activities.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.FriendsActivity;
import gorboe.com.s319482mappe2.core.Validator;
import gorboe.com.s319482mappe2.enteties.Friend;

public class CreateFriendActivity extends AppCompatActivity {

    private EditText name_field;
    private EditText number_field;
    private DBHandler db;
    private Friend existing_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend);

        name_field = findViewById(R.id.friend_name);
        number_field = findViewById(R.id.friend_number);
        db = new DBHandler(this);

        tryGetFriend();
    }

    public void tryGetFriend(){
        Intent intent = getIntent();
        long friend_id = intent.getLongExtra("selected_friend_ID", -1);

        if(friend_id != -1){
            existing_friend = db.getFriend(friend_id);
            name_field.setText(existing_friend.getName());
            number_field.setText(existing_friend.getNumber());
        }
    }

    public void saveFriend(View view) {
        if(!Validator.validatePhoneNumber(number_field.getText().toString(), this) ||
           !Validator.validateName(name_field.getText().toString(), this)){
            return;
        }
        String number = number_field.getText().toString().replaceAll("\\s+", ""); //removes whitespace
        Friend friend = new Friend(name_field.getText().toString(), number);

        if(existing_friend != null){
            friend.setFriendID(existing_friend.getFriendID());
            db.updateFriend(friend);
        }else{
            db.addFriend(friend);
        }
        finish();
        startActivity(new Intent(this, FriendsActivity.class));
    }

    public void deleteFriend(View view) {
        if(existing_friend != null){
            db.deleteFriend(existing_friend.getFriendID());
        }
        finish();
        startActivity(new Intent(this, FriendsActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
