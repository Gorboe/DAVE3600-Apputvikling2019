package gorboe.com.s319482mappe3.enteties;

import java.util.List;

public class Room {
    private int roomID;
    private String description;
    private double coordinateX;
    private double coordinateY;
    private List<Reservation> reservations;

    public Room(int RoomID, String Description, double CoordinateX, double CoordinateY, List<Reservation> reservations){
        this.roomID = RoomID;
        this.description = Description;
        this.coordinateX = CoordinateX;
        this.coordinateY = CoordinateY;
        this.reservations = reservations;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getDescription() {
        return description;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
