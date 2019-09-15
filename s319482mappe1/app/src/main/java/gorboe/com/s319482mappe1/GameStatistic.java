package gorboe.com.s319482mappe1;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameStatistic {
    private int correct_answer;
    private int wrong_answer;
    private Context context;
    private String date;
    private String time;

    public GameStatistic(int correct_answer, int wrong_answer, Context context){
        this.correct_answer = correct_answer;
        this.wrong_answer = wrong_answer;
        this.context = context;
        date = getCurrentDate();
        time = getCurrentTime();
    }

    //secondary constructor. used when restoring data from shared preferences.
    public GameStatistic(int correct_answer, int wrong_answer, String time, String date, Context context){
        this.correct_answer = correct_answer;
        this.wrong_answer = wrong_answer;
        this.context = context;
        this.time = time;
        this.date = date;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public int getWrong_answer() {
        return wrong_answer;
    }

    public String toString(){
        Database.getInstance().setLocale(context);

        return context.getResources().getString(R.string.date) + " " + date + "\n" +
               context.getResources().getString(R.string.time) + " " + time + "\n" +
               context.getResources().getString(R.string.right) + " " + correct_answer + "    " +
               context.getResources().getString(R.string.wrong) + " " + wrong_answer;
    }

    public String[] storeString(){
        String[] strings = new String[4];
        strings[0] = date;
        strings[1] = time;
        strings[2] = String.valueOf(correct_answer);
        strings[3] = String.valueOf(wrong_answer);
        return strings;
    }

    private String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    private String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
