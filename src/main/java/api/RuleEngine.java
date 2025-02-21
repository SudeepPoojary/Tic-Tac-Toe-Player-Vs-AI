package api;

import boards.Board;
import boards.CellBoard;
import boards.TicTacToeBoard;
import game.*;
import placements.DefensivePlacement;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.HashMap;
import java.util.Optional;

public class RuleEngine {

    HashMap<String, RuleSet> ruleMap = new HashMap<>();

    // To check for fork state in the board
    // It is a state where whatever the next move the opponent wins
    /* X-O
       -O-
       X-X
     */
    //Here whatever move O makes X wins the game
    // For detecting this state this function
    public GameInfo getInfo(CellBoard board){
        if (board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            GameState gameState = getState(board);
            for (TicTacToeBoard.Symbol symbol : TicTacToeBoard.Symbol.values()) {
                Player player = new Player(symbol.marker());
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(board.getSymbol(i, j) != null) {
                            TicTacToeBoard b = ticTacToeBoard.move(new Move(new Cell(i, j), player));
                            // force opponent to make a defensive move
                            // We still win
                            DefensivePlacement defense = DefensivePlacement.get();
                            Optional<Cell> defensiveCell = defense.place(b, player.flip());
                            if (defensiveCell.isPresent()){
                                b = b.move(new Move(defensiveCell.get(), player.flip()));
                                OffensivePlacement offense = OffensivePlacement.get();
                                Optional<Cell> offensiveCell = offense.place(b, player);
                                if (offensiveCell.isPresent()){
                                    return new GameInfoBuilder()
                                            .isOver(gameState.isOver())
                                            .winner(gameState.getWinner())
                                            .hasFork(true)
                                            .forkCell(new Cell(i, j))
                                            .player(player.flip()).build();
                                }
                            }
                        }
                    }
                }
            }
            return new GameInfoBuilder()
                    .isOver(gameState.isOver())
                    .winner(gameState.getWinner())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public RuleEngine() {
        ruleMap.put(TicTacToeBoard.class.getName(), TicTacToeBoard.getRules());
    }


    public GameState getState(Board board) {
        if (board instanceof TicTacToeBoard) {
            TicTacToeBoard b = (TicTacToeBoard) board;
            for(Rule r : ruleMap.get(TicTacToeBoard.class.getName())) {
                GameState gameState = r.condition.apply(b);
                if(gameState.isOver()){
                    return gameState;
                }
            }
            return new GameState(false, "-");
        } else {
            throw new IllegalArgumentException();
        }
    }
}

