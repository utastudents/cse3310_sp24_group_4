/*package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Arrays;

public class TestBoard extends TestCase {
    public TestBoard(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestBoard.class);
    }

    public void testRandomWordPlacement() {
        Board board = new Board(10, 42); // Using a fixed seed for reproducibility
        board.placeWords(Arrays.asList("HELLO"));
        boolean found = false;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == 'H') {
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        assertTrue("The word should be found on the board.", found);
    }
}*/

package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;
import java.util.List;

public class TestBoard extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestBoard(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TestBoard.class);
    }

    /**
     * Test constructor to ensure proper board initialization
     */
    public void testBoardInitialization() {
        Board board = new Board("newWords.txt", 5);
        assertNotNull("Board should be initialized", board.getBoard());
        assertEquals("Default board size should be 10 if environment variable is not set", 10, board.getBoard().length);
    }

    /**
     * Test word placement on the board
     */
    public void testWordPlacement() {
        Board board = new Board("newWords.txt", 5);

        // Clear the board entirely before placing "TEST"
        char[][] grid = board.getBoard();
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '-');  // Assuming '-' is used to indicate empty spaces in the Board class
        }
    
        // Now place "TEST" on the clear board
        List<String> words = Arrays.asList("TEST");
        board.placeWords(words);  // This should find space easily since the board is clear
    
        boolean found = false;
    
        // Check if the word "TEST" is correctly placed
        outerLoop:
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'T') {
                    // Check horizontally and vertically
                    if (j + words.get(0).length() <= grid[i].length && checkHorizontalPlacement(grid, i, j, words.get(0))) {
                        found = true;
                        break outerLoop;
                    }
                    if (i + words.get(0).length() <= grid.length && checkVerticalPlacement(grid, i, j, words.get(0))) {
                        found = true;
                        break outerLoop;
                    }
                }
            }
        }
        assertTrue("The word 'TEST' should be found on the board", found);
    }

    /**
     * Helper method to check horizontal placement
     */
    private boolean checkHorizontalPlacement(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (grid[row][col + k] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check vertical placement
     */
    private boolean checkVerticalPlacement(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (grid[row + k][col] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test loading words from file
     */
    public void testLoadWordsFromFile() {
        Board board = new Board("newWords.txt", 5);
        // Checking if at least some words have been loaded (assuming the file has at least 5 words)
        assertTrue("Word bank should contain words", board.getWordBank().size() > 0);
    }

    /**
     * Test board print functionality
     */
    public void testPrintBoard() {
        Board board = new Board("newWords.txt", 5);
        board.printBoard();
        // This test does not verify output but ensures there are no exceptions during printing
        assertTrue("Print board should complete without error", true);
    }
}

