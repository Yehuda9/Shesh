package Services;

import Data.Player;

abstract public class PlayerService {
    public void decreaseEaten(Player player) {
        player.setEaten(player.getEaten() - 1);
    }

    public void increaseEaten(Player player) {
        player.setEaten(player.getEaten() + 1);
    }
    abstract public int delta(int f, int t);
    abstract public int addOrSubtractLoc(int loc,int delta);
    abstract public int delta(int t);

    public abstract String eatenName();
}
