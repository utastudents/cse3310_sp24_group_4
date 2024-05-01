package uta.cse3310;

public class GameRoom {
    PlayerType PlayerNum;
    Player player;
    public String host; // Assume player one is always host as they create a game
    public String[] color; // 4 Colors to choose frm
    public int GameId;
    public int playerCount = 0;
    public float density;
    public int randomness;
    public Score score;
    public Leaderboard leaderboard;

    public GameRoom(Score s, Leaderboard l)
    {
        score = s;
        leaderboard = l;

        
    }

    public void leave() {
        // Allows players to leave the lobby whenever they like
        playerCount -= 1;
        
    }

    public void kick() {
        // Allows the host to kick another player in the lobby
        playerCount -= 1;
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

    public void displayPlayers(String[] playerNames) {
        // Shows all players currently in the lobby in a list
        for(String s: playerNames) {
            System.out.println(s);
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