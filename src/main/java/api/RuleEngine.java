package api;

import boards.Board;
import boards.TicTacToeBoard;
import game.*;

import java.util.HashMap;

public class RuleEngine {

    HashMap<String, RuleSet<TicTacToeBoard>> ruleMap = new HashMap<>();

    // To check for fork state in the board
    // It is a state where whatever the next move the opponent wins
    /* X-O
       -O-
       X-X
     */
    //Here whatever move O makes X wins the game
    // For detecting this state this function
    public GameInfo getInfo(Board board){
        if (board instanceof TicTacToeBoard) {
            GameState gameState = getState(board);
            String[] players = new String[]{"X", "O"};
            Cell forkCell = null;
            for (int index = 0; index < 2; index++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Board b = board.copy();
                        Player player = new Player(players[index]);
                        b.move(new Move(new Cell(i, j), new Player("X")));
                        boolean canStillWin = false;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                Board b1 = b.copy();
                                forkCell = new Cell(k, l);
                                b1.move(new Move(forkCell, player.flip()));
                                if (getState(b1).getWinner().equals(player.flip().symbol())) {
                                    canStillWin = true;
                                    break;
                                }
                            }
                            if (!canStillWin) {
                                break;
                            }
                        }
                        if (canStillWin) {
                            return new GameInfoBuilder()
                                    .isOver(gameState.isOver())
                                    .winner(gameState.getWinner())
                                    .hasFork(true)
                                    .forkCell(forkCell)
                                    .player(player.flip()).build();
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

