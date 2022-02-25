package Data;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    protected int eaten;
    protected int color;
    protected boolean canPlay;
    protected String gameID;

    Player() {
    }

    Player(String gameID) {
        this(0, Integer.parseInt(String.valueOf(gameID.charAt(gameID.length() - 1))), gameID.substring(0,
                gameID.length() - 1));
    }

    Player(String gameID, int color) {
        this(0, color, gameID);
    }

    Player(int eaten, int color, String gameID) {
        this.eaten = eaten;
        this.color = color;
        this.gameID = gameID;
    }

    public int getEaten() {
        return eaten;
    }

    public int getColor() {
        return color;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public void setEaten(int eaten) {
        this.eaten = eaten;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Player player = (Player) o;
        return color == player.color && Objects.equals(gameID, player.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, gameID);
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayerID() {
        return gameID + color;
    }
}
