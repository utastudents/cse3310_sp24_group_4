package uta.cse3310;

import java.util.Comparator;

public class Player {
    public String playerName;
    public int playerId;
    public int score;
    public String color;

    public Player() {
        this(" ", 0, 0, "None");
    }

    public Player(String playerName, int playerId) {
        this(playerName, playerId, 0, "None");
    }

    public Player(String playerName, int playerId, int score, String color) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.score = score;
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    public String getColor() {
        return color;
    }

    public static Comparator<Player> scoreCmp = new Comparator<Player>() {
        public int compare(Player p1, Player p2) {
            int score1 = p1.getScore();
            int score2 = p2.getScore();

            // Orders from highest to lowest
            // score1 - score2 would result in lowest to highest
            return score2 - score1;
        }
    };

    public static Comparator<Player> playerIdCmp = new Comparator<Player>() {
        public int compare(Player p1, Player p2) {
            int Id1 = p1.getPlayerId();
            int Id2 = p2.getPlayerId();

            // Orders from lowest to highest
            // Id2 - Id1 would result in highest to lowest
            return Id1 - Id2;
        }
    };

    @Override
    public String toString() {
        return "Username: " + playerName + " | Player ID: " + playerId +
        " | Score: " + score + " | Color: " + color;
    }
}