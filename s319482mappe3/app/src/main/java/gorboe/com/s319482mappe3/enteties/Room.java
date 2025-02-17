package gorboe.com.s319482mappe3.enteties;

import androidx.annotation.NonNull;

import java.util.List;

public class Room {
    private int roomID;
    private int markerID;
    private String description;
    private List<Reservation> reservations;

    public Room(int RoomID, String Description, List<Reservation> reservations, int markerID){
        this.roomID = RoomID;
        this.description = Description;
        this.reservations = reservations;
        this.markerID = markerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMarkerID() {
        return markerID;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
