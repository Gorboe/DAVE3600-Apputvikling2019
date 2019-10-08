package gorboe.com.s319482mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Restaurant;

public class DBHandler extends SQLiteOpenHelper {

    //Database
    private static String DATABASE_NAME = "Database";
    private static int DATABASE_VERSION = 1;

    //Restaurant table
    private static String TABLE_RESTAURANTS = "Restaurants";
    private static String KEY_RESTAURANT = "_restaurantID";
    private static String RESTAURANT_NAME = "name";
    private static String RESTAURANT_ADDRESS = "address";
    private static String RESTAURANT_NUMBER = "number";
    private static String RESTAURANT_TYPE = "type";

    //Friends table
    private static String TABLE_FRIENDS = "Friends";
    private static String KEY_FRIEND = "_friendID";
    private static String FRIEND_NAME = "name";
    private static String FRIEND_NUMBER = "number";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //RESTAURANT TABLE
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "(" + KEY_RESTAURANT +
                " INTEGER PRIMARY KEY," + RESTAURANT_NAME + " TEXT," +
                RESTAURANT_ADDRESS + " TEXT," +
                RESTAURANT_NUMBER + " TEXT," +
                RESTAURANT_TYPE + " TEXT" + ")";
        Log.d("SQL", CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_RESTAURANT_TABLE);

        //FRIENDS TABLE
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "(" + KEY_FRIEND +
                " INTEGER PRIMARY KEY," + FRIEND_NAME + " TEXT," +
                FRIEND_NUMBER + " TEXT" + ")";
        Log.d("SQL", CREATE_FRIENDS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS + TABLE_FRIENDS);
        onCreate(db);
    }

    public void addRestaurant(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, restaurant.getName());
        values.put(RESTAURANT_ADDRESS, restaurant.getAddress());
        values.put(RESTAURANT_NUMBER, restaurant.getNumber());
        values.put(RESTAURANT_TYPE, restaurant.getType());
        db.insert(TABLE_RESTAURANTS, null, values);
        db.close();
    }

    public void addFriend(Friend friend){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FRIEND_NAME, friend.getName());
        values.put(FRIEND_NUMBER, friend.getNumber());
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }
}
