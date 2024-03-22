// CSE 3310 Spring 2024
// Project Design - Group 4

package uta.cse3310;

// All imports are taken from TicTacToe game (may or may not use them)
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

// import org.java_websocket.WebSocket;
// import org.java_websocket.drafts.Draft;
// import org.java_websocket.drafts.Draft_6455;
// import org.java_websocket.handshake.ClientHandshake;
// import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

public class Lobby {
    public String[] playerNames;
    public String host;
    private String color;
    private float density;
    private int randomness;
    private int playerCount;
    
    // Game can be started once number of players is greater than 2
    public void gameStart(int playerCount) {
        // Allows the host to start the game whenever amount of players is valid
    }

    public void join() {
        // Allows players to join the lobby
    }
    
    public void leave() {
        // Allows players to leave the lobby whenever they like
    }

    public void displayPlayers(String playerNames) {
        // Shows all players currently in the lobby in a list
    }

    public void kick(String playerNames, String host) {
        // Allows the host to kick another player in the lobby
    }

    public void editValues() {
        // Allows host to edit specific game values
    }

    public String assignColor(playerNames) {
        // Allows players to select their desired color choice
    }

    public String createName(input) {
        // Players are given the choice of creating their in game name
    }

    public boolean checkPlayerCount(playerNames) {
        // returns true or false to see the number of players in the current lobby
    }

<<<<<<< HEAD
    public boolean checkUniqueName(playerNames) {
        //making sure that two players do not have the same username
=======
    boolean checkUniqueName(playerNames) {
        // making sure that two players do not have the same username
>>>>>>> f6998afa544ea2e76f3c7ec559767ba54bb9eb91
    }
}