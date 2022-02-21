package Data;

public class MoveData {
    private String from;
    private int to;
    private Player player;
    private boolean isEat;
    public MoveData(String from, int to, Player player) {
        this.from = from;
        this.to = to;
        this.player = player;
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
