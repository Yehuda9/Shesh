package com.example.demo;

abstract public class Player {
    protected int eaten;
    protected int color;
    protected Dice dice;
    protected boolean canPlay;
    protected Game game;

    Player(int color, Dice dice, Game game) {
        this.dice = dice;
        this.game = game;
        canPlay = false;
        if (color == Triangle.BROWN || color == Triangle.WHITE) {
            this.color = color;
        } else {
            throw new RuntimeException("error");
        }
    }

    public int getEaten() {
        return eaten;
    }

    public int getColor() {
        return color;
    }

    public void decreaseEaten() {
        eaten--;
    }

    public void increaseEaten() {
        eaten++;
    }

    abstract protected int delta(int f, int t);

    abstract protected int delta(int t);

    protected abstract String eatenName();
}
