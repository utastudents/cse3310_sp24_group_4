package uta.cse3310;
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

    }
    public boolean checkWinWord(String word){
        // checks the winning word and returns true or false

    }
    public boolean checkDupe(String word){
        //checks if word is duplicate or not

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
}
