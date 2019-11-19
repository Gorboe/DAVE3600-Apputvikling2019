package gorboe.com.s319482mappe3.enteties;

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
}
