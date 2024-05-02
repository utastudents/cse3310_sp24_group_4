package uta.cse3310;

/* 

Scenario:

The first player joins, and they enter their username as 'JimmyJohns'. 
After the first player joins, another player joins with the username 'Adam123'. 
With two players, they both join using the Game 1 button on the page and start with a score of 0. 
(Players play and search for words)
'JimmyJohns' finds 3 words and has a score of 300 from it. 'Adam123' finds 6 words and has a score of 600 from it.
 
After some time, a third player joins and attempts to input the username 'Adam123'. 
They retype username after the fail with the username '#1WS_gamer'. 
The fourth player joins with the username 'charmander1', and both the player 3 and player 4 join using the button Game 2. 
(Players play and search for words)
'#1WS_gamer' finds 1 words and has a score of 100 from it. 'charmander1' finds 10 words and has a score of 1000 from it.

Both games happen simultaneously, and '#1WS_gamer' decides to leave during the game. 

A new player decides to join with the username 'strugglingCS' and joins Game 2. 
'JimmyJohns' and 'Adam123' decide to leave, and the only remaining players are the players in Game 2.

*/

/* 

    This tests the following requirements:
    1.2, 1.3, 1.8, 4.7

    Other requirements are tested by the other test classes.

*/

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WholeAppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WholeAppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WholeAppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testWholeApp()
    {
        Lobby L = new Lobby(1);

        int playerCount = 0;

        // Player 1 is created here through different means since there is no actual connection happening to server
        assertTrue(L.checkUniqueName("JimmyJohns"));
        Player p1 = new Player("JimmyJohns", 1);
        L.players.add(p1);
        assertNotNull(L.players);
        playerCount += 1;

        L.displayLobby(); // This should have 1 player showing

        // Player 2 is created here through different means since there is no actual connection happening to server
        assertTrue(L.checkUniqueName("Adam123"));
        Player p2 = new Player("Adam123", 2);
        L.playerNames.add("Adam123");
        L.players.add(p2);
        assertNotNull(L.players);
        playerCount += 1;

        assertNotNull(L.leaderboard); // Leaderboard should be initialized after first player joins
        L.displayLobby(); // This should have 2 players showing

        assertEquals(2, playerCount);

        // Players join game 1 and play for a while
        L.leaderboard.updateLeaderboard(); // This should show both p1 and p2 at 0 points
        L.leaderboard.updateScores(1, 300);
        L.leaderboard.updateScores(2, 600);
        L.leaderboard.updateLeaderboard(); // This should show both p1 and p2 with updated scores

        assertEquals(300, p1.getScore());
        assertEquals(600, p2.getScore());

        // Player 3 is created here through different means since there is no actual connection happening to server
        assertFalse(L.checkUniqueName("Adam123"));
        assertTrue(L.checkUniqueName("#1WS_gamer"));
        Player p3 = new Player("#1WS_gamer", 3);
        L.players.add(p3);
        assertNotNull(L.players);
        playerCount += 1;

        L.displayLobby(); // This should have 3 players showing

        // Player 4 is created here through different means since there is no actual connection happening to server
        assertTrue(L.checkUniqueName("charmander1"));
        Player p4 = new Player("charmander1", 4);
        L.players.add(p4);
        assertNotNull(L.players);
        playerCount += 1;

        L.displayLobby(); // This should have 4 players showing

        assertEquals(4, playerCount);

        // New players join game 2 and play for a while
        L.leaderboard.updateLeaderboard(); // This should show the current scores of all 4 players, with p3 and p4 at 0 points
        L.leaderboard.updateScores(3, 100);
        L.leaderboard.updateScores(4, 1000);
        L.leaderboard.updateLeaderboard(); // This should show the current socres of all 4 players, with p3 and p4 having updated scores

        assertEquals(100, p3.getScore());
        assertEquals(1000, p4.getScore());

        // Player 3 decides to leave
        L.players.remove(p3);
        playerCount -= 1;

        L.displayLobby(); // This should have 3 players showing

        assertEquals(3, playerCount);

        // Player 5 is created here through different means since there is no actual connection happening to server
        assertTrue(L.checkUniqueName("strugglingCS"));
        Player p5 = new Player("strugglingCS", 5);
        L.players.add(p5);
        assertNotNull(L.players);
        playerCount += 1;

        L.displayLobby(); // This should have 4 players showing

        assertEquals(4, playerCount);

        // Both p1 and p2 leave here
        L.players.remove(p1);
        L.players.remove(p2);
        playerCount -= 2;

        L.displayLobby(); // This should have 2 players showing
        L.leaderboard.updateLeaderboard(); // This should have 2 players and p5 has 0 points

        assertEquals(2, playerCount);
    }
}
