package game;

import java.util.concurrent.TimeUnit;

public class Player {

    private User user;
    private  int timeUsedInMillis;
    private String playerSymbol;

    public Player(String playerSymbol) {
        this.user = new User();
        this.playerSymbol = playerSymbol;
    }

    public String symbol(){
        return playerSymbol;
    }

    public Player flip() {
        return new Player(playerSymbol.equals("X") ? "O" : "X");
    }

    public void setTimeTaken(int timeInMillis) {
        timeUsedInMillis += timeInMillis;
    }

    public int getTimeUsedInMillis() {
        return timeUsedInMillis;
    }

    public User getUser() {
        return user;
    }




}
