package Data;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

public class Test {
    private String brownPlayerID;
    private String whitePlayerID;
    private String gameID;
    private final ObjectMapper mapper = new ObjectMapper();

    void init() throws IOException {
        connect2Users();
        assertEquals(Integer.parseInt(String.valueOf(brownPlayerID.charAt(brownPlayerID.length() - 1))), 0);
        assertEquals(Integer.parseInt(String.valueOf(whitePlayerID.charAt(whitePlayerID.length() - 1))), 1);
        assertEquals(brownPlayerID.substring(0, brownPlayerID.length() - 1), whitePlayerID.substring(0,
                whitePlayerID.length() - 1));
        gameID = brownPlayerID.substring(0, brownPlayerID.length() - 1);
    }


    void getEatenBrownCoins() {
    }

    void getEatenWhiteCoins() {
    }

    void getTriangle() throws IOException {
        assertEquals(getTriangle(0), "{\"colorOfCoins\":0,\"numOfCoins\":2,\"colorOfPolygon\":2,\"loc\":0}");
        assertEquals(getTriangle(1), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":1}");
        assertEquals(getTriangle(2), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":2}");
        assertEquals(getTriangle(3), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":3}");
        assertEquals(getTriangle(4), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":4}");
        assertEquals(getTriangle(5), "{\"colorOfCoins\":1,\"numOfCoins\":5,\"colorOfPolygon\":3,\"loc\":5}");
        assertEquals(getTriangle(6), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":6}");
        assertEquals(getTriangle(7), "{\"colorOfCoins\":1,\"numOfCoins\":3,\"colorOfPolygon\":3,\"loc\":7}");
        assertEquals(getTriangle(8), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":8}");
        assertEquals(getTriangle(9), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":9}");
        assertEquals(getTriangle(10), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":10}");
        assertEquals(getTriangle(11), "{\"colorOfCoins\":0,\"numOfCoins\":5,\"colorOfPolygon\":3,\"loc\":11}");

        assertEquals(getTriangle(12), "{\"colorOfCoins\":1,\"numOfCoins\":5,\"colorOfPolygon\":2,\"loc\":12}");
        assertEquals(getTriangle(13), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":13}");
        assertEquals(getTriangle(14), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":14}");
        assertEquals(getTriangle(15), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":15}");
        assertEquals(getTriangle(16), "{\"colorOfCoins\":0,\"numOfCoins\":3,\"colorOfPolygon\":2,\"loc\":16}");
        assertEquals(getTriangle(17), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":17}");
        assertEquals(getTriangle(18), "{\"colorOfCoins\":0,\"numOfCoins\":5,\"colorOfPolygon\":2,\"loc\":18}");
        assertEquals(getTriangle(19), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":19}");
        assertEquals(getTriangle(20), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":20}");
        assertEquals(getTriangle(21), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":3,\"loc\":21}");
        assertEquals(getTriangle(22), "{\"colorOfCoins\":-1,\"numOfCoins\":0,\"colorOfPolygon\":2,\"loc\":22}");
        assertEquals(getTriangle(23), "{\"colorOfCoins\":1,\"numOfCoins\":2,\"colorOfPolygon\":3,\"loc\":23}");
    }

    void getCurrentDice() {
    }

    void getCurrentPlayer() {
    }

    void isValidMove() throws IOException {

    }

    void makeMove() throws IOException {
        int[] dice = getDice();
        Triangle triangle1 = mapper.readValue(getTriangle(dice[0]), Triangle.class);
        get(new URL("http://localhost:8081/makeMove?from=" + 0 + "&to=" + dice[0] + "&playerID=" + this.brownPlayerID));
        Triangle triangle2 = mapper.readValue(getTriangle(dice[0]), Triangle.class);
        assertNotEquals(triangle1, triangle2);
        assertEquals(triangle2.getColorOfCoins(), 0);
        assertEquals(triangle2.getNumOfCoins(), triangle1.getNumOfCoins() + 1);

        triangle1 = mapper.readValue(getTriangle(dice[1]), Triangle.class);
        get(new URL("http://localhost:8081/makeMove?from=" + 0 + "&to=" + dice[1] + "&playerID=" + this.brownPlayerID));
        triangle2 = mapper.readValue(getTriangle(dice[1]), Triangle.class);
        assertNotEquals(triangle1, triangle2);
        assertEquals(triangle2.getColorOfCoins(), 0);
        assertEquals(triangle2.getNumOfCoins(), triangle1.getNumOfCoins() + 1);
    }


    public void availableMoves() throws IOException {
        System.out.println(Arrays.toString(getDice()));
        String str = get(new URL("http://localhost:8081/availableMoves?gameID=" + gameID));
        Map<String, List<MoveData>> moveDataMap = jsonToMap(str);
        for (Map.Entry<String, List<MoveData>> s : moveDataMap.entrySet()) {
            System.out.println(s.getKey() + ": ");
            for (MoveData moveData : s.getValue()) {
                System.out.println(moveData);
            }
        }
    }

    private Map<String, List<MoveData>> jsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<MoveData>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private String get(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String s = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        //System.out.println(s);
        return s;
    }

    private void connect2Users() throws IOException {
        brownPlayerID = get(new URL("http://localhost:8081/init"));
        whitePlayerID = get(new URL("http://localhost:8081/init"));
    }

    private int[] getDice() throws IOException {
        String s1 = get(new URL("http://localhost:8081/pour?gameID=" + this.gameID));
        return mapper.readValue(s1, int[].class);
    }

    private String getTriangle(int i) throws IOException {
        return get(new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + gameID));
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.init();
        test.getTriangle();
        test.availableMoves();
        test.makeMove();
        test.availableMoves();
        test.makeMove();
        test.availableMoves();
    }
}
