package gorboe.com.s319482mappe3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gorboe.com.s319482mappe3.enteties.Reservation;
import gorboe.com.s319482mappe3.enteties.Room;

public class Database {
    private static Database INSTANCE = null;
    private Server task;
    private List<Room> rooms;
    private List<Reservation> reservations;

    private Database(){
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        GetAllItems();
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    //GetAllItems
    private void GetAllItems(){
        task = new Server();
        rooms = new FlexList<>();
        reservations = new ArrayList<>();
        String result;
        try{
            result = task.execute("http://student.cs.hioa.no/~s319482/jsonout.php").get();
        }catch (Exception e){
            //todo: catch
            System.out.println("unable to get items async");
            return;
        }

        try{
            JSONArray jsonObjects = new JSONArray(result);
            for(int j = 0; j < jsonObjects.length(); j++){
                JSONObject jsonObject = jsonObjects.getJSONObject(j);

                if(!jsonObject.isNull("ReservationID")){
                    //Is a reservation
                    int reservationID = jsonObject.getInt("ReservationID");
                    int roomID = jsonObject.getInt("RoomID");
                    String date = jsonObject.getString("Date");
                    String time = jsonObject.getString("Time");
                    Reservation reservation = new Reservation(reservationID, roomID, date, time);
                    reservations.add(reservation);
                }else{
                    //Is a room
                    int roomID = jsonObject.getInt("RoomID");
                    String description = jsonObject.getString("Description");
                    double coordinateX = jsonObject.getDouble("CoordinateX");
                    double coordinateY = jsonObject.getDouble("CoordinateY");

                    //get reservations for this room
                    List<Reservation> currentRoomReservations = new ArrayList<>();
                    System.out.println("RES: " + reservations.size());
                    for(Reservation candidate: reservations){
                        if(candidate.getRoomID() == roomID){
                            currentRoomReservations.add(candidate);
                            System.out.println(roomID + " got a new candidate " + candidate.getReservationID());
                        }
                    }
                    Room room = new Room(roomID, description, coordinateX, coordinateY, currentRoomReservations);
                    System.out.println("ID: " + roomID);
                    rooms.add(roomID, room);
                }
            }
        }catch (Exception e){
            //todo: catch
            System.out.println("unable to create jasonobjects");
            return;
        }

    }

    //AddRoom always call GetAllItems after to update list items
    public void AddRoom(String description, double coordinateX, double coordinateY){
        //todo: validation

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsonin.php/?Table=Room&Beskrivelse="
                + description + "&Cordx=" + coordinateX + "&Cordy=" + coordinateY);
        GetAllItems();
    }

    //AddReservation always call GetAllItems after to update list items
    public void AddReservation(int roomID, String date, String time){
        //todo: validation

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsonin.php/?Table=Reservation&Romid="
                + roomID + "&Date=" + date + "&Time=" + time);
        GetAllItems();
    }

    //GETTERS
    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id){
        return rooms.get(id);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
