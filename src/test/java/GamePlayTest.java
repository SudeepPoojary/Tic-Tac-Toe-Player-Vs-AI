import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import boards.Board;
import game.Cell;
import game.Move;
import game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GamePlayTest {

    GameEngine gameEngine;
    AIEngine aiEngine;
    RuleEngine ruleEngine;

    @Before
    public void setup() {
        gameEngine = new GameEngine();
        ruleEngine = new RuleEngine();
        aiEngine = new AIEngine();
    }

    @Test
    public void checkForRowWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][] {{1, 0}, {1, 1}, {1, 2}};
        int[][] secondPlayerMoves = new int[][] {{0, 0}, {0, 1}, {0, 2}};
        //Make moves
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForColWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][] {{0, 0}, {1, 0}, {2, 0}};
        int[][] secondPlayerMoves = new int[][] {{0, 1}, {0, 2}, {1, 1}};
        //Make moves
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForDiagWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][] {{0, 0}, {1, 1}, {2, 2}};
        int[][] secondPlayerMoves = new int[][] {{0, 1}, {0, 2}, {1, 0}};
        //Make moves
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForRevDiagWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][] {{0, 2}, {1, 1}, {2, 0}};
        int[][] secondPlayerMoves = new int[][] {{0, 0}, {0, 1}, {1, 0}};
        //Make moves
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForSecondPlayerWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][] {{1, 0}, {1, 1}, {2, 0}};
        int[][] secondPlayerMoves = new int[][] {{0, 0}, {0, 1}, {0, 2}};
        //Make moves
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("O", ruleEngine.getState(board).getWinner());
    }

    private void playGame(Board board, int[][] firstPlayerMoves, int[][] secondPlayerMoves) {
        int row, col, next = 0;
        while (!ruleEngine.getState(board).isOver()) {
            Player first = new Player("X"), second = new Player("O");
//            System.out.println("Make your move");
//            System.out.println(board);
            row = firstPlayerMoves[next][0];
            col = firstPlayerMoves[next][1];
            Move firstPlayerMove = new Move(new Cell(row, col), first);
            gameEngine.move(board, firstPlayerMove);
            if (!ruleEngine.getState(board).isOver()) {
                int sRow = secondPlayerMoves[next][0];
                int sCol = secondPlayerMoves[next][1];
                Move secondPlayerMove = new Move(new Cell(sRow, sCol), second);
                gameEngine.move(board, secondPlayerMove);
            }
            next++;
        }
    }
}
