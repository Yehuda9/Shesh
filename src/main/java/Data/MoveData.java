package Data;

import java.io.Serializable;

public class MoveData implements Serializable {
    private String from;
    private int to;
    private Player player;
    private boolean isEat;

    public MoveData(String from, int to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean isEat() {
        return isEat;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "MoveData{" + "from='" + from + '\'' + ", to=" + to + ", player=" + player + '}';
    }
}
