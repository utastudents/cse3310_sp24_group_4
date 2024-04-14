// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Lobby extends WebSocketServer {
    private Vector<GameRoom> rooms = new Vector<GameRoom>();
    public String[] playerNames;
    public String host;
    public String color;
    private float density;
    private int randomness;
    private int playerCount;

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) 
    {

    connectionId++;

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    ServerEvent E = new ServerEvent();
    
    // allows the websocket to give us the Game when a message arrives..
    // it stores a pointer to G, and will give that pointer back to us
    // when we ask for it
    conn.setAttachment(G);

    Gson gson = new Gson();

    // Note only send to the single connection
    String jsonString = gson.toJson(E);
    conn.send(jsonString);
    System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
            + escape(jsonString));

    // Update the running time
    stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

    // The state of the game has changed, so lets send it to everyone
    jsonString = gson.toJson(G);
    System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
    broadcast(jsonString);

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
        // Retrieve the game tied to the websocket connection
        Game G = conn.getAttachment();
        G = null;
    }

    @Override
    public void onMessage(WebSocket conn, String message)
    {
        System.out.println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "-" + " " + escape(message));

        // Bring in the data from the webpage
        // A UserEvent is all that is allowed at this point
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        UserEvent U = gson.fromJson(message, UserEvent.class);

        // Update the running time
        stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

        // Get our Game Object
        Game G = conn.getAttachment();
        G.Update(U);

        // send out the game state every time
        // to everyone
        String jsonString;
        jsonString = gson.toJson(G);

        System.out.println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
        broadcast(jsonString);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message)
    {
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        ex.printStackTrace();
        if (conn != null) {
        // some errors like port binding failed may not be assignable to a specific
        // websocket
        }
    }

    @Override
    public void onStart()
    {
        setConnectionLostTimeout(0);
        stats = new Statistics();
        startTime = Instant.now();
    }

    public void createGame()
    {
        // Creates a game lobby where players can join
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
}