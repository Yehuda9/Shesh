package Services;


public class WhitePlayerService extends PlayerService {

    @Override
    public int delta(int f, int t) {
        return -(t-f);
    }

    @Override
    public int addOrSubtractLoc(int loc, int delta) {
        return loc-delta;
    }

    @Override
    public int delta(int t) {
        return 24-t;
    }

    @Override
    public String eatenName() {
        return "whiteeaten";
    }
}

