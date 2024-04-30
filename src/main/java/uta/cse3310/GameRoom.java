package uta.cse3310;

import java.util.ArrayList;

public class GameRoom {
    PlayerType PlayerNum;
    Player player;
    public ArrayList<Player> players = new ArrayList<Player>();
    public String[] color; // 4 Colors to choose frm
    public int hostLobbyId;
    public int GameId;
    public int hostId; // Assume player one is always host as they create a game
    public int playerCount = 0;
    public float density;
    public int randomness;
    public Score score;
    public Leaderboard leaderboard;
    public Lobby lobby = new Lobby(hostLobbyId);

    public GameRoom(int hostLobbyId, Score s, Leaderboard l)
    {
        this.hostLobbyId = hostLobbyId;
        score = s;
        leaderboard = l;
    }

    public void leave(int connId) {
        // Allows players to leave the lobby whenever they like
        playerCount -= 1;
        for(Player p : players) {
            if(connId == p.getPlayerId()) {
                lobby.players.add(p);
                players.remove(p); 
            }
        }
    }

    public void kick(int connId) {
        // Allows the host to kick another player in the lobby
        playerCount -= 1;
        for(Player p : players) {
            if(connId == p.getPlayerId()) {
                lobby.players.add(p);
                players.remove(p);
            }
        }
    }

    public String assignColor(String choice) {
        // Allows players to select their desired color choice
        if(choice.equals("red")) {
            // System.out.println("Player chose red");
            player.color = "red";
            return "red";
        }
        else if(choice.equals("blue")) {
            // System.out.println("Player chose blue");
            player.color = "blue";
            return "blue";
        }
        else if(choice.equals("green")) {
            // System.out.println("Player chose green");
            player.color = "green";
            return "green";
        }
        else if(choice.equals("yellow")) {
            // System.out.println("Player chose yellow");
            player.color = "yellow";
            return "yellow";
        }
        // Testing purposes
        System.out.println("Invalid color.");

        return null;
    }

    public float editDensity(float dens) {
        // Allows host to edit specific game values
        density = dens;
        return density;
    }

    public int editRandomness(int rando) {
        // Allows host to edit specific game values
        randomness = rando;
        return randomness;
    }

    public void displayPlayers() {
        // Shows all players currently in the lobby in a list
        for(Player p : players) {
            System.out.println(p.playerName);
        }
    }

    // Game can be started once number of players is greater than 2
    public boolean gameStart(int playerCount) {
        // Allows the host to start the game whenever amount of players is valid
        if(playerCount >= 2 || playerCount <= 4) {
            // game has option to be started
            System.out.println("Game can be started");
            return true;
        }
        else if(playerCount > 4) {
            // Player after the 4th joins is moved into another lobby and game can be started still
            while(playerCount > 4) {
                playerCount -= 1;
            }
            System.out.println("Game can be started after 5th player was moved");
            return true;
        }
        System.out.println("Not enough players");
        return false;
    }
}
