package src;

import java.util.Arrays;
import java.util.Scanner;

public class Minesweeper {

    public static void main(String[] args) {

        // Reading
        Scanner reader = new Scanner(System.in);

        System.out.print("Rows: ");
        int rows = reader.nextInt();

        System.out.print("Columns: ");
        int columns = reader.nextInt();

        System.out.print("Bombs: ");
        int bombCount = reader.nextInt();

        // Create Game
        Game game = new Game(bombCount, columns, rows);

        System.out.println("Bombs located: " + Arrays.toString(game.board.bombIndices));

        game.board.print();
        System.out.println("Select a square to click: ");
        int initCell = reader.nextInt();

        game.board.init(initCell);
        game.click(initCell, false);

        while (game.state == 0) {

            // Some space
            System.out.println("\n\n\n\n");

            // Print the board in the CLI
            game.board.print();

            // Ask for user input, currentlyt just the index
            System.out.println("Select a square to click: ");
            int cell = reader.nextInt();

            game.click(cell, false);

        }

        for (int i: game.board.bombIndices) {
            game.board.cells[i] = 10;
        }

        // Finish the game
        System.out.println("\n\n\n\n");
        printEnd(game.state);
        game.board.print();

    }

    public static void printEnd(int state) {

        switch (state) {

            case 1:
                System.out.println("                          YOU'RE A LOSER\n");
                break;

            case 2:
                System.out.println("                          YOU'RE A WINNER\n");
                break;

        }
    }
}