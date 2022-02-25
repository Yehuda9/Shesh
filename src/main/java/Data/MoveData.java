package Data;

import java.io.Serializable;

public class MoveData implements Serializable {
    private String from;
    private int to;
    private String playerID;
    private boolean isEat;

    public MoveData(String from, int to, String playerID) {
        this.from = from;
        this.to = to;
        this.playerID = playerID;
    }

    MoveData() {
        this(null, 0, null);
    }

    public void setEat(boolean eat) {
        isEat = eat;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean isEat() {
        return isEat;
    }

    public String getPlayerID() {
        return playerID;
    }

    public int getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "MoveData{" + "from='" + from + '\'' + ", to=" + to + ", player=" + playerID + '}';
    }
}
