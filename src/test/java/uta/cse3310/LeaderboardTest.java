package uta.cse3310;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LeaderboardTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LeaderboardTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(LeaderboardTest.class);
    }

    public void testLeaderboard() {
        Lobby L = new Lobby(1);

        // Create 2 new players with unique ids
        Player p1 = new Player("Andy", 1);
        Player p2 = new Player("Bob123", 2);

        // Lobby now contains both of the players
        L.players.add(p1);
        L.players.add(p2);

        // Send the arraylist of players to leaderboard and display current standings
        L.leaderboard.updateLeaderboard();

        Player p3 = new Player("George Washington", 3);

        L.players.add(p3);

        // Use the players saved in leaderboard and update scores based on the id
        L.leaderboard.updateScores(1, 100);
        L.leaderboard.updateScores(2, 400);
        L.leaderboard.updateScores(3, 1000);

        // Display the newly changed scores for the players
        L.leaderboard.updateLeaderboard();

        // Display lobby where it's sorted by player ids
        L.displayLobby();
    }
}