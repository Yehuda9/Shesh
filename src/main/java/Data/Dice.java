package Data;

import java.util.*;

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

    public List<Integer> distSum() {
        Set<Integer> set = new HashSet<>();
        distSumRec(getCurrentDice(), 4, 0, 0, set);
        return set.stream().toList();
    }

    private void distSumRec(int[] arr, int n, int sum,
                            int currindex, Set<Integer> s) {
        if (currindex > n) {
            return;
        }
        if (currindex == n) {
            s.add(sum);
            return;
        }
        distSumRec(arr, n, sum + arr[currindex],
                currindex + 1, s);
        distSumRec(arr, n, sum, currindex + 1, s);
    }

    public boolean isValidDelta(int d) {
        return distSum().contains(d);
        /*int i = 0;
        int sum = 0;
        while (i < currentDice.length) {
            sum += currentDice[i];
            if (d == sum) {
                return true;
            }
            i++;
        }
        return false;*/
    }

    @Override
    public String toString() {
        return "Dice{" +
                "currentDice=" + Arrays.toString(currentDice) +
                '}';
    }
}
