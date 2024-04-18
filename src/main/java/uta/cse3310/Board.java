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
            return 10; // Default size if environment variable is not set
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
        placeWords(wordBank); // Place words after initializing board
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

    public void placeWords(List<String> words) {
        for (String word : words) {
            List<int[]> possiblePositions = findPossiblePositions(word);
            if (possiblePositions.isEmpty()) {
                System.err.println("Cannot place word due to lack of space: " + word);
                continue;
            }

            boolean placed = false;
            Collections.shuffle(possiblePositions); // Randomize possible positions
            for (int[] position : possiblePositions) {
                int row = position[0];
                int col = position[1];
                boolean horizontal = position[2] == 1; // 1 for horizontal, 0 for vertical

                if (canPlaceWord(word, row, col, horizontal)) {
                    for (int i = 0; i < word.length(); i++) {
                        board[horizontal ? row : row + i][horizontal ? col + i : col] = word.charAt(i);
                    }
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                System.err.println("Failed to place word after checking all positions: " + word);
            }
        }
    }

    private List<int[]> findPossiblePositions(String word) {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPlaceWord(word, i, j, true)) {  // Check horizontal possibility
                    positions.add(new int[]{i, j, 1});
                }
                if (canPlaceWord(word, i, j, false)) {  // Check vertical possibility
                    positions.add(new int[]{i, j, 0});
                }
            }
        }
        return positions;
    }

    public List<String> getWordBank() {
        return wordBank;
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
    

    private boolean canPlaceWord(String word, int row, int col, boolean horizontal) {
        int deltaRow = horizontal ? 0 : 1;
        int deltaCol = horizontal ? 1 : 0;
        for (int i = 0; i < word.length(); i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;
            if (newRow >= size || newCol >= size || board[newRow][newCol] != '-') return false;
        }
        return true;
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
}
