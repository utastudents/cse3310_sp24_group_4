package uta.cse3310;

public class Leaderboard {
    private String playerName;
    private int scores;

    public Leaderboard(String playerName, int scores) {
        this.playerName = playerName;
        this.scores = scores;
    }

    public void updateScores(int scores) {
        this.scores = scores;
    }

    public void updateLeaderboard(String[] playerNames, int[] scores) {
        // Update leaderboard
    }
}