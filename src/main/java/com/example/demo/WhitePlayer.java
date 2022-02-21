package com.example.demo;

public class WhitePlayer extends Player {

    WhitePlayer(int color, Dice dice, Game game) {
        super(color, dice, game);
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
