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

        L.displayPlayers(names);
    }
}
