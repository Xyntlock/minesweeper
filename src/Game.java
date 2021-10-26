package src;

import java.util.Arrays;

public class Game {

    public Board board = new Board();

    /**
     * For rendering the UI
     */
    public final int TILE_WIDTH = 20;
    public final int TILE_HEIGHT = 20;

    public Game() {

    }

    public void printBoard() {
        System.out.println(Arrays.toString(board.cells));
    }
}