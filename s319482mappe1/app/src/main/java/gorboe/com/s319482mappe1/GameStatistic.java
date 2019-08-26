package gorboe.com.s319482mappe1;

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
        return "Riktig: " + correct_answer + "\t\tFeil: " + wrong_answer;
    }
}
