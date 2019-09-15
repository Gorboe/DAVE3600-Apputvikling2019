package gorboe.com.s319482mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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

    public void storeStatistics(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int i = 0;
        for(GameStatistic stat: Database.getInstance().getPrevious_games_collection()){
            preferences.edit().putString("date" + i, stat.storeString()[0]).apply();
            preferences.edit().putString("time" + i, stat.storeString()[1]).apply();
            preferences.edit().putString("correct_answer" + i, stat.storeString()[2]).apply();
            preferences.edit().putString("wrong_answer" + i, stat.storeString()[3]).apply();
            i++;
        }
        preferences.edit().putString("stats_count", String.valueOf(Database.getInstance().getPrevious_games_collection().size())).apply();
    }

    public void retrieveStatistics(Context context){
        if(!previous_games_collection.isEmpty()){
            return;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        int stats_count = Integer.parseInt(preferences.getString("stats_count", String.valueOf(0)));

        for(int i = 0; i < stats_count; i++){
            int correct_answer = Integer.parseInt(preferences.getString("correct_answer" + i, String.valueOf(-1)));
            int wrong_answer = Integer.parseInt(preferences.getString("wrong_answer" + i, String.valueOf(-1)));
            String time = preferences.getString("time" + i, null);
            String date = preferences.getString("date" + i, null);
            previous_games_collection.add(new GameStatistic(correct_answer, wrong_answer, time, date, context));
        }
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
