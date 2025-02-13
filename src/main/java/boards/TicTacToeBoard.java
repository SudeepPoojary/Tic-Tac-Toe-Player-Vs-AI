package boards;

import api.Rule;
import api.RuleSet;
import game.Cell;
import game.GameState;
import game.Move;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TicTacToeBoard implements CellBoard {
    String[][] cells = new String[3][3];

    public static RuleSet<TicTacToeBoard> getRules() {
        RuleSet rules = new RuleSet();
        rules.add(new Rule(board -> outerTraversals((i, j) -> board.getSymbol(i, j))));
        rules.add(new Rule(board -> outerTraversals((i, j) -> board.getSymbol(j, i))));
        rules.add(new Rule(board-> traverse(i -> board.getSymbol(i, i))));
        rules.add(new Rule(board-> traverse(i -> board.getSymbol(i, 2 - i))));
        rules.add(new Rule(board-> {
            int countOfFilledCells = 0;
            for(int i=0; i<3; i++){
                for(int j=1; j<3; j++){
                    if(board.getSymbol(i, j) != null){
                        countOfFilledCells++;
                    }
                }
            }
            if(countOfFilledCells == 9){
                return new GameState(true, "-");
            }
            return new GameState(false, "-");
        }));
        return rules;
    }

    public String getSymbol(int row, int col){
        return cells[row][col];
    }

    public void setCell(Cell cell, String symbol) {
        if(cells[cell.getRow()][cell.getCol()] == null){
            cells[cell.getRow()][cell.getCol()] = symbol;
        } else {
            System.out.println(this);
            throw new IllegalArgumentException(cell.getRow() + " " + cell.getCol());
        }

    }

    @Override
    public String toString() {
        String result = "";
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                result += cells[i][j] + "  ";
            }
            result += "\n";
        }
        return result;
    }

    @Override
    public void move(Move move) {
        setCell(move.getCell(), move.getPlayer().symbol());
    }

    @Override
    public TicTacToeBoard copy(){
        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard();
        for(int i=0; i<3; i++) {
            System.arraycopy(cells[i], 0, ticTacToeBoard.cells[i], 0, 3);
        }
        return ticTacToeBoard;
    }

    private static GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
        GameState result = new GameState(false, "-");
        for (int i = 0; i < 3; i++) {
            final int ii = i;
            GameState traversal = traverse(j -> next.apply(ii, j));
            if (traversal.isOver()) {
                result = traversal;
                break;
            }
        }
        return result;
    }

    private static GameState traverse(Function<Integer, String> traversal) {
        GameState result = new GameState(false, "-");
        boolean possibleStreak = true;
        for (int j=0; j<3; j++){
            if (traversal.apply(0) == null || !traversal.apply(0).equals(traversal.apply(j))) {
                possibleStreak = false;
                break;
            }
        }

        if(possibleStreak){
            result =  new GameState(true, traversal.apply(0));
        }
        return result;
    }
}
