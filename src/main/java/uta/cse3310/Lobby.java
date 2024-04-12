// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

public class Lobby {
    public String[] playerNames;
    public String host;
    public String color;
    private float density;
    private int randomness;
    private int playerCount;
    
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

    public void join() {
        // Allows players to join the lobby
    }
    
    public void leave() {
        // Allows players to leave the lobby whenever they like
    }

    public void displayPlayers(String[] playerNames) {
        // Shows all players currently in the lobby in a list
        for(int i = 0; i < 4; i++) {
            System.out.println(playerNames[i]);
        }
    }

    public void kick(String playerNames, String host) {
        // Allows the host to kick another player in the lobby
    }

    public void editValues() {
        // Allows host to edit specific game values
    }

    public void displayHelp() {
        // Gives player the option to view instructions/explanation of the game mechanics and functions
    }

    public String assignColor(String playerNames) {
        // Allows players to select their desired color choice
    }

    public String createName(String input) {
        // Players are given the choice of creating their in game name
    }

    public boolean checkPlayerCount(String playerNames) {
        // returns true or false to see the number of players in the current lobby
    }

    public boolean checkUniqueName(String playerNames) {
        // making sure that two players do not have the same username
    }
}