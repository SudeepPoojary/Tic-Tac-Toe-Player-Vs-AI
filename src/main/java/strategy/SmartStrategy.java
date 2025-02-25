package strategy;

import api.RuleEngine;
import boards.TicTacToeBoard;
import game.Cell;
import game.Move;
import game.Player;

public class SmartStrategy extends Strategy {
    /*
        1. Can AI win with this move?
            Make winning move
        2. Will human win with their next move?
            Block human from winning
         */

    private final RuleEngine ruleEngine = new RuleEngine();
    private final BasicStrategy basicStrategy = new BasicStrategy();

    @Override
    public Cell getOptimalMove(TicTacToeBoard board, Player player) {
        //Victory moves
        Cell best = offense(player, board);
        if (best != null) return best;
        //Defencive moves
        best = defense(player, board);
        if (best != null) return best;
        return basicStrategy.getOptimalMove(board, player);
    }

    private Cell offense(Player player, TicTacToeBoard board) {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (board.getSymbol(i, j) == null) {
                    Move move = new Move(Cell.getCell(i, j), player);
                    TicTacToeBoard boardCopy = (TicTacToeBoard) board.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return move.getCell();
                    }
                }
            }
        }
        return null;
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

}
