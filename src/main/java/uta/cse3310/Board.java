package uta.cse3310;

import java.io.*;
import java.util.*;

public class Board {
    private char[][] board;
    private int size;
    private Random random;
    private List<String> wordBank;

    // Default constructor with customizable filename and word count
    public Board(String filename, int numberOfWords) {
        this.size = determineSizeFromEnv();
        this.board = new char[size][size];
        this.random = new Random();
        this.wordBank = new ArrayList<>();
        loadWordsFromFile(filename, numberOfWords);
        initializeBoard();

    }

    // Overload constructor for default settings
    public Board() {
        this("newWords.txt", 20);  // Default filename and number of words
    }

    private int determineSizeFromEnv() {
        String gridSize = System.getenv("TEST_GRID");
        try {
            return Integer.parseInt(gridSize);
        } catch (NumberFormatException e) {
            return 35; // Default size if environment variable is not set
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
        putWords(20); // Place random words after initializing board
        fillEmptySpaces();
    }

    private void loadWordsFromFile(String filename, int numberOfWords) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
            Collections.shuffle(lines); // Shuffle the lines to randomize
            wordBank.addAll(lines.subList(0, Math.min(numberOfWords, lines.size()))); // Add only the required number of words
        } catch (IOException e) {
            System.err.println("Failed to load words from file: " + e.getMessage());
        }
    }

    void putWords(int numberOfWords) {
        List<String> wordsToPlace = new ArrayList<>();
        int placedCount = 0;
        for (String word : wordBank) {
            if (placedCount >= numberOfWords) {
                break; // Stop if enough words are placed
            }
            List<int[]> possiblePositions = findPossiblePositions(word);
            if (!possiblePositions.isEmpty()) {
                Collections.shuffle(possiblePositions); // Randomize possible positions
                for (int[] position : possiblePositions) {
                    int row = position[0];
                    int col = position[1];
                    int orientation = position[2]; // 0 for vertical, 1 for horizontal, 2 for diagonal

                    if (canPlaceWord(word, row, col, orientation)) {
                        placeWord(word, row, col, orientation);
                        wordsToPlace.add(word); // Add the placed word to the list
                        placedCount++;
                        break;
                    }
                }
            }
        }
        wordBank.clear(); // Clear the word bank
        wordBank.addAll(wordsToPlace); // Add the placed words to the word bank
    }

    private List<int[]> findPossiblePositions(String word) {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPlaceWord(word, i, j, 0)) {  // Check vertical possibility
                    positions.add(new int[]{i, j, 0});
                }
                if (canPlaceWord(word, i, j, 1)) {  // Check horizontal possibility
                    positions.add(new int[]{i, j, 1});
                }
                if (canPlaceWord(word, i, j, 2)) {  // Check diagonal possibility
                    positions.add(new int[]{i, j, 2});
                }
            }
        }
        return positions;
    }

    private boolean canPlaceWord(String word, int row, int col, int orientation) {
        if (orientation == 0) {
            // Vertical placement
            if (row + word.length() > size) {
                return false; // Word exceeds board size vertically
            }
            for (int i = 0; i < word.length(); i++) {
                if (board[row + i][col] != '-' && board[row + i][col] != word.charAt(i)) {
                    return false; // Word clashes with existing letter
                }
            }
        } else if (orientation == 1) {
            // Horizontal placement
            if (col + word.length() > size) {
                return false; // Word exceeds board size horizontally
            }
            for (int i = 0; i < word.length(); i++) {
                if (board[row][col + i] != '-' && board[row][col + i] != word.charAt(i)) {
                    return false; // Word clashes with existing letter
                }
            }
        } else if (orientation == 2) {
            // Diagonal placement
            if (row + word.length() > size || col + word.length() > size) {
                return false; // Word exceeds board size diagonally
            }
            for (int i = 0; i < word.length(); i++) {
                if (board[row + i][col + i] != '-' && board[row + i][col + i] != word.charAt(i)) {
                    return false; // Word clashes with existing letter
                }
            }
        }
        return true;
    }

    private void placeWord(String word, int row, int col, int orientation) {
        if (orientation == 0) {
            // Vertical placement
            for (int i = 0; i < word.length(); i++) {
                board[row + i][col] = word.charAt(i);
            }
        } else if (orientation == 1) {
            // Horizontal placement
            for (int i = 0; i < word.length(); i++) {
                board[row][col + i] = word.charAt(i);
            }
        } else if (orientation == 2) {
            // Diagonal placement
            for (int i = 0; i < word.length(); i++) {
                board[row + i][col + i] = word.charAt(i);
            }
        }
    }

    private void fillEmptySpaces() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = (char) ('A' + random.nextInt(26)); // Fill with a random letter
                }
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<String> getPlacedWords() {
        return wordBank;
    }
}
