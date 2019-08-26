package gorboe.com.s319482mappe1;

import java.util.ArrayList;

public class Database {
    private static Database INSTANCE = null;
    private int preferred_amount_of_questions;
    private ArrayList<GameStatistic> previous_games_collection;

    private Database(){
        //TODO: create database, use this to store preferences and other useful stuff.
        preferred_amount_of_questions = 5;
        previous_games_collection = new ArrayList<>();
    }

    public static Database getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Database();

        return INSTANCE;
    }

    public int getPreferred_amount_of_questions() {
        return preferred_amount_of_questions;
    }

    public void setPreferred_amount_of_questions(int preferred_amount_of_questions) {
        this.preferred_amount_of_questions = preferred_amount_of_questions;
    }

    public void addGameStatistic(int correct_answer, int wrong_answer){
        previous_games_collection.add(new GameStatistic(correct_answer, wrong_answer));
    }

    public ArrayList<GameStatistic> getPrevious_games_collection() {
        return previous_games_collection;
    }
}
