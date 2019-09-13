package gorboe.com.s319482mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Locale;

public class Database {
    private static Database INSTANCE = null;
    private int preferred_amount_of_questions;
    private ArrayList<GameStatistic> previous_games_collection;
    private int currentlySelectedStat;

    private Database(){
        preferred_amount_of_questions = 5;
        previous_games_collection = new ArrayList<>();
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    public boolean isGerman(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences.getString("language_key", "no").equals("de")){
            return true;
        }
        return false;
    }

    public void setLocale(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Locale locale = new Locale(preferences.getString("language_key", "no")); //"no" is default if no set language is found
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public int getPreferred_amount_of_questions() {
        return preferred_amount_of_questions;
    }

    public void setPreferred_amount_of_questions(int preferred_amount_of_questions) {
        this.preferred_amount_of_questions = preferred_amount_of_questions;
    }

    public void addGameStatistic(int correct_answer, int wrong_answer, Context context){
        previous_games_collection.add(new GameStatistic(correct_answer, wrong_answer, context));
    }

    public ArrayList<GameStatistic> getPrevious_games_collection() {
        return previous_games_collection;
    }

    public void selectedStat(int position){
        currentlySelectedStat = position;
    }

    public void deleteStat(){
        previous_games_collection.remove(currentlySelectedStat);
    }
}
