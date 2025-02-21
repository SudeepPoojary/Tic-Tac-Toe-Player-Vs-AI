package game;

import boards.Board;

public class Game {
    private GameConfig gameConfig;
    private Board board;
    Player winner;
    private int lastMoveTimeInMillis;
    private int maxTimePerPlayer;
    private int maxTimePerMove;

    public void move(Move move, int timeStampInMillis) {
        int timeTakenSinceLastMove = timeStampInMillis - lastMoveTimeInMillis;
        move.getPlayer().setTimeTaken(timeStampInMillis);
        if(gameConfig.timed) {
            moveForTimedGame(move, timeTakenSinceLastMove);
        } else {
            board.move(move);
        }
    }

    private void moveForTimedGame(Move move, int timeTakenSinceLastMove) {
        int currentTime = gameConfig.timePerMove != null ? timeTakenSinceLastMove : move.getPlayer().getTimeUsedInMillis();
        int endTime = gameConfig.timePerMove != null ? maxTimePerMove : maxTimePerPlayer;
        if (currentTime < endTime) {
            board.move(move);
        } else {
            winner = move.getPlayer().flip();
        }
    }

}
