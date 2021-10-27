package src;

import java.util.*;
import java.util.stream.IntStream;

/**
1D or 2D array, whichever is easier
*/

public class Board {


    // Generate grid
    public int columns;
    public int rows;
    public int bombCount;

    public int[] cells;
    public int[] bombIndices;

    public final int[] relativeAdjacentCells;

    public Board(int b, int c, int r) {
        bombCount = b;
        columns = c;
        rows = r;

        cells = new int[rows * columns];
        relativeAdjacentCells = new int[]{-columns - 1, -columns, -columns + 1, -1, +1, columns - 1, columns, columns + 1};
    }

    /**
     * Initiates the board, regenerates if the clicked cell has a bomb adjacent
     * @param cell The cell initially clicked
     */
    public void init(int cell) {

        do {

            bombIndices = generateBombPositions();

        } while (getAdjacentBombCount(cell) != 0 || isBomb(cell));

    }

    /**
     * Generates the index of the bombs to be placed
     * Does this by randomly sorting an array from 1-CellsLength and taking the first X values
     * @return int[]
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

    /**
     * Gets the adjacent cells to a given cell. (Ignores illegal)
     * @param cell
     * @return int[]
     */
    public int[] getAdjacentCells(int cell) {

        // Initiate empty array
        int[] adjCells = new int[0];

        // Iterate through relative adjacent cells
        for (int i: relativeAdjacentCells) {

            // New cell to check (doesnt necesarily exist)
            int newCell = cell + i;

            // If new cell is out of bounds above or below the boundries
            if (newCell < 0 || newCell >= cells.length) continue;

            // If new cell wraps around
            if (Math.abs(cellPos(newCell)[0] - cellPos(cell)[0]) > 1) continue;

            // To add a new element, make a new array with +1 length
            int[] newCells = new int[adjCells.length + 1];

            // Copy the old array to the new one
            if (adjCells.length > 0) {

                for (int ii = 0; ii < adjCells.length; ii++) {
                    newCells[ii] = adjCells[ii];
                }
            }

            // Add the new one
            newCells[adjCells.length] = newCell;

            adjCells = newCells;

        }

        return adjCells;
    }

    /**
     * Checks how many bombs are in the surrounding cells
     * @param cell Cell to check
     * @return int
     */
    public int getAdjacentBombCount(int cell) {

        // Check surrounding cells for bombs
        int adjacentBombs = 0;

        // Array of all adjacent cells
        int[] adjacentCells = new int[]{-columns - 1, -columns, -columns + 1, -1, +1, columns - 1, columns, columns + 1};

        for (int newCell: getAdjacentCells(cell)) {

            // Increment adjacent bomb counter if there is a bomb in the newCell
            if (Arrays.stream(bombIndices).anyMatch(x -> x == newCell)) {
                adjacentBombs++;
            }

        }

        return adjacentBombs;

    }

    public boolean isBomb(int cell) {
        return Arrays.stream(bombIndices).anyMatch(x -> x == cell);
    }

    /**
     * Integer codes for display:
     * 0 for unchecked,
     * 1-8 for adjacent bombs,
     * 9 for flagged,
     * 10 for bomb,
     * 11 for empty
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

    public int[] cellPos(int cell) {
        int x = cell % columns;
        int y = cell/rows;

        return new int[]{x, y};
    }

    /**
     * Given a number 0-10, returns the corresponding display character
     * @param i - The integer representing the display
     * @return string
     */
    private String integerToDisplayChar(int i) {
        // Display characters layed out with corresponding index
        String[] chars = {" ", "\u001B[34m1\u001B[0m", "\u001B[32m2\u001B[0m", "\u001B[31m3\u001B[0m", "\u001B[35m4\u001B[0m", "\u001B[36m5\u001B[0m", "\u001B[33m6\u001B[0m", "7", "8", "F", "\u001B[31mX\u001B[0m", "\u001B[30mO\u001B[0m"};
        return chars[i];
    }

    /**
     * Simple function to display the board in the CLI
     */
    public void print() {

        // Print top row
        System.out.print("┌");
        for (int i = 0; i < columns; i++) {
            System.out.print("─────");
            if (i+1 != columns) System.out.print("┬");
        }
        System.out.println("┐");

        // Print each row
        for (int j = 0; j < rows; j++) {

            // Print numbers and sides
            System.out.print("│");
            for (int i = 0; i < columns; i++) {
                System.out.print("  " + integerToDisplayChar(cells[j * rows + i]) + "  ");
                System.out.print("│");
            }
            System.out.println("  " + j*columns);

            // Print between rows
            if (j + 1 != rows) {
                System.out.print("├");
                for (int i = 0; i < columns; i++) {
                    System.out.print("─────");
                    if (i + 1 != columns) System.out.print("┼");
                }
                System.out.println("┤");
            }
        }

        // Print bottom row
        System.out.print("└");
        for (int i = 0; i < columns; i++) {
            System.out.print("─────");
            if (i+1 != columns) System.out.print("┴");
        }
        System.out.println("┘");
    }

}