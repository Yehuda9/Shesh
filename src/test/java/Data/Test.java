package Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.objectweb.asm.TypeReference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

public class Test {
    String brownPlayerID;
    String whitePlayerID;
    String gameID;
    ObjectMapper mapper = new ObjectMapper();

    void init() throws IOException {
        connect2Users();
        assertEquals(Integer.parseInt(String.valueOf(brownPlayerID.charAt(brownPlayerID.length() - 1))), Triangle.BROWN);
        assertEquals(Integer.parseInt(String.valueOf(whitePlayerID.charAt(whitePlayerID.length() - 1))), Triangle.WHITE);
        /*whitePlayerID = whitePlayerID.substring(0, brownPlayerID.length() - 1);
        brownPlayerID = brownPlayerID.substring(0, brownPlayerID.length() - 1);*/
        assertEquals(brownPlayerID.substring(0, brownPlayerID.length() - 1), whitePlayerID.substring(0,
                whitePlayerID.length() - 1));
        gameID = brownPlayerID.substring(0, brownPlayerID.length() - 1);
    }


    void getEatenBrownCoins() {
    }

    void getEatenWhiteCoins() {
    }

    void getTriangle() throws IOException {
        List<Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Triangle triangle = getTriangle(i);
            triangles.add(triangle);
        }
        assertEquals(triangles.get(0), new Triangle(Triangle.BROWN, 2, Triangle.RED, 0));
        assertEquals(triangles.get(1), new Triangle(-1, 0, Triangle.BLACK, 1));
        assertEquals(triangles.get(2), new Triangle(-1, 0, Triangle.RED, 2));
        assertEquals(triangles.get(3), new Triangle(-1, 0, Triangle.BLACK, 3));
        assertEquals(triangles.get(4), new Triangle(-1, 0, Triangle.RED, 4));
        assertEquals(triangles.get(5), new Triangle(Triangle.WHITE, 5, Triangle.BLACK, 5));
        assertEquals(triangles.get(6), new Triangle(-1, 0, Triangle.RED, 6));
        assertEquals(triangles.get(7), new Triangle(Triangle.WHITE, 3, Triangle.BLACK, 7));
        assertEquals(triangles.get(8), new Triangle(-1, 0, Triangle.RED, 8));
        assertEquals(triangles.get(9), new Triangle(-1, 0, Triangle.BLACK, 9));
        assertEquals(triangles.get(10), new Triangle(-1, 0, Triangle.RED, 10));
        assertEquals(triangles.get(11), new Triangle(Triangle.BROWN, 5, Triangle.BLACK, 11));

        assertEquals(triangles.get(12), new Triangle(Triangle.WHITE, 5, Triangle.RED, 12));
        assertEquals(triangles.get(13), new Triangle(-1, 0, Triangle.BLACK, 13));
        assertEquals(triangles.get(14), new Triangle(-1, 0, Triangle.RED, 14));
        assertEquals(triangles.get(15), new Triangle(-1, 0, Triangle.BLACK, 15));
        assertEquals(triangles.get(16), new Triangle(Triangle.BROWN, 3, Triangle.RED, 16));
        assertEquals(triangles.get(17), new Triangle(-1, 0, Triangle.BLACK, 17));
        assertEquals(triangles.get(18), new Triangle(Triangle.BROWN, 5, Triangle.RED, 18));
        assertEquals(triangles.get(19), new Triangle(-1, 0, Triangle.BLACK, 19));
        assertEquals(triangles.get(20), new Triangle(-1, 0, Triangle.RED, 20));
        assertEquals(triangles.get(21), new Triangle(-1, 0, Triangle.BLACK, 21));
        assertEquals(triangles.get(22), new Triangle(-1, 0, Triangle.RED, 22));
        assertEquals(triangles.get(23), new Triangle(Triangle.WHITE, 2, Triangle.BLACK, 23));
    }

    void getCurrentDice() {
    }

    void getCurrentPlayer() {
    }

    void isValidMove() throws IOException {

    }

    void makeMove() throws IOException {
        int[] dice = getDice();
        Triangle triangle1 = getTriangle(dice[0]);
        get(new URL("http://localhost:8081/makeMove?from=" + 0 + "&to=" + dice[0] + "&playerID=" + this.brownPlayerID));
        Triangle triangle2 = getTriangle(dice[0]);
        assertNotEquals(triangle1, triangle2);
        assertEquals(triangle2.getColorOfCoins(), triangle1.getColorOfCoins());
        assertEquals(triangle2.getNumOfCoins(), triangle1.getNumOfCoins() + 1);
    }


    public void availableMoves() throws IOException {
        String str = get(new URL("http://localhost:8081" +
                "/availableMoves" + "?gameID=" + gameID));
        Map<String, List<MoveData>> moveDataMap = jsonToMap(str);
        for (Map.Entry<String, List<MoveData>> s : moveDataMap.entrySet()) {
            System.out.println(s.getKey() + ": ");
            for (MoveData moveData : s.getValue()) {
                System.out.println(moveData);
            }
        }
    }

    private Map<String, List<MoveData>> jsonToMap(String json) throws JsonProcessingException {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<MoveData>>>() {}.getType();
        // Use fromJson method to deserialize json into an ArrayList of Map
        return gson.fromJson(json, type);
        /*JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, MoveData.class);
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class,
                LinkedHashMap.class);
        Map<String, LinkedHashMap<String,MoveData>> moveDataMap1 = mapper.readValue(json, mapType);
        Map<String, List<MoveData>> moveDataMap2 = new HashMap<>();
        for (Map.Entry<String, LinkedHashMap<String, MoveData>> entry : moveDataMap1.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                System.out.println("");
            }
        }
        return null;*/
    }

    private String get(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String s = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(s);
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

    private Triangle getTriangle(int i) throws IOException {
        return mapper.readValue(new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + gameID),
                Triangle.class);
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.init();
        test.getTriangle();
        //test.makeMove();
        test.availableMoves();
    }
}
