package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CornerPlacement implements Placement{

    private static CornerPlacement cornerPlacement;

    private CornerPlacement() {
    }

    public synchronized static CornerPlacement get(){
        cornerPlacement = (CornerPlacement) Utils.getIfNull(cornerPlacement, () -> new CornerPlacement());
        return cornerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(placeInCorner(board));
    }

    private Cell placeInCorner(TicTacToeBoard board) {
        final int[][] corners = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for(int i=0; i<4; i++){
            if(board.getSymbol(corners[i][0], corners[i][1]) == null){
                return new Cell(corners[i][0], corners[i][1]);
            }
        }
        return null;
    }

    @Override
    public Placement next() {
        return null;
    }
}
