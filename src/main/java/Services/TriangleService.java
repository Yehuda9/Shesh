package Services;

import Data.Triangle;

public class TriangleService {
    public void decreaseCoins(Triangle triangle) {
        triangle.setNumOfCoins(triangle.getNumOfCoins()-1);
        if (triangle.getNumOfCoins() <= 0) {
            triangle.setColorOfCoins(-1);
        }
    }

    public void increaseCoins(Triangle triangle) {
        triangle.setNumOfCoins(triangle.getNumOfCoins()+1);
    }
}
