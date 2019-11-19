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
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    //GetAllItems //TODO: make this private and call it before getters to make sure data updated
    public void GetAllItems(){
        task = new Server();
        String result;
        try{
            result = task.execute("http://student.cs.hioa.no/~s319482/jsonout.php").get();
        }catch (Exception e){
            //catch
            System.out.println("unable to get items async");
            return;
        }
        System.out.println(result);
        //to json objects todo: following 5lines should maybe be elsewhere?
        try{
            JSONArray jsonObjects = new JSONArray(result);
            for(int j = 0; j < jsonObjects.length(); j++){
                JSONObject jsonObject = jsonObjects.getJSONObject(j);
                System.out.println(jsonObject); //todo:to convert to object Gson gson = new Gson();Object object = gson.fromJson(jsonObject, object.class);

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
                    Room room = new Room(roomID, description, coordinateX, coordinateY);
                    rooms.add(room);
                }
                //String name = jsonObject.getString("name");
                //result.append(name).append(" ");
            }
        }catch (Exception e){
            //catch
            System.out.println("unable to create jasonobjects");
            return;
        }

    }

    //AddRoom always call GetAllItems after to update list items

    //AddReservation always call GetAllItems after to update list items

    //GETTERS
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
