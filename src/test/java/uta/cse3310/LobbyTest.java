package uta.cse3310;

import junit.framework.TestCase;

public class LobbyTest extends TestCase {
    public LobbyTest(String testName) {
        super(testName);
    }

    public void testGameStart() {
        Lobby L = new Lobby();
        // Tests when game attempts to start with 1 player in lobby
        L.gameStart(1);

        // Tests when game attempts to start with 2 players in lobby
        L.gameStart(2);

        // Tests when game attempts to start with 5 players in lobby
        L.gameStart(5);
    }

    public void testDisplayPlayers() {
        String[] names = {"James", "Bob123", "Dave_0"};
        Lobby L = new Lobby();

        L.displayPlayers(names);
    }
}
