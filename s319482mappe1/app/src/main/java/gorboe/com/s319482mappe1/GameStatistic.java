package gorboe.com.s319482mappe1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameStatistic {
    private int correct_answer;
    private int wrong_answer;

    public GameStatistic(int correct_answer, int wrong_answer){
        this.correct_answer = correct_answer;
        this.wrong_answer = wrong_answer;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public int getWrong_answer() {
        return wrong_answer;
    }

    public String toString(){
        return "Dato: " + getCurrentTimeAndDate() + "\nRiktig: " + correct_answer + "\t\t\tFeil: " + wrong_answer;
    }

    private String getCurrentTimeAndDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //using old approach with sdf instead of dtf as api lvl 26 required for dtf
        Date date = new Date();
        return sdf.format(date);
    }
}
