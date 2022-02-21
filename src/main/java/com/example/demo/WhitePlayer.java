package com.example.demo;

public class WhitePlayer extends Player {

    WhitePlayer(int color, String game) {
        super(color, game);
    }
    WhitePlayer(int eaten, int color, String gameID) {
        super(eaten, color, gameID);
    }
    @Override
    protected int delta(int f, int t) {
        return -(t - f);
    }

    @Override
    protected int delta(int t) {
        return 24 - t;
    }

    @Override
    protected String eatenName() {
        return "whiteeaten";
    }

}
