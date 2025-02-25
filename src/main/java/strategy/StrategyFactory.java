package strategy;

import api.RuleEngine;
import boards.TicTacToeBoard;
import game.Cell;
import game.Move;
import game.Player;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class StrategyFactory {

    public Strategy getStrategy(TicTacToeBoard b, Player player) {
        Strategy strategy = null;
        int threshold = 3;
        if(countMoves(b) < threshold) {
            strategy = new BasicStrategy();
        } else if(countMoves(b) < threshold + 1){
            strategy = new SmartStrategy();
        } else if (player.getTimeUsedInMillis() > 100000) {
            strategy = new BasicStrategy();
        } else {
            strategy = new OptionalStrategy();
        }
        return strategy;
    }

    private int countMoves(TicTacToeBoard board) {
        int count = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (board.getSymbol(i, j) != null) {
                    count++;
                }
            }
        }
        return count;
    }
}
