package gorboe.com.s319482mappe2.enteties;

import androidx.annotation.NonNull;

public class Friend {
    private long _friendID;
    private String name;
    private String number;

    public Friend(long _friendID, String name, String number){
        this._friendID = _friendID;
        this.name = name;
        this.number = number;
    }

    public Friend(String name, String number){
        this.name = name;
        this.number = number;
    }

    public Friend(){

    }

    public long getFriendID() {
        return _friendID;
    }

    public void setFriendID(long _friendID) {
        this._friendID = _friendID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
