package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.GameInfo;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class ForkPlacement implements Placement{

    private static ForkPlacement forkPlacement;

    private ForkPlacement() {
    }

    public synchronized static ForkPlacement get(){
        forkPlacement = (ForkPlacement) Utils.getIfNull(forkPlacement, () -> new ForkPlacement());
        return forkPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        Cell best = null;
        GameInfo gameInfo = ruleEngine.getInfo(board);
        if (gameInfo.hasAFork()){
            best = gameInfo.getForkCell();
        }
        return Optional.ofNullable(best);
    }

    @Override
    public Placement next() {
        return CenterPlacement.get();
    }
}
