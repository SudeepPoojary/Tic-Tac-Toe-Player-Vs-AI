package api;

import boards.Board;
import boards.TicTacToeBoard;
import game.*;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class AIEngine {


    private RuleEngine ruleEngine;

    public Move suggestMove(Player player, Board board) {
        if(board instanceof TicTacToeBoard){
            TicTacToeBoard b = (TicTacToeBoard) board;
            Cell suggestion;
            int threshold = 3;
            if(countMoves(b) < threshold) {
                suggestion = getBasicMove(b);
            } else if(countMoves(b) < threshold + 1){
                suggestion = getCellToPlay(player, b);
            } else {
                suggestion = getOptimalMove(player, b);
            }
            if (suggestion != null) return new Move(suggestion, player);
            throw new IllegalStateException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Cell getOptimalMove(Player player, TicTacToeBoard board) {
        // These condition should run in this order only it has
        // to be in checked after previous conditions are checked
        // If you have winning move play it
        // If opponent has a winning move then block it
        // If you have a fork then play it
        // If opponent has a fork block it
        // If center is available play it
        // If corner is available take it
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






    private Cell getCellToPlay(Player player, TicTacToeBoard board) {
        /*
        1. Can AI win with this move?
            Make winning move
        2. Will human win with their next move?
            Block human from winning
         */
        //Victory moves
        Cell best = offense(player, board);
        if (best != null) return best;
        //Defencive moves
        best = defense(player, board);
        if (best != null) return best;
        return getBasicMove(board);
    }

    private Cell offense(Player player, TicTacToeBoard board) {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (board.getSymbol(i, j) == null) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = board.copy();
                    boardCopy.move(move);
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
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = board.copy();
                    boardCopy.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }

    private static Cell getBasicMove(TicTacToeBoard board1) {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (board1.getSymbol(i, j) == null) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
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
