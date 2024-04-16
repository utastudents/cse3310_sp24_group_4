// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<GameRoom> rooms = new ArrayList<GameRoom>();
    public PlayerType players;
    public String[] playerNames;
    public int numOfPlayers;
    public int playerId;
    public Score score;

    public void createGame()
    {
        // Creates a game lobby where players can join
        GameRoom GR = null;
        GR = new GameRoom(score);
        GR.GameId += 1;
        // Add the first player
        GR.PlayerNum = PlayerType.ONE;
        GR.playerCount = 1;
        rooms.add(GR);
        System.out.println(" creating a new Game Room");
    }

    public String displayLobby()
    {
        // Shows all players currently in the lobby in a list

        // Testing purposes
        /* for(String s: playerNames) {
            System.out.println(s);
        } */

        for(String name : playerNames) {
            return name;
        }
    }

    public void displayHelp()
    {
        // Gives player the option to view instructions/explanation of the game mechanics and functions
    }

    public String assignColor(String playerNames) {
        // Allows players to select their desired color choice
        return "";
    }

    public String createName(String input) {
        // (should be the first thing they are shown/allowed to do)
        if(checkUniqueName(input) == true) {
            playerNames[playerId] = input;
            return "Valid username.";
        }
        else {
            return "Username taken.";
        } 
    }

    public boolean checkUniqueName(String input)
    {
        // making sure that two players do not have the same username
        for(String s : playerNames) {
            if(s.equals(input)) {
                System.out.println("Name taken."); // Testing purposes
                return false;
            }
        }
        // after every name tested, username can be accepted since no match found
        System.out.println("Name available."); // Testing purposes
        return true;
    }

    public void Update(UserEvent U) {

    }
}