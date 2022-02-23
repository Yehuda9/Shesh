package Data;

import Services.BrownPlayerService;
import Services.PlayerService;
import Services.TriangleService;
import Services.WhitePlayerService;

import java.util.*;

public class Game {
    public Player brownPlayer;
    public Player whitePlayer;
    private final Triangle[] triangles = new Triangle[24];
    private final Dice currentDice = new Dice();
    private Player currentPlayerTurn;
    private final String gameID;
    TriangleService triangleService;
    PlayerService whitePlayerService;
    PlayerService brownPlayerService;
    PlayerService playerService;


    Game(String id) {
        this.gameID = id;
        brownPlayer = null;
        whitePlayer = null;
        this.triangleService = new TriangleService();
        this.whitePlayerService = new WhitePlayerService();
        this.brownPlayerService = new BrownPlayerService();
    }

    public void setBrownPlayer() {
        this.brownPlayer = new Player(0, Triangle.BROWN, this.gameID);
        if (whitePlayer != null) {
            initializeTriangles();
            return;
        }
        this.currentPlayerTurn = brownPlayer;
        this.playerService = brownPlayerService;
    }

    public String getGameID() {
        return gameID;
    }

    public void setWhitePlayer() {
        this.whitePlayer = new Player(0, Triangle.WHITE, this.gameID);
        if (brownPlayer != null) {
            initializeTriangles();
            return;
        }
        this.currentPlayerTurn = whitePlayer;
        this.playerService = whitePlayerService;
    }

    private void initializeTriangles() {
        final int delta = 800 / 12;
        for (int i = 0; i < 12; i++) {
            this.triangles[i] = new Triangle(getInitColorOfCoins(i), 0, i % 2 == 0 ? Triangle.RED : Triangle.BLACK, i);
        }
        for (int i = 12; i < 24; i++) {
            this.triangles[i] = new Triangle(getInitColorOfCoins(i), 0, i % 2 == 0 ? Triangle.RED : Triangle.BLACK, i);
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

    private int getInitColorOfCoins(int i) {
        return switch (i) {
            case 0, 11, 16, 18 -> Triangle.BROWN;
            case 5, 7, 12, 23 -> Triangle.WHITE;
            default -> -1;
        };
    }

    public Player getBrownPlayer() {
        return brownPlayer;
    }

    public Player getWhitePlayer() {
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
        if (!player.equals(currentPlayerTurn)) {
            return false;
        }
        try {
            return isValidMove(Integer.parseInt(from), to);
        } catch (NumberFormatException e) {
            return isValidMoveForComeBack(from, to);
        }
    }

    private boolean isValidMoveForComeBack(String from, int to) {
        if (currentPlayerTurn.getEaten() > 0 &&
                from.equals(playerService.eatenName()) &&
                (triangles[to].getColorOfCoins() == currentPlayerTurn.getColor() || triangles[to].getNumOfCoins() <= 1)) {
            return true;
        }
        return false;
    }

    private boolean isValidMove(int from, int to) {
        if (from != to && from < 24 && from > -1 && to < 24 && to > -1 && currentPlayerTurn.getColor() == triangles[from].getColorOfCoins()
                && (triangles[from].getNumOfCoins() > 0) && (triangles[to].getNumOfCoins() <= 1 ||
                triangles[to].getColorOfCoins() == currentPlayerTurn.getColor())) {
            int d = playerService.delta(from, to);

            return Arrays.stream(currentDice.getCurrentDice()).anyMatch(value -> value == d) || currentDice.isValidDelta(d);
        }
        return false;
    }

    private void makeEat(int to) {
        triangleService.decreaseCoins(triangles[to]);
        if (currentPlayerTurn == brownPlayer) {
            playerService.increaseEaten(whitePlayer);
            triangles[to].setColorOfCoins(brownPlayer.getColor());
        } else if (currentPlayerTurn == whitePlayer) {
            playerService.increaseEaten(whitePlayer);
            triangles[to].setColorOfCoins(whitePlayer.getColor());
        }
    }

    public void makeMove(MoveData moveData) {
        if (!isValidMove(moveData)) {
            return;
        }
        String from = moveData.getFrom();
        int to = moveData.getTo();
        if (triangles[to].getNumOfCoins() == 1 && triangles[to].getColorOfCoins() != currentPlayerTurn.getColor()) {
            makeEat(to);
        }
        try {
            int f = Integer.parseInt(from);
            triangles[to].setColorOfCoins(triangles[f].getColorOfCoins());
            triangleService.decreaseCoins(triangles[f]);
            triangleService.increaseCoins(triangles[to]);
            moveDone(playerService.delta(f, to));
        } catch (Exception e) {
            triangles[to].setColorOfCoins(currentPlayerTurn.getColor());
            triangleService.increaseCoins(triangles[to]);
            playerService.decreaseEaten(currentPlayerTurn);
            moveDone(playerService.delta(to));
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
        playerService = playerService == brownPlayerService ? whitePlayerService : brownPlayerService;
        currentDice.setCurrentDice();
    }


    public Map<String, Set<MoveData>> availableMoves() {
        Map<String, Set<MoveData>> moveDataMap = new HashMap<>();
        int[] dice = getCurrentDice().getCurrentDice();
        List<Integer> distSums = distSum();
        for (Triangle triangle : triangles) {
            if (triangle.getColorOfCoins() != currentPlayerTurn.color) {
                continue;
            }
            for (int i : distSums/*Arrays.stream(dice).filter(value -> value != 0).toArray()*/) {
                MoveData moveData1 = new MoveData(String.valueOf(triangle.getLoc()), triangle.getLoc() + i,
                        currentPlayerTurn);
                if (isValidMove(moveData1)) {
                    moveDataMap.computeIfAbsent(moveData1.getFrom(), k -> new HashSet<>());
                    moveDataMap.get(moveData1.getFrom()).add(moveData1);
                }
            }
            /*MoveData moveData1 = new MoveData(String.valueOf(triangle.getLoc()), triangle.getLoc() + dice[0]+dice[1],
                    currentPlayerTurn);
            if (isValidMove(moveData1)) {
                moveDataMap.computeIfAbsent(moveData1.getFrom(), k -> new HashSet<>());
                moveDataMap.get(moveData1.getFrom()).add(moveData1);
            }
            moveData1 = new MoveData(String.valueOf(triangle.getLoc()), triangle.getLoc() + dice[1]+dice[2],
                    currentPlayerTurn);
            if (isValidMove(moveData1)) {
                moveDataMap.computeIfAbsent(moveData1.getFrom(), k -> new HashSet<>());
                moveDataMap.get(moveData1.getFrom()).add(moveData1);
            }
            moveData1 = new MoveData(String.valueOf(triangle.getLoc()), triangle.getLoc() + dice[2]+dice[3],
                    currentPlayerTurn);
            if (isValidMove(moveData1)) {
                moveDataMap.computeIfAbsent(moveData1.getFrom(), k -> new HashSet<>());
                moveDataMap.get(moveData1.getFrom()).add(moveData1);
            }*/
        }
        return moveDataMap;
    }

    private List<Integer> distSum() {
        Set<Integer> set = new HashSet<>();
        distSumRec(getCurrentDice().getCurrentDice(), 4, 0, 0, set);
        return set.stream().toList();
    }

    private void distSumRec(int[] arr, int n, int sum,
                            int currindex, Set<Integer> s) {
        if (currindex > n)
            return;

        if (currindex == n) {
            s.add(sum);
            return;
        }

        distSumRec(arr, n, sum + arr[currindex],
                currindex + 1, s);
        distSumRec(arr, n, sum, currindex + 1, s);
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
