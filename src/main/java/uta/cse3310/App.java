
// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package uta.cse3310;

import static spark.Spark.*;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App extends WebSocketServer {
  // All server(s) currently underway on this server stored here
  // private Vector<Lobby> Lobbies = new Vector<Lobby>();
  Lobby lob = null;

  public int numOfPlayers = 1;
  public int playerId = 1;
  public int lobbyId = 1;
  private int connectionId = 0;
  // private Instant startTime;
  // private Statistics stats;
  private static Board board;

  public App(int port) {
    super(new InetSocketAddress(port));
  }

  public App(InetSocketAddress address) {
    super(address);
  }
  private static Messaging messaging;
  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    //messaging = new Messaging(this);
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

    connectionId++;

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    ServerEvent E = new ServerEvent();

    // Search for an open lobby
    if(lob != null && numOfPlayers < 20) {
      lob.numOfPlayers = numOfPlayers;
      numOfPlayers++;
      lob.playerId = playerId;
      playerId++;
      System.out.println("Found a Lobby.");
    }
    // No matches? Create a new Lobby.
    else if (lob == null) {
      lob = new Lobby(lobbyId);
      lob.numOfPlayers = numOfPlayers;
      numOfPlayers++;
      lob.playerId = playerId;
      playerId++;
      lobbyId++;
      System.out.println("Creating a new Lobby.");
    }
    else {
      // Since there can be 5 concurrent games with 4 people max, lobby goes up to 20 players.
      System.out.println("Lobby full.");
    }

    // create an event to go to only the new player
    E.YouAre = lob.playerT;
    // E.GameId = G.GameId;

    // allows the websocket to give us the Game when a message arrives..
    // it stores a pointer to G, and will give that pointer back to us
    // when we ask for it
    conn.setAttachment(lob);

    Gson gson = new Gson();

    // Note only send to the single connection
    String jsonString = gson.toJson(E);
    conn.send(jsonString);
    /* System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
            + escape(jsonString)); */

    // Update the running time
    // stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

    // The state of the game has changed, so lets send it to everyone
    jsonString = gson.toJson(lob);
    /* System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString)); */
    broadcast(jsonString);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");
    // Retrieve the game tied to the websocket connection
    Lobby lob = conn.getAttachment();
    lob = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    //System.out.println(message);
    if (message.startsWith("msg: ")) {
        //System.out.println("testt: " + message);
        messaging.sendMsg(message);
    } else {
        JsonElement element = JsonParser.parseString(message);
        JsonObject obj = element.getAsJsonObject();


        String type = obj.get("type").getAsString();
        if (type.equals("letterSelection")) 
        {
          JsonArray f = obj.get("firstLetterCoordinate").getAsJsonArray();
          int[] first = new int[f.size()];

          for (int i = 0; i < f.size(); i++) {
              JsonElement e = f.get(i);
              first[i] = e.getAsInt();
          }

          JsonArray s = obj.get("secondLetterCoordinate").getAsJsonArray();

          int[] second = new int[s.size()];

          for (int i = 0; i < s.size(); i++) {
              JsonElement e = s.get(i);
              second[i] = e.getAsInt();
          }

          //check if first and second is valid word
            //first check if horizontal, diagnoal, veriticle
          
          char firstLetter = board.getBoard()[first[0]][first[1]];
          char secondLetter = board.getBoard()[second[0]][second[1]];
    
          obj = new JsonObject();
          obj.addProperty("type", "valid");
          System.out.println("End: " + board.validateSelection(firstLetter, secondLetter, first, second));
          if (board.validateSelection(firstLetter, secondLetter, first, second)) {
            obj.addProperty("firstLetter", Arrays.toString(first));
            obj.addProperty("secondLetter", Arrays.toString(second));
            broadcast(obj.toString());
        } else {
          System.out.println("nothing");
        }


    /* System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "-" + " " + escape(message)); */

    // Bring in the data from the webpage
    // A UserEvent is all that is allowed at this point
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    UserEvent U = gson.fromJson(message, UserEvent.class);

    // Update the running time
    // stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

    // Get our Game Object
    Lobby lob = conn.getAttachment();
    lob.Update(U);

    // send out the lobby state every time to everyone
    String jsonString;
    jsonString = gson.toJson(lob);

    /* System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString)); */
    broadcast(jsonString);
    }
  }
    
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific websocket
    }
  }

  @Override
  public void onStart() {
    setConnectionLostTimeout(0);
    // stats = new Statistics();
    // startTime = Instant.now();
  }

  private String escape(String S) {
    // turns " into \"
    String retval = new String();
    // this routine is very slow.
    // but it is not called very often
    for (int i = 0; i < S.length(); i++) {
      Character ch = S.charAt(i);
      if (ch == '\"') {
        retval = retval + '\\';
      }
      retval = retval + ch;
    }
    return retval;
  }

  public static void main(String[] args) {
    String HttpPort = System.getenv("HTTP_PORT");
    int port = 9004;
    if (HttpPort!=null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server
    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);
    board = H.getBoard();

    // create and start the websocket server
    port = 9104;
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort!=null) {
      port = Integer.valueOf(WSPort);
    }

    
    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);

    messaging = new Messaging(A);

  }

  
  private static String convertGridToHTML(char[][] grid) {
    StringBuilder html = new StringBuilder("<table style=\"border-collapse: collapse; font-family: Arial, sans-serif;\">");
    for (char[] row : grid) {
        html.append("<tr>");
        for (char cell : row) {
            html.append("<td style=\"border: 1px solid black; width: 20px; height: 15px; text-align: center;\">").append(cell).append("</td>");
        }
        html.append("</tr>");
    }
    html.append("</table>");
    return html.toString();
  }

  private static String convertWordBankToHTML(List<String> placedWords) {
    StringBuilder html = new StringBuilder("<div id=\"wordBank\" style=\"border: 1px solid black; padding: 10px;max-width: 200px;\">");
    html.append("<h2>Word Bank</h2>\n");
    html.append("<ul>\n");

    for (String word : placedWords) {
        html.append("<li>").append(word).append("</li>\n");
    }

    html.append("</ul>\n");
    html.append("</div>\n");

    return html.toString();
  }

}
