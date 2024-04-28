package uta.cse3310;

import java.io.*;
import java.util.*;

public class Board {
    private char[][] board;
    private int size;
    private Random random;
    private List<String> wordBank;
    private int overlapCount = 0;  // Track the number of words that have overlapped

    public Board(String filename, int numberOfWords) {
        this.size = determineSizeFromEnv();
        this.board = new char[size][size];
        this.random = new Random();
        this.wordBank = new ArrayList<>();
        loadWordsFromFile(filename, numberOfWords);
        initializeBoard();
    }

    public Board() {
        this("newWords.txt", 35);
    }

    private int determineSizeFromEnv() {
        String gridSize = System.getenv("TEST_GRID");
        try {
            return Integer.parseInt(gridSize);
        } catch (NumberFormatException e) {
            return 20;  // Default size if environment variable is not set
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
        putWords(35);  // Place random words after initializing board
        fillEmptySpaces();
    }

    private void loadWordsFromFile(String filename, int numberOfWords) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim().toUpperCase());
            }
            Collections.shuffle(lines);
            wordBank.addAll(lines.subList(0, Math.min(numberOfWords, lines.size())));
        } catch (IOException e) {
            System.err.println("Failed to load words from file: " + e.getMessage());
        }
    }

    void putWords(int numberOfWords) {
        Collections.shuffle(wordBank);
        List<String> successfullyPlacedWords = new ArrayList<>();
        for (String word : wordBank) {
            if (overlapCount < 10) {
                placeWordWithOverlap(word, successfullyPlacedWords);
            } else {
                placeWordNormally(word, successfullyPlacedWords);
            }
        }
        wordBank.clear();
        wordBank.addAll(successfullyPlacedWords);
    }

    private void placeWordWithOverlap(String word, List<String> successfullyPlacedWords) {
        List<int[]> possiblePositions = findPossiblePositions(word, true);
        if (!possiblePositions.isEmpty()) {
            int[] position = possiblePositions.get(random.nextInt(possiblePositions.size()));
            placeWord(word, position[0], position[1], position[2]);
            successfullyPlacedWords.add(word);
            overlapCount++;
        }
    }

    private void placeWordNormally(String word, List<String> successfullyPlacedWords) {
        List<int[]> possiblePositions = findPossiblePositions(word, false);
        if (!possiblePositions.isEmpty()) {
            int[] position = possiblePositions.get(random.nextInt(possiblePositions.size()));
            placeWord(word, position[0], position[1], position[2]);
            successfullyPlacedWords.add(word);
        } else {
            System.out.println("Unable to place word: " + word);
        }
    }

    private List<int[]> findPossiblePositions(String word, boolean forceOverlap) {
        List<int[]> positions = new ArrayList<>();
        // Attempt to find positions that encourage overlapping
        if (forceOverlap) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] != '-' && containsChar(word, board[i][j])) {
                        for (int orientation = 0; orientation < 3; orientation++) {
                            if (canPlaceWord(word, i, j, orientation)) {
                                positions.add(new int[]{i, j, orientation});
                            }
                        }
                    }
                }
            }
        }
        // If no overlapping positions are possible or not forcing overlap, find any position
        if (positions.isEmpty()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int orientation = 0; orientation < 3; orientation++) {
                        if (canPlaceWord(word, i, j, orientation)) {
                            positions.add(new int[]{i, j, orientation});
                        }
                    }
                }
            }
        }
        return positions;
    }

    private boolean containsChar(String word, char c) {
        return word.indexOf(c) >= 0;
    }

    private boolean canPlaceWord(String word, int row, int col, int orientation) {
        int wordLength = word.length();
        if (orientation == 0 && row + wordLength > size) return false;
        if (orientation == 1 && col + wordLength > size) return false;
        if (orientation == 2 && (row + wordLength > size || col + wordLength > size)) return false;

        for (int i = 0; i < wordLength; i++) {
            int x = row + (orientation == 0 ? i : 0);
            int y = col + (orientation == 1 ? i : 0);
            if (orientation == 2) {
                x = row + i;
                y = col + i;
            }
            if (board[x][y] != '-' && board[x][y] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void placeWord(String word, int row, int col, int orientation) {
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++) {
            int x = row + (orientation == 0 ? i : 0);
            int y = col + (orientation == 1 ? i : 0);
            if (orientation == 2) {
                x = row + i;
                y = col + i;
            }
            board[x][y] = word.charAt(i);
        }
    }

    private void fillEmptySpaces() {
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

    public List<String> getPlacedWords() {
        return wordBank;
    }

     
    public boolean validateSelection(char firstLetter, char secondLetter, int[] firstLetterCoordinate, int[] secondLetterCoordinate){
		if(checkHorizontal(firstLetterCoordinate,secondLetterCoordinate)){
            System.out.println("horizontal");
			return validate(firstLetterCoordinate,secondLetterCoordinate,"horizontal");
        }
		else if(checkVertical(firstLetterCoordinate,secondLetterCoordinate)){
            System.out.println("vertical");
            return validate(firstLetterCoordinate,secondLetterCoordinate,"vertical");
        }
		else if(checkDiagonal(firstLetterCoordinate,secondLetterCoordinate)){
            System.out.println("diagonal");
            return validate(firstLetterCoordinate, secondLetterCoordinate, "diagonal");
        }
		return false;
	}

    public boolean validate(int[] firstLetterCoordinate, int[] secondLetterCoordinate, String direction){
        int length;
        int i;
        int j;
        StringBuilder sb = new StringBuilder();
        String word = new String();
        switch(direction){
            case "horizontal":
                if(firstLetterCoordinate[1] > secondLetterCoordinate[1]){
                    length = firstLetterCoordinate[1]-secondLetterCoordinate[1];
                    for(i = 0; i <= length; i++)
                        sb.append(this.board[(int)firstLetterCoordinate[0]][(int)firstLetterCoordinate[1]-i]);
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                    
                }
                else if(firstLetterCoordinate[1] < secondLetterCoordinate[1]){
                    length = secondLetterCoordinate[1] - firstLetterCoordinate[1];
                    for(i = 0; i <= length; i++){
                        sb.append(this.board[(int)secondLetterCoordinate[0]][(int)secondLetterCoordinate[1]-i]);
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                break;
            case "vertical":
                if(firstLetterCoordinate[0] > secondLetterCoordinate[0]){
                    length = firstLetterCoordinate[0]-secondLetterCoordinate[0];
                    for(i = 0; i <= length; i++)
                        sb.append(this.board[(int)firstLetterCoordinate[0] -i][(int)firstLetterCoordinate[1]]);
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                    
                }
                else if(firstLetterCoordinate[0] < secondLetterCoordinate[0]){
                    length = secondLetterCoordinate[0] - firstLetterCoordinate[0];
                    for(i = 0; i <= length; i++){
                        sb.append(this.board[(int)secondLetterCoordinate[0]-i][(int)secondLetterCoordinate[1]]);
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                break;
            case "diagonal":
            //if first letter is right
            if(firstLetterCoordinate[1] > secondLetterCoordinate[1]){
                //check if down
                if(firstLetterCoordinate[0] > secondLetterCoordinate[0]) {
                    int lengthX = firstLetterCoordinate[0]-secondLetterCoordinate[0];
                    int lengthY = firstLetterCoordinate[1]-secondLetterCoordinate[1];
                    i = 0;
                    j = 0;
                    while (i<lengthX+1 && j < lengthY+1) {
                        sb.append(this.board[(int)firstLetterCoordinate[0]-i][(int)firstLetterCoordinate[1]-j]);
                        i = i + 1;
                        j = j + 1;
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else if (firstLetterCoordinate[0] < secondLetterCoordinate[0]) {
                    int lengthX = secondLetterCoordinate[0]-firstLetterCoordinate[0];
                    int lengthY = firstLetterCoordinate[1]-secondLetterCoordinate[1];
                    i = 0;
                    j = 0;
                    while (i<lengthX+1 && j < lengthY+1) {
                        sb.append(this.board[(int)firstLetterCoordinate[0]+i][(int)firstLetterCoordinate[1]-j]);
                        i = i + 1;
                        j = j + 1;
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                
                
            } else if(firstLetterCoordinate[1] < secondLetterCoordinate[1]){
                //check if down
                if(firstLetterCoordinate[0] < secondLetterCoordinate[0]) {
                    int lengthX = secondLetterCoordinate[0]-firstLetterCoordinate[0];
                    int lengthY = secondLetterCoordinate[1]-firstLetterCoordinate[1];
                    i = 0;
                    j = 0;
                    while (i<lengthX+1 && j < lengthY+1) {
                        sb.append(this.board[(int)secondLetterCoordinate[0]-i][(int)secondLetterCoordinate[1]-j]);
                        i = i + 1;
                        j = j + 1;
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else if (firstLetterCoordinate[0] > secondLetterCoordinate[0]) {
                    int lengthX = firstLetterCoordinate[0]-secondLetterCoordinate[0];
                    int lengthY = secondLetterCoordinate[1]-firstLetterCoordinate[1];
                    i = 0;
                    j = 0;
                    while (i<lengthX+1 && j < lengthY+1) {
                        sb.append(this.board[(int)secondLetterCoordinate[0]+i][(int)secondLetterCoordinate[1]-j]);
                        i = i + 1;
                        j = j + 1;
                    }
                    word = sb.toString();
                    sb.delete(0,sb.length());
                    if(!this.wordBank.contains(word)){
                        StringBuilder reversedString = new StringBuilder(word).reverse();
                        if(!this.wordBank.contains(reversedString.toString())) {
                            return false;
                            
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                
                
            }
            
                break;
        }
        
        return false;
    }


    public boolean checkHorizontal(int[] first, int[] last){
	
		if((first[1] != last[1])&&(first[0] == last[0]))
			return true;
		return false;
	}
	
	public boolean checkVertical(int[] first, int[] last){
	
		if((first[1] == last[1])&&(first[0] != last[0]))
			return true;
		return false;
	}
	
	public boolean checkDiagonal(int[] first, int[] last){
	
		if(first[0] == last[0] || first[1] == last[1])
			return false;

		if(Math.abs(((last[1]-first[1]))/((last[0]-first[0])))==1)
			return true;
		return false;
	}
}
