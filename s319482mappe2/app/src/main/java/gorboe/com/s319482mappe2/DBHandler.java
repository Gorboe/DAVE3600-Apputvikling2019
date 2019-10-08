package gorboe.com.s319482mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import gorboe.com.s319482mappe2.enteties.Restaurant;

public class DBHandler extends SQLiteOpenHelper {

    //Database
    private static String DATABASE_NAME = "Database";
    private static int DATABASE_VERSION = 1;

    //Restaurant table
    private static String TABLE_RESTAURANTS = "Restaurants";
    private static String KEY_RESTAURANT = "_restaurantID";
    private static String RESTAURANT_NAME = "navn";
    private static String RESTAURANT_ADDRESS = "address";
    private static String RESTAURANT_NUMBER = "number";
    private static String RESTAURANT_TYPE = "type";

    //Friends table TODO: IMPLEMENT FRIENDS TABLE
    private static String TABLE_FRIENDS = "Friends";
    private static String KEY_FRIENDSID = "";
    //+++

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS + "(" + KEY_RESTAURANT +
                " INTEGER PRIMARY KEY," + RESTAURANT_NAME + " TEXT," +
                RESTAURANT_ADDRESS + " TEXT," +
                RESTAURANT_NUMBER + " TEXT," +
                RESTAURANT_TYPE + " TEXT" + ")";

        //FRIENDS TABLE

        Log.d("SQL", CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_RESTAURANT_TABLE);
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
}
