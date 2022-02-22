package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Triangle implements Serializable {
    public static final int BROWN = 0;
    public static final int WHITE = 1;
    public static final int RED = 2;
    public static final int BLACK = 3;
    @JsonProperty("colorOfCoins")
    private int colorOfCoins;
    @JsonProperty("numOfCoins")
    private int numOfCoins;
    @JsonProperty("colorOfPolygon")
    private int colorOfPolygon;
    @JsonProperty("loc")
    private int loc;


    Triangle() {
    }

    Triangle(int colorOfCoins, int numOfCoins, int colorOfPolygon, int loc) {
        this.numOfCoins = numOfCoins;
        this.loc = loc;
        if (colorOfPolygon == RED || colorOfPolygon == BLACK) {
            this.colorOfPolygon = colorOfPolygon;
        } else {
            throw new RuntimeException("error");
        }
        this.colorOfCoins = colorOfCoins;
    }

    public void setColorOfPolygon(int colorOfPolygon) {
        this.colorOfPolygon = colorOfPolygon;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public void setNumOfCoins(int numOfCoins) {
        this.numOfCoins = numOfCoins;
    }

    public int getColorOfCoins() {
        return colorOfCoins;
    }

    public int getNumOfCoins() {
        return numOfCoins;
    }

    public void setColorOfCoins(int colorOfCoins) {
        this.colorOfCoins = colorOfCoins;
    }

    public int getColorOfPolygon() {
        return colorOfPolygon;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "colorOfCoins=" + colorOfCoins +
                ", numOfCoins=" + numOfCoins +
                ", colorOfPolygon=" + colorOfPolygon +
                ", loc=" + loc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Triangle triangle = (Triangle) o;
        return colorOfCoins == triangle.colorOfCoins && numOfCoins == triangle.numOfCoins && colorOfPolygon == triangle.colorOfPolygon && loc == triangle.loc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorOfCoins, numOfCoins, colorOfPolygon, loc);
    }
}
