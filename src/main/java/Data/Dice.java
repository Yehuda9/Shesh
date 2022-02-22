package Data;

import java.util.Arrays;
import java.util.Random;

public class Dice {
    private int[] currentDice;
    private final Random random = new Random();

    Dice() {
        setCurrentDice();
    }

    void setCurrentDice() {
        this.currentDice = new int[]{Math.abs(random.nextInt() % 6) + 1, Math.abs(random.nextInt() % 6) + 1, 0, 0};
        if (currentDice[0] == currentDice[1]) {
            currentDice[2] = currentDice[1];
            currentDice[3] = currentDice[0];
        }
    }

    public int[] getCurrentDice() {
        return currentDice;
    }

    public boolean isValidDelta(int d) {
        int i = 0;
        int sum = 0;
        while (i < currentDice.length) {
            sum += currentDice[i];
            if (d == sum) {
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "currentDice=" + Arrays.toString(currentDice) +
                '}';
    }
}
