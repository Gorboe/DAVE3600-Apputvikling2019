package gorboe.com.s319482mappe3;

import java.util.Comparator;

public class TimeComparator implements Comparator<String> {
    @Override
    public int compare(String s, String t1) {
        Integer s1 = Integer.parseInt(s.substring(0, 2));
        Integer s2 = Integer.parseInt(t1.substring(0, 2));

        return s1.compareTo(s2);
    }
}
