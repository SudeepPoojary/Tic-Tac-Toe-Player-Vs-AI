package game;

public class Move {
    public Player getPlayer() {
        return player;
    }

    private Player player;
    private Cell cell;

    public Move(Cell cell, Player player) {
        this.cell = cell;
        this.player = player;
    }


    public Cell getCell() {
        return cell;
    }
}

