package uta.cse3310;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestBoard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display options to the user
        System.out.println("Select the grid size:");
        System.out.println("1 - 10x10 Grid");
        System.out.println("2 - 20x20 Grid");
        System.out.println("3 - 30x30 Grid");
        System.out.println("4 - 40x40 Grid");
        System.out.println("5 - 50x50 Grid");
        System.out.print("Enter your choice (1-5): ");
        
        // Read the user input
        int choice = scanner.nextInt();
        int size = 10; // default size

        // Determine the size based on the user choice
        switch (choice) {
            case 1:
                size = 10;
                break;
            case 2:
                size = 20;
                break;
            case 3:
                size = 30;
                break;
            case 4:
                size = 40;
                break;
            case 5:
                size = 50;
                break;
            default:
                System.out.println("Invalid choice. Using default size 10x10.");
                break;
        }

        scanner.close(); // Close the scanner

        // Initialize the board with the selected size
        Board board = new Board(size);

        // Define words for the test
        List<String> words = Arrays.asList("JAVA", "GAME", "MOBILE", "DESKTOP");

        // Create the board and place words
        board.placeWords(words);
        board.printBoard();
    }
}
