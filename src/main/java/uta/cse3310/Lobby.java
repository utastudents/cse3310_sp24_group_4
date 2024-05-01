// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

import java.util.ArrayList;
import java.util.Collections;

public class Lobby {
    public ArrayList<GameRoom> rooms = new ArrayList<GameRoom>();
    public ArrayList<String> playerNames = new ArrayList<String>();
    public ArrayList<Player> players = new ArrayList<Player>();
    public PlayerType playerT;
    public int numOfPlayers;
    public int playerId;
    public int lobbyId;
    public Score score;
    public Leaderboard leaderboard = new Leaderboard(players);

    public Lobby(int lobbyId)
    {
        this.lobbyId = lobbyId;
    }

    public void createGame(int connId)
    {
        // Creates a game lobby where players can join
        GameRoom GR = null;
        GR = new GameRoom(lobbyId, score, leaderboard);
        GR.GameId += 1;
        // Add the first player
        GR.PlayerNum = PlayerType.ONE;
        GR.playerCount = 1;
        for(Player p : players) {
            if(p.playerId == connId) {
                GR.hostId = p.playerId;
                GR.players.add(p);
                players.remove(p);
            }
        }
        rooms.add(GR);
        System.out.println("Creating a new Game Room");
    }

    // roomId should be from 0-4 where the button to join will be dependent on the order of where it is on the list
    // of rooms and will send the information to that room only
    public void joinRoom(int connId, int roomId) {
        if(rooms.isEmpty() == false && (roomId >= 0 && roomId <= 4)) {
            GameRoom targetRoom = rooms.get(roomId);
            targetRoom.playerCount += 1;
            if(targetRoom.playerCount == 2) {
                targetRoom.PlayerNum = PlayerType.TWO;
                for(Player p : players) {
                    if(p.getPlayerId() == connId) {
                        players.remove(p);
                        targetRoom.players.add(p);
                    }
                }
            }
            else if(targetRoom.playerCount == 3) {
                targetRoom.PlayerNum = PlayerType.THREE;
                for(Player p : players) {
                    if(p.getPlayerId() == connId) {
                        players.remove(p);
                        targetRoom.players.add(p);
                    }
                }
            }
            else if(targetRoom.playerCount == 4) {
                targetRoom.PlayerNum = PlayerType.FOUR;
                for(Player p : players) {
                    if(p.getPlayerId() == connId) {
                        players.remove(p);
                        targetRoom.players.add(p);
                    }
                }
            }
        }
    }

    public void displayLobby()
    {
        // Gives array list of all players currently in the lobby in a list for use

        // Sorts players by connection id in case of leaderboard manipulation
        Collections.sort(players, Player.playerIdCmp);
        // Testing purposes
        for(String s: playerNames) {
            System.out.println(s);
        }
        for(Player p : players) {
            System.out.println(p.getPlayerName());
        }
    }

    public void displayHelp()
    {
        // Gives player the option to view instructions/explanation of the game mechanics and functions
    }

    public String createName(String input) {
        // (should be the first thing they are shown/allowed to do)
        if(checkUniqueName(input) == true) {
            playerNames.add(input);
            Player newPlayer = new Player(input, playerId);
            players.add(newPlayer);
            // System.out.println(players);
            for(Player p : players) {
                System.out.println(p);
            } /* Testing Purposes */
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
