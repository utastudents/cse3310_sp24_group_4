package uta.cse3310;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
    public ArrayList<Player> players;

    public Leaderboard(ArrayList<Player> players) {
        this.players = players;
    }

    // Score changes based on player id where input from server/webpage matches with id
    public void updateScores(int connId, int score) {
        for(Player p : players) {
            if(p.getPlayerId() == connId) {
                p.score = score;
            }
        }
    }

    // Leaderboard is set to highest score to lowest score and displays only the username and corresponding score
    public void updateLeaderboard() {
        Collections.sort(players, Player.scoreCmp);
        for(Player p : players) {
            System.out.println("Username: " + p.getPlayerName() + " | Score: " + p.getScore());
        }
    }
}