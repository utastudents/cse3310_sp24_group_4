package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
        return new TestSuite(GameUnitTest.class);
    }

    public void testCreateGame()
    {
        Lobby L = new Lobby();
        
        L.createGame();

        assertNotNull(L.rooms);
    }

    public void testDisplayLobby()
    {
        String[] names = {"James", "Bob123", "Dave_0"};
        Lobby L = new Lobby();

        L.playerNames = names;

        L.displayLobby();
    }

    public void testNameCreation() 
    {
        String[] names = {"Dan", "xx_WordSearchGamer", "Curious George", "123_name"};
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
    }
}
