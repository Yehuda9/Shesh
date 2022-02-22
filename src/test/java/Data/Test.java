package Data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        assertEquals(triangles.get(0), new Triangle(Triangle.BROWN, 2, Triangle.RED));
        assertEquals(triangles.get(1), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(2), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(3), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(4), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(5), new Triangle(Triangle.WHITE, 5, Triangle.BLACK));
        assertEquals(triangles.get(6), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(7), new Triangle(Triangle.WHITE, 3, Triangle.BLACK));
        assertEquals(triangles.get(8), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(9), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(10), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(11), new Triangle(Triangle.BROWN, 5, Triangle.BLACK));

        assertEquals(triangles.get(12), new Triangle(Triangle.WHITE, 5, Triangle.RED));
        assertEquals(triangles.get(13), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(14), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(15), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(16), new Triangle(Triangle.BROWN, 3, Triangle.RED));
        assertEquals(triangles.get(17), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(18), new Triangle(Triangle.BROWN, 5, Triangle.RED));
        assertEquals(triangles.get(19), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(20), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(21), new Triangle(-1, 0, Triangle.BLACK));
        assertEquals(triangles.get(22), new Triangle(-1, 0, Triangle.RED));
        assertEquals(triangles.get(23), new Triangle(Triangle.WHITE, 2, Triangle.BLACK));
    }

    void getCurrentDice() {
    }

    void getCurrentPlayer() {
    }

    void isValidMove() throws IOException {
        String s1 = get(new URL("http://localhost:8081/pour?gameID=" + this.playersID.get(0).substring(0,
                this.playersID.get(0).length() - 1)));
        int[] dice = mapper.readValue(s1, int[].class);
        Triangle triangle1 =
                mapper.readValue(new URL("http://localhost:8081/triangle?index=" + dice[0] + "&gameID=" + playersID.get(0)), Triangle.class);
        String s2 =
                get(new URL("http://localhost:8081/makeMove?from=" + 0 + "&to=" + dice[0] + "&gameID=" + this.playersID.get(0)));
        Triangle triangle2 = mapper.readValue(new URL("http://localhost:8081/triangle?index=" + dice[0] + "&gameID=" + playersID.get(0)), Triangle.class);
        assertNotEquals(triangle1,triangle2);
    }

    void makeMove() {

    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.init(Integer.parseInt(args[0]));
        test.getTriangle();
        test.isValidMove();
    }
}
