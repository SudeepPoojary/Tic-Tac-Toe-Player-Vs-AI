import api.*;
import boards.Board;
import commands.EmailCommand;
import commands.SMSCommand;
import events.*;
import game.*;
import services.EmailService;
import services.SMSService;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine gameEngine = new GameEngine();
        RuleEngine ruleEngine = new RuleEngine();
        EmailService emailService = new EmailService();
        SMSService smsService = new SMSService();
        AIEngine aiEngine = new AIEngine();
        Board board = gameEngine.start("TicTacToe");
        EventBus eventBus = new EventBus();
        eventBus.subscribe(new Subscriber((event -> emailService.send(new EmailCommand(event)))));
        eventBus.subscribe(new Subscriber((event -> smsService.send(new SMSCommand(event)))));
        // make moves in a loop
        int row, col;
        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.createGame();
        Player computer = new Player("O"), human = new Player("X");
        if (human.getUser().activeAfter(10, TimeUnit.DAYS)) {
            eventBus.publish(new Event(human.getUser(), "Welcome back", "https://google.com", "ACTIVITY"));
        }

        //Make moves
        while (!ruleEngine.getState(board).isOver()) {
            System.out.println("Make your move");
            System.out.println(board);
            row = scanner.nextInt();
            col = scanner.nextInt();
            Move opponentMove = new Move(new Cell(row, col), human);
            gameEngine.move(board, opponentMove);
            System.out.println(board);
            if (!ruleEngine.getState(board).isOver()) {
                Move computerMove = aiEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
            }
        }

        if (ruleEngine.getState(board).getWinner().equals(human.symbol())) {
            eventBus.publish(new Event(human.getUser(), "Congratulations!!", "https://google.com", "WIN"));
        }
        System.out.println("Game Result : " + ruleEngine.getState(board));
        System.out.println(board);
    }
}
