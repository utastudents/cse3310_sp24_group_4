package uta.cse3310;

import junit.framework.TestCase;

public class LobbyTest extends TestCase
{
    public LobbyTest(String testName)
    {
        super(testName);
    }

    public void testCreateGame()
    {
        Lobby L = new Lobby();
        
    }

    public void testDisplayLobby()
    {
        String[] names = {"James", "Bob123", "Dave_0"};
        Lobby L = new Lobby();

        L.playerNames = names;

        L.displayLobby();
    }

    public void testNameCreation() {
        String[] names = {"Dan", "xx_WordSearchGamer", "Curious George", "123_name"};
        Lobby L = new Lobby();

        L.playerNames = names;

        // Case 1: Same username
        L.checkUniqueName("Dan");
        L.checkUniqueName("Curious George");
        L.createName("Dan");
        L.createName("Curious George");

        // Case 2: Same username, different capitalizations
        L.checkUniqueName("XX_WordsearchGamer");
        L.checkUniqueName("123_NAME");
        L.createName("XX_WordsearchGamer");
        L.createName("123_NAME");

        // Case 3: Different username
        L.checkUniqueName("Candy0");
        L.checkUniqueName("HandsomeSquidward");
        L.createName("Candy0");
        L.createName("HandsomeSquidward");
    }
}
