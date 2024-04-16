package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;

public class LobbyTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LobbyTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(LobbyTest.class);
    }

    public void testCreateGame()
    {
        Lobby L = new Lobby();
        
        L.createGame();

        assertNotNull(L.rooms);
    }

    public void testDisplayLobby()
    {
        ArrayList<String> names = new ArrayList<String>();
        names.add("James");
        names.add("Bob123");
        names.add("Dave_0");
        // String[] names = {"James", "Bob123", "Dave_0"};
        Lobby L = new Lobby();

        L.playerNames = names;

        L.displayLobby();
    }

    public void testNameCreation() 
    {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Dan");
        names.add("xx_WordSearchGamer");
        names.add("Curious George");
        names.add("123_name");
        // String[] names = {"Dan", "xx_WordSearchGamer", "Curious George", "123_name"};
        Lobby L = new Lobby();

        L.playerNames = names;

        // Case 1: Same username
        assertFalse(L.checkUniqueName("Dan"));
        assertFalse(L.checkUniqueName("Curious George"));;

        // Case 2: Same username, different capitalizations
        assertTrue(L.checkUniqueName("XX_WordsearchGamer"));
        assertTrue(L.checkUniqueName("123_NAME"));

        // Case 3: Different username
        assertTrue(L.checkUniqueName("Candy0"));
        assertTrue(L.checkUniqueName("HandsomeSquidward"));

        L.createName("HandsomeSquidward");
        L.createName("Dan");

        L.displayLobby();
    }
}
