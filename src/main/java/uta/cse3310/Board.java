/*package uta.cse3310;
import java.util.List;

public class Board {
    private char[][] board;
    private int size;
    private String letters;
    private String validWords;

    public Board(int size) {
        this.size = size;
        this.board = new char[size][size];
    }

    public void placeWords(List<String> words) {
        // Implementation to place words on the board
    }

    public void printBoard() {
        // Implementation to print the board
    }
    
    public boolean checkWin(){
        // checks win and returns true or false
        return true;
    }
    public boolean checkWinWord(String word){
        // checks the winning word and returns true or false
        return true;
    }
    public boolean checkDupe(String word){
        //checks if word is duplicate or not
        return true;
    }
    public void changeColor(String color){
        //changes the color

    }
    public void createBoard(float density){
        //creates the board

    }
    public void giveHint(){
        //gives hint

    }
    public void displayList(){
        //displays list

    }
    public void voteRestart(){
        //allows the user to vote to restart game

    }
}*/



package uta.cse3310;

import java.util.List;
import java.util.Random;

public class Board {
    private char[][] board;
    private int size;
    private Random random = new Random();

    public Board() {
        this.size = determineSizeFromEnv();
        this.board = new char[size][size];
        initializeBoard();
    }

    // Overloaded constructor for testing with a specific size
    public Board(int size) {
        this.size = size;
        this.board = new char[this.size][this.size];
        initializeBoard();
    }

    private int determineSizeFromEnv() {
        String gridSize = System.getenv("TEST_GRID");
        try {
            return Integer.parseInt(gridSize);
        } catch (NumberFormatException e) {
            return 10;  // Default size if environment variable is not set
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void placeWords(List<String> words) {
        for (String word : words) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                boolean horizontal = random.nextBoolean(); // Randomly decide orientation
                if (canPlaceWord(word, row, col, horizontal)) {
                    for (int i = 0; i < word.length(); i++) {
                        board[horizontal ? row : row + i][horizontal ? col + i : col] = word.charAt(i);
                    }
                    placed = true;
                }
            }
        }
        fillRandomLetters();
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

    private void fillRandomLetters() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = (char) ('A' + random.nextInt(26));
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

    public boolean checkWin(){
        // checks win and returns true or false
        return true;
    }
    public boolean checkWinWord(String word){
        // checks the winning word and returns true or false
        return true;
    }
    public boolean checkDupe(String word){
        //checks if word is duplicate or not
        return true;
    }
    public void changeColor(String color){
        //changes the color

    }
    public void giveHint(){
        //gives hint

    }
    public void displayList(){
        //displays list

    }
    public void voteRestart(){
        //allows the user to vote to restart game

    }
}



