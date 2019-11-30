package gorboe.com.s319482mappe3;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import gorboe.com.s319482mappe3.enteties.Reservation;

public class DateComparator implements Comparator<Reservation> {
    @Override
    public int compare(Reservation res1, Reservation res2){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Date orderDate1 = sdf.parse(res1.getDate() + " " + res1.getTimeTo());
                Date orderDate2 = sdf.parse(res2.getDate() + " " + res2.getTimeTo());
                return orderDate1.compareTo(orderDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
