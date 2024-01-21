package structs;

public class GamePiece {
    public int row, column, x, y, player;

    public GamePiece(int row, int column, int x, int y, int player) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        this.player = player;
    }
}
