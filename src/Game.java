package src;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

    public Board board;

    /**
     * Game state is how the game determins the win/loss/in-progress
     * 0 in progress
     * 1 is loss
     * 2 is win
     * This is so that the while loop can run
     */
    public int state = 0;

    /**
     * For rendering the UI perhaps
     */
    public final int TILE_WIDTH = 20;
    public final int TILE_HEIGHT = 20;

    public Game(int bombCount, int columns, int rows) {
        board = new Board(bombCount, columns, rows);
    }

    /**
     * Imitating the process of clicking on a cell
     * @param cell - The cell being clicked on
     * @param flag - Whether or not the cell is being flagged as a bomb
     */
    public void click(int cell, boolean flag) {

        if (board.cells[cell] == 11) return;

        if (Arrays.stream(board.bombIndices).anyMatch(x -> x == cell)) {
            // Change game state to 1, You lost :(
            state = 1;
            return;

        } else {

            int adjacentBombs = board.getAdjacentBombCount(cell);

            if (adjacentBombs == 0) {

                board.cells[cell] = 11;

                for (int newCell: board.getAdjacentCells(cell)) {

                    // Click surrounding cells
                    click(newCell, false);
                }

            } else {

                // Set number
                board.cells[cell] = adjacentBombs;

            }

        }
    }

}