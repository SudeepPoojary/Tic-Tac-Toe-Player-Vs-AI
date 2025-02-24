package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.Move;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class DefensivePlacement implements Placement{
    private static DefensivePlacement defensivePlacement;

    private DefensivePlacement() {

    }

    public synchronized static DefensivePlacement get(){
        defensivePlacement = (DefensivePlacement) Utils.getIfNull(defensivePlacement, () -> new DefensivePlacement());
        return defensivePlacement;
    }


    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(defense(player, board));
    }

    private Cell defense(Player player, TicTacToeBoard board) {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (board.getSymbol(i, j) == null) {
                    Move move = new Move(Cell.getCell(i, j), player.flip());
                    TicTacToeBoard boardCopy = board.copy();
                    boardCopy.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return Cell.getCell(i, j);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Placement next() {
        return ForkPlacement.get();
    }
}
