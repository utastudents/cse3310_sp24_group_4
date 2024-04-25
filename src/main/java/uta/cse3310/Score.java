package uta.cse3310;

public class Score {
    private String playerName;
    private int score;

    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public void updateScore(String playerName, int newScore) {
        if (this.playerName.equals(playerName)) {
            this.score = newScore;
        }
    }
    public int getScore() {
        return score;
    }
}
