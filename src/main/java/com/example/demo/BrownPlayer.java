package com.example.demo;

public class BrownPlayer extends Player {
    BrownPlayer(int color, Dice dice, Game game) {
        super(color, dice, game);
    }

    @Override
    protected int delta(int f, int t) {
        return t-f;
    }

    @Override
    protected int delta(int t) {
        return t+1;
    }

    @Override
    protected String eatenName() {
        return "browneaten";
    }
}
