package uta.cse3310;

import junit.framework.TestCase;

public class ScoreTest extends TestCase {
    private Score score;
    // set up the test
    protected void setUp() {
        score = new Score("Player1",100);
    }
    //test the updateScore method
    public void testUpdateScore() {
        assertEquals(100, score.getScore());
        // update the score for the player
        score.updateScore("Player1",150);
        // check if the score is updated correctly
        assertEquals(150, score.getScore());
        // update the score for different player
        score.updateScore("Player2",200);
        //check that the score remains the same for Player1
        assertEquals(150, score.getScore());
    }
}
