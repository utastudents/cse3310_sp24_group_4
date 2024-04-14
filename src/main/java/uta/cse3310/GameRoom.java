package uta.cse3310;

public class GameRoom {
    public GameRoom
    {

    }

    public void leave() {
        // Allows players to leave the lobby whenever they like
    }

    public void kick(String playerNames, String host) {
        // Allows the host to kick another player in the lobby
    }

    public String assignColor(String playerNames) {
        // Allows players to select their desired color choice
    }

    public void editValues() {
        // Allows host to edit specific game values
    }

    public void displayPlayers(String[] playerNames) {
        // Shows all players currently in the lobby in a list
        for(String s: playerNames) {
            System.out.println(s);
        }
    }

    public boolean checkPlayerCount(String playerNames) {
        // returns true or false to see the number of players in the current lobby
    }

    // Game can be started once number of players is greater than 2
    public void gameStart(int playerCount) {
        // Allows the host to start the game whenever amount of players is valid
        if(playerCount < 2) {
            // game cannot be started
        }
        else if(playerCount >= 2 || playerCount <= 4) {
            // game has option to be started
        }
        else if(playerCount > 4) {
            // Player after the 4th joins is moved into another lobby
        }
    }
}
