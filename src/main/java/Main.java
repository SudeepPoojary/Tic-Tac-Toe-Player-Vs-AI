import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import boards.Board;
import game.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        RuleEngine ruleEngine = new RuleEngine();
        AIEngine aiEngine = new AIEngine();
        Board board = gameEngine.start("TicTacToe");
        int row, col;
        Scanner scanner = new Scanner(System.in);
        //Make moves
        while(!ruleEngine.getState(board).isOver()){
            Player computer = new Player("O"), human = new Player("X");
            System.out.println("Make your move");
            System.out.println(board);
            row = scanner.nextInt();
            col = scanner.nextInt();
            Move opponentMove = new Move(new Cell(row, col), human);
            gameEngine.move(board, opponentMove);
            if(!ruleEngine.getState(board).isOver()){
                Move computerMove = aiEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
            }
        }

        System.out.println("Game Result : " + ruleEngine.getState(board));
        System.out.println(board);
    }
}
