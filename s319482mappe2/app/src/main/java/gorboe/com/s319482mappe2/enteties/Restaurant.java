package gorboe.com.s319482mappe2.enteties;

import androidx.annotation.NonNull;

public class Restaurant {

    private long _restaurantID;
    private String name;
    private String address;
    private String number;
    private String type;

    public Restaurant(long _restaurantID, String name, String address, String number, String type){
        this._restaurantID = _restaurantID;
        this.name = name;
        this.address = address;
        this.number = number;
        this.type = type;
    }

    public Restaurant(String name, String address, String number, String type){
        this.name = name;
        this.address = address;
        this.number = number;
        this.type = type;
    }

    public Restaurant(){

    }

    public long getRestaurantID() {
        return _restaurantID;
    }

    public void setRestaurantID(long _restaurantID) {
        this._restaurantID = _restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
