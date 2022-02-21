package com.example.demo;

import java.io.Serializable;

abstract public class Player implements Serializable {
    protected int eaten;
    protected int color;
    protected boolean canPlay;
    //protected Game game;
    protected String gameID;

    Player(int eaten, int color,String gameID) {
        this.eaten = eaten;
        this.color = color;
        this.gameID =gameID;
    }

    Player(int color, String game) {
        this.gameID = game;
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

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void setColor(int color) {
        this.color = color;
    }



    public void setEaten(int eaten) {
        this.eaten = eaten;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }


    public String getGameID() {
        return gameID;
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
