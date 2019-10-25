package gorboe.com.s319482mappe2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.List;

import gorboe.com.s319482mappe2.core.DBHandler;
import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.create.CreateFriendActivity;
import gorboe.com.s319482mappe2.enteties.Friend;

public class FriendsActivity extends AppCompatActivity {

    private DBHandler db;
    private ListView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mymenu);
        setActionBar(toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_order:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.nav_restaurant:
                finish();
                startActivity(new Intent(this, RestaurantActivity.class));
                break;
            case R.id.nav_friends:
                finish();
                startActivity(new Intent(this, FriendsActivity.class));
                break;
            case R.id.nav_settings:
                finish();
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
