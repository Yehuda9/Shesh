package Services;


public class BrownPlayerService extends PlayerService {

    @Override
    public int delta(int f, int t) {
        return t-f;
    }

    @Override
    public int addOrSubtractLoc(int loc, int delta) {
        return loc+delta;
    }

    @Override
    public int delta(int t) {
        return t+1;
    }

    @Override
    public String eatenName() {
        return "browneaten";
    }
}
