package com.example.demo;

public class BrownPlayer extends Player {
    BrownPlayer(int color, String game) {
        super(color, game);
    }

    BrownPlayer(int eaten, int color, String gameID) {
        super(eaten, color, gameID);
    }

    @Override
    protected int delta(int f, int t) {
        return t - f;
    }

    @Override
    protected int delta(int t) {
        return t + 1;
    }

    @Override
    protected String eatenName() {
        return "browneaten";
    }
}
