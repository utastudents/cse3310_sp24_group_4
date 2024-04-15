// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.ArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Lobby {
    private ArrayList<GameRoom> rooms = new ArrayList<GameRoom>();
    public Player[] players;
    public String[] playerNames;
    public int numOfPlayers;

    public void createGame()
    {
        // Creates a game lobby where players can join
        GameRoom GR = null;
        GR = new GameRoom(stats);
        GR.GameId = GameId;
        GameId++;
        // Add the first player
        GR.PlayerNum = Player.ONE;
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

    public static void main(String[] args) {

        // Set up the http server
        int port = 9080;
        HttpServer H = new HttpServer(port, "./html");
        H.start();
        System.out.println("http Server started on port: " + port);
    
        // create and start the websocket server
    
        port = 9880;
        Lobby L = new Lobby(port);
        L.setReuseAddr(true);
        L.start();
        System.out.println("websocket Server started on port: " + port);
    
    }
}