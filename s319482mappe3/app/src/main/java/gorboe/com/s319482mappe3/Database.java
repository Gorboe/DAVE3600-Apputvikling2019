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
        getAllItems();
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    //getAllItems
    private void getAllItems(){
        task = new Server();
        rooms = new FlexList<>();
        reservations = new FlexList<>();
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
                    reservations.add(reservationID, reservation);
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
                        if(candidate == null) continue;
                        if(candidate.getRoomID() == roomID){
                            currentRoomReservations.add(candidate);
                        }
                    }
                    Room room = new Room(roomID, description, coordinateX, coordinateY, currentRoomReservations);
                    rooms.add(roomID, room);
                }
            }
        }catch (Exception e){
            //todo: catch
            System.out.println("unable to create jasonobjects");
            return;
        }

    }

    //addRoom always call getAllItems after to update list items
    public void addRoom(String description, double coordinateX, double coordinateY){
        //todo: validation

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsonin.php/?Table=Room&Beskrivelse="
                + description + "&Cordx=" + coordinateX + "&Cordy=" + coordinateY);
        getAllItems();
    }

    public void updateRoom(Room room){

    }

    public void deleteRoom(int id){
        //"http://student.cs.hioa.no/~s319482/jsondelete.php/?Table=Room&Roomid=" + id

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsondelete.php/?Table=Room&Roomid=" + id);
        getAllItems();
    }

    //addReservation always call getAllItems after to update list items
    public void addReservation(Reservation reservation){
        //todo: validation (maybe to here? but in creates)

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsonin.php/?Table=Reservation&Romid="
                + reservation.getRoomID() + "&Date=" + reservation.getDate() + "&Time=" + reservation.getTime());
        getAllItems();
    }

    public void updateReservation(Reservation reservation){

    }

    public void deleteReservation(int id){
        //"http://student.cs.hioa.no/~s319482/jsondelete.php/?Table=Reservation&Resid=" + id

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/jsondelete.php/?Table=Reservation&Resid=" + id);
        getAllItems();
    }

    //GETTERS
    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id){
        return rooms.get(id);
    }

    public Reservation getReservation(int id){
        return reservations.get(id);
    }
}
