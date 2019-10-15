package gorboe.com.s319482mappe2.enteties;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Order {
    private long _orderID;
    private Restaurant restaurant;
    private String date;
    private String time;
    private List<Friend> friends;

    public Order(long _orderID, Restaurant restaurant, String date, String time, List<Friend> friends){
        this._orderID = _orderID;
        this.restaurant = restaurant;
        this.date = date;
        this.time = time;
        this.friends = friends;
    }

    public Order(Restaurant restaurant, String date, String time, List<Friend> friends){
        this.restaurant = restaurant;
        this.date = date;
        this.time = time;
        this.friends = friends;
    }

    public Order(){

    }

    public long get_orderID() {
        return _orderID;
    }

    public void set_orderID(long _orderID) {
        this._orderID = _orderID;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public String toString() {
        return restaurant.getName() + "\t" + restaurant.getType() + "\nDate: " + date + "\nTime: " + time;
    }
}
