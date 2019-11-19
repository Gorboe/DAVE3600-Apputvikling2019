package gorboe.com.s319482mappe3;

import java.util.ArrayList;

public class FlexList<T> extends ArrayList<T> {
    @Override
    public void add(int index, T element) {
        if(index >= 0 && index <= size()){
            super.add(index, element);
            return;
        }
        int amountOfNulls = index - size();
        for(int i = 0; i < amountOfNulls; i++){
            super.add(null);
        }
        super.add(index, element);
    }
}
