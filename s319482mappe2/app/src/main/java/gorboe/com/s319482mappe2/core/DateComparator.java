package gorboe.com.s319482mappe2.core;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

import gorboe.com.s319482mappe2.enteties.Order;

public class DateComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Date orderDate1 = sdf.parse(order1.getDate() + " " + order1.getTime());
                Date orderDate2 = sdf.parse(order2.getDate() + " " + order2.getTime());
                return orderDate1.compareTo(orderDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
