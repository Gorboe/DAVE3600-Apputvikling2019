package gorboe.com.s319482mappe3.enteties;

import androidx.annotation.NonNull;

public class Reservation {
    private int reservationID;
    private int roomID;
    private String date;
    private String timeFrom;
    private String timeTo;
    private String name;
    private String description;

    public Reservation(int ReservationID, int RoomID, String Date, String timeFrom, String timeTo, String name, String description){
        this.reservationID = ReservationID;
        this.roomID = RoomID;
        this.date = Date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.name = name;
        this.description = description;
    }

    public Reservation(int roomID, String date, String timeFrom, String timeTo, String name, String description){
        this.roomID = roomID;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.name = name;
        this.description = description;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getDate() {
        return date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String toString() {
        return date + "\n" + timeFrom;
    }
}
