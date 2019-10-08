package gorboe.com.s319482mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import gorboe.com.s319482mappe2.enteties.Friend;

public class FriendsActivity extends AppCompatActivity {

    private DBHandler db;
    private ListView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        db = new DBHandler(this);
        friendList = findViewById(R.id.friendList);
        initializeFriendList();
    }

    public void initializeFriendList(){
        final List<Friend> friends = db.getAllFriends();

        ArrayAdapter<Friend> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, friends);

        friendList.setAdapter(arrayAdapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //position = the item pressed. first list item starts at 0
                Intent intent = new Intent(FriendsActivity.this, CreateFriendActivity.class);
                intent.putExtra("selected_friend_ID", friends.get(position).getFriendID());
                startActivity(intent);
            }
        });
    }


    public void startCreateFriendActivity(View view) {
        startActivity(new Intent(this, CreateFriendActivity.class));
    }
}
