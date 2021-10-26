package src;

import java.util.*;
import java.util.stream.IntStream;

/**
1D or 2D array, whichever is easier
*/

public class Board {

    public int columns = 16;
    public int rows = 16;
    public int bombCount = 32;

    public Board() {
        init();
    }

    public Board(int b, int c, int r) {
        bombCount = b;
        columns = c;
        rows = r;

        init();
    }

    public int[] cells = new int[rows * columns];

    /**
     * Generates the index of the bombs to be placed
     * Does this by randomly sorting an array from 1-CellsLength and taking the first X values
     * @return {int}
     */
    private int[] generateBombPositions() {

        // Generate range of numbers of the indices to the cells.
        int[] range = IntStream.rangeClosed(0, cells.length - 1).toArray();

        // Shuffle the array
        Random rand = new Random();
        for (int i = 0; i < range.length; i++) {
            int randomIndexToSwap = rand.nextInt(range.length);
            int temp = range[randomIndexToSwap];
            range[randomIndexToSwap] = range[i];
            range[i] = temp;
        }

        // Take first X elements
        int[] indices = Arrays.copyOfRange(range, 0, bombCount);

        return indices;
    }

    public void init() {

        int[] indices = generateBombPositions();

        for (int i : indices) {
            cells[i] = 10;
        }

    }

    /**
     * Integer codes for display
     * 0 for unchecked
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8
     * 9 for flagged
     * 10 for bomb
     */

    public void displayRaw() {

        for (int j = 0; j < rows; j++) {

            for (int i = 0; i < columns; i++) {
                System.out.print(cells[j * rows + i]);
                System.out.print(" ");
            }

            System.out.println();

        }
    };

    /**
     * Given a number 0-10, returns the corresponding display character
     * @param i - The integer representing the display
     * @return
     */
    private String displayInt(int i) {
        // Display characters layed out with corresponding index
        String[] chars = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "F", "X"};
        return chars[i];
    }

    /**
     * Simple function to display the board in the CLI
     */
    public void display() {

        // Print top row
        System.out.print('┌');
        for (int i = 0; i < columns; i++) {
            System.out.print('─'); System.out.print('─'); System.out.print('─');
            if (i+1 != columns) System.out.print('┬');
        }
        System.out.println('┐');

        // Print each row
        for (int j = 0; j < rows; j++) {

            // Print numbers and sides
            System.out.print("│");
            for (int i = 0; i < columns; i++) {
                System.out.print(' ' + displayInt(cells[j * rows + i]) + ' ');
                System.out.print("│");
            }
            System.out.println();

            // Print between rows
            if (j + 1 != rows) {
                System.out.print('├');
                for (int i = 0; i < columns; i++) {
                    System.out.print('─'); System.out.print('─'); System.out.print('─');
                    if (i + 1 != columns) System.out.print('┼');
                }
                System.out.println('┤');
            }
        }

        // Print bottom row
        System.out.print('└');
        for (int i = 0; i < columns; i++) {
            System.out.print('─'); System.out.print('─'); System.out.print('─');
            if (i+1 != columns) System.out.print('┴');
        }
        System.out.println('┘');
    }

}