package gorboe.com.s319482mappe2.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import java.util.Stack;

import gorboe.com.s319482mappe2.activities.MainActivity;

public class Navigation {

    private static Navigation INSTANCE = null;
    private Stack<Activity> stack;

    private Navigation(){
        stack = new Stack<>();
    }

    public static Navigation getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Navigation();
        }

        return INSTANCE;
    }

    public void forward(Activity activity, Intent intent){
        System.out.println("TESTTEST: " + activity.getClass().equals(MainActivity.class)); //TRUE!!!
        if(!activity.getClass().equals(MainActivity.class)){
            System.out.println("ACTIVITY FINISH();");
            activity.finish();
        }
        activity.startActivity(intent);
    }

    public void back(Context context){
        if(stack.isEmpty()){
            return;
        }
        Activity activity = stack.pop();
        activity.finish();
    }


}
