// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<GameRoom> rooms = new ArrayList<GameRoom>();
    public PlayerType players;
    public String[] playerNames;
    public int numOfPlayers;
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

    public void displayLobby(String[] playerNames)
    {
        // Shows all players currently in the lobby in a list
        for(String s: playerNames) {
            System.out.println(s);
        }
    }

    public void displayHelp()
    {
        // Gives player the option to view instructions/explanation of the game mechanics and functions
    }

    public String createName(String input)
    {
        // Players are given the choice of creating their in game name
        // (should be the first thing they are shown/allowed to do)
    }

    public boolean checkUniqueName(String playerNames)
    {
        // making sure that two players do not have the same username
    }

    public void Update(UserEvent U) {

    }
}