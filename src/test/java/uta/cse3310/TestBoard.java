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
        Board board = new Board("newWords.txt", 35);
        assertNotNull("Board should be initialized", board.getBoard());
        assertEquals("Default board size should be 20 if environment variable is not set", 20, board.getBoard().length);
    }

    /**
     * Test word placement on the board
     */
    public void testWordPlacement() {
        Board board = new Board("newWords.txt", 35);

        // Clear the board entirely before placing words
        char[][] grid = board.getBoard();
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '-');
        }

        // Now place words on the clear board
        board.putWords(35);
        List<String> placedWords = board.getPlacedWords();

        // Check if all the words are correctly placed
        for (String word : placedWords) {
            assertTrue("The word '" + word + "' should be found on the board", isWordOnBoard(grid, word));
        }
    }

    /**
     * Helper method to check if a word is present on the board
     */
    private boolean isWordOnBoard(char[][] grid, String word) {
        int n = grid.length;
        int m = grid[0].length;
        int len = word.length();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == word.charAt(0)) {
                    // Check horizontally
                    if (j + len <= m && checkHorizontal(grid, i, j, word)) {
                        return true;
                    }
                    // Check vertically down
                    if (i + len <= n && checkVertical(grid, i, j, word)) {
                        return true;
                    }
                    // Check vertically up
                    if (i - len >= -1 && checkVerticalUp(grid, i, j, word)) {
                        return true;
                    }
                    // Check diagonally
                    if (j + len <= m && i + len <= n && checkDiagonal(grid, i, j, word)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    /**
     * Helper method to check if a word is present horizontally on the board
     */
    private boolean checkHorizontal(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (grid[row][col + k] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if a word is present vertically on the board
     */
    private boolean checkVertical(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (grid[row + k][col] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if a word is present diagonally on the board
     */
    private boolean checkDiagonal(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (grid[row + k][col + k] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVerticalUp(char[][] grid, int row, int col, String word) {
        for (int k = 0; k < word.length(); k++) {
            if (row - k < 0 || grid[row - k][col] != word.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test loading words from file
     */
    public void testLoadWordsFromFile() {
        Board board = new Board("newWords.txt", 35);
        // Checking if at least some words have been loaded (assuming the file has at least 5 words)
        assertTrue("Word bank should contain words", board.getPlacedWords().size() > 0);
    }

    /**
     * Test board print functionality
     */
    public void testPrintBoard() {
        Board board = new Board("newWords.txt", 35);
        board.printBoard();
        // This test does not verify output but ensures there are no exceptions during printing
        assertTrue("Print board should complete without error", true);
    }
}


