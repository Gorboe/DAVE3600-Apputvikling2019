package gorboe.com.s319482mappe3.enteties;

import java.util.List;

public class Marker {
    private int markerID;
    private double coordinateX;
    private double coordinateY;
    private List<Room> rooms;

    public Marker(int markerID, double coordinateX, double coordinateY, List<Room> rooms){
        this.markerID = markerID;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.rooms = rooms;
    }

    public Marker(double coordinateX, double coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public int getMarkerID() {
        return markerID;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
