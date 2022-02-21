package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class Triangle implements Serializable {
    public static final int BROWN = 0;
    public static final int WHITE = 1;
    public static final int RED = 2;
    public static final int BLACK = 3;
    @JsonProperty("colorOfCoins")
    private Player colorOfCoins;
    @JsonProperty("numOfCoins")
    private int numOfCoins;
    @JsonProperty("colorOfPolygon")
    private int colorOfPolygon;



    Triangle(int colorOfPolygon, Player colorOfCoins) {
        if (colorOfPolygon == RED || colorOfPolygon == BLACK) {
            this.colorOfPolygon = colorOfPolygon;
        } else {
            throw new RuntimeException("error");
        }
        this.colorOfCoins = colorOfCoins;
    }

    public void setNumOfCoins(int numOfCoins) {
        this.numOfCoins = numOfCoins;
    }

    public Player getColorOfCoins() {
        return colorOfCoins;
    }

    public int getNumOfCoins() {
        return numOfCoins;
    }

    public void setColorOfCoins(Player colorOfCoins) {
        this.colorOfCoins = colorOfCoins;
    }

    public void decreaseCoins() {
        this.numOfCoins--;
        if (numOfCoins <= 0) {
            colorOfCoins = null;
        }
    }

    public void increaseCoins() {
        this.numOfCoins++;
    }
}
