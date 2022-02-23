package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.objectweb.asm.TypeReference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

public class Test {
    List<String> playersID = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    void init(int n) throws IOException {
        //Application.main(new String[]{});
        connectNUsers(n);
    }

    private String get(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String s = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(s);
        return s;
    }

    private void connectNUsers(int n) throws IOException {
        String id;
        for (int i = 0; i < n; i++) {
            id = get(new URL("http://localhost:8081/init"));
            playersID.add(id);
        }
    }

    void getEatenBrownCoins() {
    }

    void getEatenWhiteCoins() {
    }

    void getTriangle() throws IOException {
        List<Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            URL url = new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + playersID.get(0));
            String s = get(url);
            Triangle triangle = mapper.readValue(s, Triangle.class);
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
        get(new URL("http://localhost:8081/makeMove?from=" + 0 + "&to=" + dice[0] + "&gameID=" + this.playersID.get(0).substring(0, playersID.get(0).length() - 1)));
        Triangle triangle2 = getTriangle(dice[0]);
        assertNotEquals(triangle1, triangle2);
        assertEquals(triangle2.getColorOfCoins(), Integer.parseInt(String.valueOf(playersID.get(0).charAt(playersID.get(0).length() - 1))));
        assertEquals(triangle2.getNumOfCoins(), triangle1.getNumOfCoins() + 1);
    }

    private Triangle getTriangle(int i) throws IOException {
        return mapper.readValue(new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + playersID.get(0)), Triangle.class);
    }

    public void availableMoves() throws IOException {

        /*TypeReference<HashMap<String, List<MoveData>>> typeRef
                = new TypeReference<HashMap<String, List<MoveData>>>() {};
        Map<String, List<MoveData>> moveDataMap1=new HashMap<String, List<MoveData>>();*/
        Map<String, List<MoveData>> moveDataMap = mapper.readValue(
                get(new URL("http://localhost:8081/availableMoves?gameID=" + this.playersID.get(0).substring(0,
                        this.playersID.get(0).length() - 1))), HashMap.class
                /*new TypeReference<HashMap<String,TypeReference<List<TypeReference<MoveData>>>>>() {}*/);
        for (Map.Entry<String, List<MoveData>> s : moveDataMap.entrySet()) {
            System.out.println(s.getKey() + ": ");
            for (MoveData moveData : s.getValue().stream().toList()) {
                System.out.println(moveData);
            }
        }
    }

    private int[] getDice() throws IOException {
        String s1 = get(new URL("http://localhost:8081/pour?gameID=" + this.playersID.get(0).substring(0,
                this.playersID.get(0).length() - 1)));
        return mapper.readValue(s1, int[].class);
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.init(Integer.parseInt(args[0]));
        test.getTriangle();
        //test.makeMove();
        test.availableMoves();
    }
}
