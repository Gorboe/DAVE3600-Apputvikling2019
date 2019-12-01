package gorboe.com.s319482mappe3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gorboe.com.s319482mappe3.enteties.Marker;
import gorboe.com.s319482mappe3.enteties.Reservation;
import gorboe.com.s319482mappe3.enteties.Room;

//TODO: VALIDATION NOT ALLOW & SIGN IN ANY FIELDS WILL FUCK UP PHP.

public class Database {
    private static Database INSTANCE = null;
    private Server task;
    private List<Room> rooms;
    private List<Marker> markers;
    private List<Reservation> reservations;

    private Database(){
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        markers = new ArrayList<>();
        getAllItems();
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    //getAllItems
    public void getAllItems(){
        task = new Server();
        rooms = new FlexList<>();
        markers = new FlexList<>();
        reservations = new FlexList<>();
        String result;
        try{
            result = task.execute("http://student.cs.hioa.no/~s319482/out.php").get();
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
                    String timeFrom = jsonObject.getString("TimeFrom");
                    String timeTo = jsonObject.getString("TimeTo");
                    String name = jsonObject.getString("Name");
                    String description = jsonObject.getString("Description");
                    int fMarkerID = jsonObject.getInt("FMarkerID");
                    Reservation reservation = new Reservation(reservationID, roomID, date, timeFrom, timeTo, name, description, fMarkerID);
                    reservations.add(reservationID, reservation);
                }else if(!jsonObject.isNull("FKMarkerID")){
                    //Is a room
                    int roomID = jsonObject.getInt("RoomID");
                    String description = jsonObject.getString("Description");
                    int markerID = jsonObject.getInt("FKMarkerID");

                    //get reservations for this room
                    List<Reservation> currentRoomReservations = new ArrayList<>();
                    for(Reservation candidate: reservations){
                        if(candidate == null) continue;
                        if(candidate.getRoomID() == roomID){
                            currentRoomReservations.add(candidate);
                        }
                    }
                    Room room = new Room(roomID, description, currentRoomReservations, markerID);
                    rooms.add(roomID, room);
                }
                else{
                    //Is a marker
                    int markerID = jsonObject.getInt("MarkerID");
                    double coordinateX = jsonObject.getDouble("CoordinateX");
                    double coordinateY = jsonObject.getDouble("CoordinateY");

                    //get rooms for this marker
                    List<Room> currentRooms = new ArrayList<>();
                    for(Room candidate: rooms){
                        if(candidate == null) continue;
                        if(candidate.getMarkerID() == markerID){
                            currentRooms.add(candidate);
                        }
                    }

                    Marker marker = new Marker(markerID, coordinateX, coordinateY, currentRooms);
                    markers.add(markerID, marker);
                }
            }
        }catch (Exception e){
            //todo: catch
            System.out.println("unable to create jasonobjects");
            return;
        }

    }

    public void addMarker(double coordinateX, double coordinateY){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/in.php/?Table=Marker&Cordx="
                + coordinateX + "&Cordy=" + coordinateY);
        getAllItems();
    }

    public void updateMarker(String x, String y, int markerID){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/update.php/?Table=Marker" +
                "&Cordx=" + x +
                "&Cordy=" + y +
                "&Markerid=" + markerID);
        getAllItems();
    }

    public void deleteMarker(int id){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/delete.php/?Table=Marker&Markerid=" + id);
        getAllItems();
    }

    //addRoom always call getAllItems after to update list items
    public void addRoom(String description, int markerID){
        //todo: validation

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/in.php/?Table=Room&Beskrivelse="
                + description + "&Markerid=" + markerID);
        getAllItems();
    }

    public void updateRoom(int roomID, String description){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/update.php/?Table=Room&Beskrivelse=" +
                description +
                "&Roomid=" + roomID);
        getAllItems();
    }

    public void deleteRoom(int id){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/delete.php/?Table=Room&Roomid=" + id);
        getAllItems();
    }

    //addReservation always call getAllItems after to update list items
    public void addReservation(Reservation reservation){
        //todo: validation (maybe to here? but in creates)

        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/in.php/?Table=Reservation&Romid="
                + reservation.getRoomID() +
                "&Date=" + reservation.getDate() +
                "&Timefrom=" + reservation.getTimeFrom() +
                "&Timeto=" + reservation.getTimeTo() +
                "&Name=" + reservation.getName() +
                "&Description=" + reservation.getDescription() +
                "&Fmarkerid=" + reservation.getfMarkerID());
        getAllItems();
    }

    public void updateReservation(Reservation reservation){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/update.php/?Table=Reservation&Date="
                + reservation.getDate() +
                "&Timefrom=" + reservation.getTimeFrom() +
                "&Timeto=" + reservation.getTimeTo() +
                "&Name=" + reservation.getName() +
                "&Description=" + reservation.getDescription() +
                "&Reservationid=" + reservation.getReservationID() +
                "&Fmarkerid=" + reservation.getfMarkerID());
        getAllItems();
    }

    public void deleteReservation(int id){
        task = new Server();
        task.execute("http://student.cs.hioa.no/~s319482/delete.php/?Table=Reservation&Resid=" + id);
        getAllItems();
    }

    //GETTERS
    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int id){
        System.out.println(rooms + " " + id);
        return rooms.get(id);
    }

    public Marker getMarker(int id){
        return markers.get(id);
    }

    public Reservation getReservation(int id){
        return reservations.get(id);
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public List<String> getAvailableTimes(String date, int roomID){
        //ROMTIDER 06:00-23:00 max 4timer om gangen
        List<String> times = new ArrayList<>();

        List<Reservation> reservationsToday = new ArrayList<>();
        //1. get all reservations for the selected date
        for(Reservation reservation: reservations){
            if(reservation == null) continue;

            if(reservation.getDate().equals(date) && reservation.getRoomID() == roomID){
                reservationsToday.add(reservation);
            }
        }

        //2. get all times selected fe. 10:00-12:00, 16:00-17:00 are selected already
        List<String> selectedTimes = new ArrayList<>();
        for(Reservation reservation: reservationsToday){
            //pull out xx and match from and to and add the inbetweens
            int from = Integer.parseInt(reservation.getTimeFrom().substring(0, 2));
            int to = Integer.parseInt(reservation.getTimeTo().substring(0, 2));
            //case from can be 8 and to can be 12.
            for(;from < to; from++){
                String time = String.format("%02d", from) + ":00";
                selectedTimes.add(time);
            }
        }

        //3. give back 06:00-09:00, 13:00, 14:00, 15:00, 18:00-22:00 //til kan ha 23:00
        for(int i = 6; i <= 22; i++){
            boolean isSelected = false;
            for(String time: selectedTimes){
                int t = Integer.parseInt(time.substring(0, 2));
                if(t == i){
                    isSelected = true;
                }
            }
            if(!isSelected) {
                times.add(String.format("%02d", i) + ":00");
            }
        }
        return times;
    }

    public List<String> getAvailableToTimes(String date, String fromTime, int roomID, int resID){
        List<String> times = new ArrayList<>();

        List<Reservation> reservationsToday = new ArrayList<>();
        //1. get all reservations for the selected date
        for(Reservation reservation: reservations){
            if(reservation == null) continue;

            if(reservation.getDate().equals(date) && reservation.getRoomID() == roomID){
                reservationsToday.add(reservation);
            }
        }

        //2. get all times selected
        List<String> selectedTimes = new ArrayList<>();
        for(Reservation reservation: reservationsToday){
            //pull out xx and match from and to and add the inbetweens
            if(reservation.getReservationID() == resID) continue;
            int from = Integer.parseInt(reservation.getTimeFrom().substring(0, 2));
            int to = Integer.parseInt(reservation.getTimeTo().substring(0, 2));
            //case from can be 8 and to can be 12.
            from++;
            for(;from <= to; from++){
                String time = String.format("%02d", from) + ":00";
                selectedTimes.add(time);
            }
        }

        //3. Sort selected times list
        Collections.sort(selectedTimes, new TimeComparator());

        //4. Get all times but the selected ones
        List<String> AllpossibleTimes = new ArrayList<>();
        for(int i = 7; i <= 23; i++){
            boolean isSelected = false;
            for(String time: selectedTimes){
                int t = Integer.parseInt(time.substring(0, 2));
                if(t == i){
                    isSelected = true;
                }
            }
            if(!isSelected) {
                AllpossibleTimes.add(String.format("%02d", i) + ":00");
            }
        }

        //5. Remove times before our fromTime
        List<String> possibleTimes = new ArrayList<>();
        for(String time: AllpossibleTimes){
            int from = Integer.parseInt(fromTime.substring(0, 2));
            int t = Integer.parseInt(time.substring(0, 2));
            if(from < t){
                possibleTimes.add(time);
            }
        }
        System.out.println("STEP 5: " + possibleTimes);

        //6. Get the next times max 4hours
        int counter = 0;
        int from = Integer.parseInt(fromTime.substring(0, 2));
        for(String time: possibleTimes){
            from++;
            int t = Integer.parseInt(time.substring(0, 2));
            if(from == t && counter < 4){
                counter++;
                times.add(time);
            }
        }
        System.out.println("STEP 6: " + times);
        return times;
    }

    public List<String> getTimesInReservation(int resID){
        List<String> times = new ArrayList<>();
        Reservation reservation = getReservation(resID);
        int from = Integer.parseInt(reservation.getTimeFrom().substring(0, 2));
        int to = Integer.parseInt(reservation.getTimeTo().substring(0, 2));

        for(;from <= to; from++){
            String time = String.format("%02d", from) + ":00";
            times.add(time);
        }

        return times;
    }
}
