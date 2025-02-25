import game.Game;
import game.GameFactory;
import org.junit.Test;

public class GameTest {

    GameFactory gameFactory = new GameFactory();
    // todo: Adding tests for Game class
    @Test
    public void timeOutTest() {
        Game game = gameFactory.createGame(3, 120);
        
    }

    @Test
    public void timeOutTestPerPlayer() {
        int secondsElapsed = 0;
        Game game = gameFactory.createGame(null, 120);

    }
}
