package gorboe.com.s319482mappe3.enteties;

import androidx.annotation.NonNull;

public class Reservation {
    private int reservationID;
    private int roomID;
    private String date;
    private String time;

    public Reservation(int ReservationID, int RoomID, String Date, String Time){
        this.reservationID = ReservationID;
        this.roomID = RoomID;
        this.date = Date;
        this.time = Time;
    }

    public int getReservationID() {
        return reservationID;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @NonNull
    @Override
    public String toString() {
        return date + "\n" + time;
    }
}
