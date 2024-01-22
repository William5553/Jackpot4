package structs;

import java.util.Random;

public class GamePiece {
    private final Random rand = new Random();
    public int row, column, x, y, player, rotation;

    public GamePiece(int row, int column, int x, int y, int player) {
        this.row = row; // the row the piece is in
        this.column = column; // the column the piece is in
        this.x = x; // the x coordinate of the piece
        this.y = y; // the y coordinate of the piece
        this.player = player; // the player that owns the piece
        this.rotation = rand.nextInt(0, 360);
    }
}
