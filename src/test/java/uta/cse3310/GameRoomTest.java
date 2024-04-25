package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameRoomTest extends TestCase
{
    Score s;
    Leaderboard l;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameRoomTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(LobbyTest.class);
    }

    public void testAssignColor()
    {
        GameRoom G = new GameRoom(s, l);

        // Case 1: All the available 4 colors to choose from
        assertEquals(G.assignColor("red"), "red");
        assertEquals(G.assignColor("blue"), "blue");
        assertEquals(G.assignColor("green"), "green");
        assertEquals(G.assignColor("yellow"), "yellow");

        // Case 2: Other colors not apart of the list
        assertNull(G.assignColor("black"));
        assertNull(G.assignColor("orange"));
        assertNull(G.assignColor("white"));
        assertNull(G.assignColor("purple"));
    }

    public void testGameStart()
    {
        GameRoom G = new GameRoom(s, l);

        // Case 1: There are 0 or 1 player(s) in the room
        assertFalse(G.gameStart(0));
        assertFalse(G.gameStart(1));

        // Case 2: There are 2-4 players in the room
        assertTrue(G.gameStart(2));
        assertTrue(G.gameStart(3));
        assertTrue(G.gameStart(4));

        // Case 3: There are 4+ players in the room
        assertTrue(G.gameStart(5));
        assertEquals(G.playerCount, 4);
        assertTrue(G.gameStart(10));
        assertEquals(G.playerCount, 4);
    }
}
