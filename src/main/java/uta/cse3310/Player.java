package uta.cse3310;

public class Player {
    public String playerName;
    public int playerId;
    public int score;
    public String color;

    public Player() {
        this(" ", 0, 0, " ");
    }

    public Player(String playerName, int playerId) {
        this(playerName, playerId, 0, " ");
    }

    public Player(String playerName, int playerId, int score, String color) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.score = score;
        this.color = color;
    }
}