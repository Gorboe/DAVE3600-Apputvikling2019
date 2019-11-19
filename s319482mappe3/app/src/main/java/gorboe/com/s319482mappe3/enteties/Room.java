package gorboe.com.s319482mappe3.enteties;

public class Room {
    private int roomID;
    private String description;
    private double coordinateX;
    private double coordinateY;

    public Room(int RoomID, String Description, double CoordinateX, double CoordinateY){
        this.roomID = RoomID;
        this.description = Description;
        this.coordinateX = CoordinateX;
        this.coordinateY = CoordinateY;
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
}
