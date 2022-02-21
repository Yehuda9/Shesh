package com.example.demo;

import java.util.*;

public class Game {
    public BrownPlayer brownPlayer;
    public WhitePlayer whitePlayer;
    private final Random random = new Random();
    private final Triangle[] triangles = new Triangle[24];
    private final Dice currentDice = new Dice();
    private Player currentPlayerTurn;
    private final String gameID;

    Game(String id) {
        this.gameID = id;
        brownPlayer = null;
        whitePlayer = null;
    }

    public void setBrownPlayer() {
        this.brownPlayer = new BrownPlayer(Triangle.BROWN, currentDice, this);
        if (whitePlayer != null) {
            initializeTriangles();
        }
    }

    public String getGameID() {
        return gameID;
    }

    public void setWhitePlayer() {
        this.whitePlayer = new WhitePlayer(Triangle.WHITE, currentDice, this);
        if (brownPlayer != null) {
            initializeTriangles();
        }
    }

    private void initializeTriangles() {
        final int delta = 800 / 12;
        int x = delta / 2;
        for (int i = 0; i < 12; i++) {
            this.triangles[i] = new Triangle(i % 2 == 0 ? Triangle.RED : Triangle.BLACK, getInitColorOfCoins(i));
            x += delta;
        }
        x -= delta;
        for (int i = 12; i < 24; i++) {
            this.triangles[i] = new Triangle(i % 2 == 0 ? Triangle.RED : Triangle.BLACK, getInitColorOfCoins(i));
            x -= delta;
        }
        this.triangles[0].setNumOfCoins(2);
        this.triangles[5].setNumOfCoins(5);
        this.triangles[7].setNumOfCoins(3);
        this.triangles[11].setNumOfCoins(5);
        this.triangles[12].setNumOfCoins(5);
        this.triangles[16].setNumOfCoins(3);
        this.triangles[18].setNumOfCoins(5);
        this.triangles[23].setNumOfCoins(2);

    }

    private Player getInitColorOfCoins(int i) {
        return switch (i) {
            case 0, 11, 16, 18 -> this.brownPlayer;
            case 5, 7, 12, 23 -> this.whitePlayer;
            default -> null;
        };
    }

    public BrownPlayer getBrownPlayer() {
        return brownPlayer;
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public Triangle[] getTriangles() {
        return triangles;
    }

    public Dice getCurrentDice() {
        return currentDice;
    }

    public Player getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }


    public boolean isValidMove(MoveData moveData) {
        String from = moveData.getFrom();
        int to = moveData.getTo();
        Player player = moveData.getPlayer();
        if (player != currentPlayerTurn) {
            return false;
        }
        try {
            return isValidMove(Integer.parseInt(from), to);
        } catch (Exception e) {
            return isValidMoveForComeBack(from, to);
        }
    }

    private boolean isValidMoveForComeBack(String from, int to) {
        if (currentPlayerTurn.getEaten() > 0 &&
                from.equals(currentPlayerTurn.eatenName()) &&
                (triangles[to].getColorOfCoins() == currentPlayerTurn || triangles[to].getNumOfCoins() <= 1)) {
            return true;
        }
        return false;
    }

    private boolean isValidMove(int from, int to) {
        if (currentPlayerTurn == triangles[from].getColorOfCoins()
                && (triangles[from].getNumOfCoins() > 0) && (triangles[to].getNumOfCoins() <= 1 ||
                triangles[to].getColorOfCoins() == currentPlayerTurn)) {
            int d = currentPlayerTurn.delta(from, to);
            return Arrays.stream(currentDice.getCurrentDice()).anyMatch(value -> value == d) || currentDice.isValidDelta(d);
        }
        return false;
    }

    private void makeEat(int to) {
        triangles[to].decreaseCoins();
        if (currentPlayerTurn == brownPlayer) {
            whitePlayer.increaseEaten();
            triangles[to].setColorOfCoins(brownPlayer);
        } else if (currentPlayerTurn == whitePlayer) {
            brownPlayer.increaseEaten();
            triangles[to].setColorOfCoins(whitePlayer);
        }
    }

    public void makeMove(MoveData moveData) {
        if (!isValidMove(moveData)) {
            return;
        }
        String from = moveData.getFrom();
        int to = moveData.getTo();
        if (triangles[to].getNumOfCoins() == 1 && triangles[to].getColorOfCoins() != currentPlayerTurn) {
            makeEat(to);
        }
        try {
            int f = Integer.parseInt(from);
            triangles[to].setColorOfCoins(triangles[f].getColorOfCoins());
            triangles[f].decreaseCoins();
            triangles[to].increaseCoins();
            moveDone(currentPlayerTurn.delta(f, to));
        } catch (Exception e) {
            triangles[to].setColorOfCoins(currentPlayerTurn);
            triangles[to].increaseCoins();
            currentPlayerTurn.decreaseEaten();
            moveDone(currentPlayerTurn.delta(to));
        }
    }

    private void moveDone(int delta) {
        int i = 0;
        int sum = 0;
        while (i < currentDice.getCurrentDice().length) {
            sum += currentDice.getCurrentDice()[i];
            if (delta == currentDice.getCurrentDice()[i]) {
                currentDice.getCurrentDice()[i] = 0;
                if (Arrays.stream(currentDice.getCurrentDice()).allMatch(value -> value == 0)) {
                    turnDone();
                    return;
                }
                return;
            }
            if (delta == sum) {
                for (int j = 0; j <= i; j++) {
                    currentDice.getCurrentDice()[j] = 0;
                }
                if (Arrays.stream(currentDice.getCurrentDice()).allMatch(value -> value == 0)) {
                    turnDone();
                    return;
                }
                return;
            }
            i++;
        }
    }

    public void turnDone() {
        currentPlayerTurn = currentPlayerTurn == brownPlayer ? whitePlayer : brownPlayer;
        currentDice.setCurrentDice();
    }


    protected List<MoveData> availableMoves() {
        List<MoveData> moveDataList = new ArrayList<>();
        OptionalInt m = Arrays.stream(currentDice.getCurrentDice()).max();
        int maxDice = 0;
        if (m.isPresent()) {
            maxDice = m.getAsInt();
        } else {
            return null;
        }
        try {
            for (int j = 0; j < 24; j++) {
                for (int i = j + 1; i < maxDice; i++) {
                    MoveData moveData = new MoveData(Integer.toString(j), i, currentPlayerTurn);
                    if (isValidMove(moveData)) {
                        moveDataList.add(moveData);
                    }
                }
            }

        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Game game = (Game) o;
        return Objects.equals(gameID, game.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }
}
