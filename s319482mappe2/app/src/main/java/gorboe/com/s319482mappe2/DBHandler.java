package gorboe.com.s319482mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gorboe.com.s319482mappe2.enteties.Friend;
import gorboe.com.s319482mappe2.enteties.Order;
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

    //Order table
    private static String TABLE_ORDERS = "Orders";
    private static String KEY_ORDER = "_orderID";
    private static String ORDER_RESTAURANT_KEY = "restaurantFK";//private static String ORDER_RESTAURANT = "restaurant"; //restaurants...
    private static String ORDER_DATE = "date";
    private static String ORDER_TIME = "time";
    //friends...

    //OrderPersonDetails
    private static String TABLE_ORDER_PERSON_DETAILS = "OrderPersonDetails";
    private static String KEY_ORDER_PERSON_DETAILS = "orderFK"; //KEY_ORDER
    private static String KEY_ORDER_PERSON_DETAILS_FRIEND = "friendFK"; //KEY_FRIEND

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

        //ORDER TABLE
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDERS + "(" + KEY_ORDER +
                " INTEGER PRIMARY KEY," + ORDER_RESTAURANT_KEY + " TEXT," + ORDER_DATE + " TEXT," +
                ORDER_TIME + " TEXT" + ")";
        Log.d("SQL", CREATE_ORDER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);

        //ORDER PERSON DETAILS TABLE
        String CREATE_ORDER_PERSON_DETAILS_TABLE = "CREATE TABLE " + TABLE_ORDER_PERSON_DETAILS +
                "(" + KEY_ORDER_PERSON_DETAILS + "," + KEY_ORDER_PERSON_DETAILS_FRIEND + " INTEGER PRIMARY KEY" + ")"; //TODO: TRENGER BEGGE Å VÆRE KEY??
        Log.d("SQL", CREATE_ORDER_PERSON_DETAILS_TABLE);
        db.execSQL(CREATE_ORDER_PERSON_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_PERSON_DETAILS);
        onCreate(db);
    }

    /**ORDER METHODS**/
    public void addOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_RESTAURANT_KEY, order.getRestaurant().getRestaurantID());
        values.put(ORDER_DATE, order.getDate());
        values.put(ORDER_TIME, order.getTime());

        System.out.println("VALUES: " + values);
        System.out.println("Friends: " + order.getFriends());
        db.insert(TABLE_ORDERS, null, values);
        addOrderPersonDetails(order);
        db.close();
    }

    private void addOrderPersonDetails(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //loop over friends list
        for(Friend friend: order.getFriends()){
            //TODO: OR PUT HERE...
            values.put(KEY_ORDER_PERSON_DETAILS, "CORRECTKEY!!"); //so we get equal amount of ORDERIDS? //TODO:TEST this
            values.put(KEY_ORDER_PERSON_DETAILS_FRIEND, friend.getFriendID());
            System.out.println("IN FOR LOOP");
            //insert here
            db.insert(TABLE_ORDER_PERSON_DETAILS, null, values);
        }
    }

    /**RESTAURANT METHODS**/
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

    public Restaurant getRestaurant(long restaurant_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RESTAURANTS + " WHERE " + KEY_RESTAURANT
                + " = " + restaurant_id;

        Log.d("SQL", query);

        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantID(c.getInt(c.getColumnIndex(KEY_RESTAURANT)));
        restaurant.setName(c.getString(c.getColumnIndex(RESTAURANT_NAME)));
        restaurant.setAddress(c.getString(c.getColumnIndex(RESTAURANT_ADDRESS)));
        restaurant.setNumber(c.getString(c.getColumnIndex(RESTAURANT_NUMBER)));
        restaurant.setType(c.getString(c.getColumnIndex(RESTAURANT_TYPE)));

        db.close();
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Restaurant> restaurants = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_RESTAURANTS;

        Log.d("SQL", query);

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantID(c.getInt(c.getColumnIndex(KEY_RESTAURANT)));
                restaurant.setName(c.getString(c.getColumnIndex(RESTAURANT_NAME)));
                restaurant.setAddress(c.getString(c.getColumnIndex(RESTAURANT_ADDRESS)));
                restaurant.setNumber(c.getString(c.getColumnIndex(RESTAURANT_NUMBER)));
                restaurant.setType(c.getString(c.getColumnIndex(RESTAURANT_TYPE)));

                restaurants.add(restaurant);
            }while(c.moveToNext());
            c.close();
            db.close();
        }

        return restaurants;
    }

    public void deleteRestaurant(long restaurant_id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_RESTAURANTS, KEY_RESTAURANT + " = ?",
                new String[]{ String.valueOf(restaurant_id) });
        db.close();
    }

    public int updateRestaurant(Restaurant restaurant){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, restaurant.getName());
        values.put(RESTAURANT_ADDRESS, restaurant.getAddress());
        values.put(RESTAURANT_NUMBER, restaurant.getNumber());
        values.put(RESTAURANT_TYPE, restaurant.getType());
        int changed = db.update(TABLE_RESTAURANTS, values, KEY_RESTAURANT + " = ?",
                new String[] { String.valueOf(restaurant.getRestaurantID()) });
        db.close();
        return changed;
    }

    /**FRIENDS METHODS**/
    public void addFriend(Friend friend){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FRIEND_NAME, friend.getName());
        values.put(FRIEND_NUMBER, friend.getNumber());
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }

    public Friend getFriend(long friend_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " + KEY_FRIEND
                + " = " + friend_id;

        Log.d("SQL", query);

        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
        }

        Friend friend = new Friend();
        friend.setFriendID(c.getInt(c.getColumnIndex(KEY_FRIEND)));
        friend.setName(c.getString(c.getColumnIndex(FRIEND_NAME)));
        friend.setNumber(c.getString(c.getColumnIndex(FRIEND_NUMBER)));

        db.close();
        return friend;
    }

    public List<Friend> getAllFriends(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Friend> friends = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FRIENDS;

        Log.d("SQL", query);

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Friend friend = new Friend();
                friend.setFriendID(c.getInt(c.getColumnIndex(KEY_FRIEND)));
                friend.setName(c.getString(c.getColumnIndex(FRIEND_NAME)));
                friend.setNumber(c.getString(c.getColumnIndex(FRIEND_NUMBER)));

                friends.add(friend);
            }while(c.moveToNext());
            c.close();
            db.close();
        }

        return friends;
    }

    public void deleteFriend(long friend_id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_FRIENDS, KEY_FRIEND + " = ?",
                new String[]{ String.valueOf(friend_id) });
        db.close();
    }

    public int updateFriend(Friend friend){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FRIEND_NAME, friend.getName());
        values.put(FRIEND_NUMBER, friend.getNumber());

        int changed = db.update(TABLE_FRIENDS, values, KEY_FRIEND + " = ?",
                new String[] { String.valueOf(friend.getFriendID()) });
        db.close();
        return changed;
    }
}
