package strategy;

import boards.TicTacToeBoard;
import game.Cell;
import game.Player;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class OptionalStrategy extends Strategy{
    // These condition should run in this order only it has
    // to be in checked after previous conditions are checked
    // If you have winning move play it
    // If opponent has a winning move then block it
    // If you have a fork then play it
    // If opponent has a fork block it
    // If center is available play it
    // If corner is available take it
    @Override
    public Cell getOptimalMove(TicTacToeBoard board, Player player) {
        Placement placement = OffensivePlacement.get();
        while (placement.next() != null){
            Optional<Cell> place = placement.place(board, player);
            if (place.isPresent()){
                return place.get();
            }
            placement = placement.next();
        }
        return null;
    }
}
