package Data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.junit.Assert.*;
public class Test {
    List<String> playersID = new ArrayList<>();

    void init(int n) throws IOException {
        Application.main(new String[]{});
        connectNUsers(n);
    }

    private String get(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String s = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return s;
    }

    private void connectNUsers(int n) throws IOException {
        String id;
        for (int i = 0; i < n; i++) {
            id = get(new URL("http://localhost:8081/init"));
            //System.out.println(id);
            playersID.add(id);
        }
    }

    void getEatenBrownCoins() {
    }

    void getEatenWhiteCoins() {
    }

    void getTriangle() throws IOException {
        List<Triangle> triangles = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < 24; i++) {
            URL url = new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + playersID.get(0));
            String s = get(url);
            Triangle triangle = mapper.readValue(s, Triangle.class);
            //System.out.println(triangle);
            triangles.add(triangle);
        }
        assertEquals (triangles.get(0),new Triangle(Triangle.BROWN, 2, Triangle.RED));
        assertEquals (triangles.get(1),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(2),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(3),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(4),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(5),new Triangle(Triangle.WHITE, 5, Triangle.BLACK));
        assertEquals (triangles.get(6),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(7),new Triangle(Triangle.WHITE, 3, Triangle.BLACK));
        assertEquals (triangles.get(8),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(9),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(10),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(11),new Triangle(Triangle.BROWN, 5, Triangle.BLACK));

        assertEquals (triangles.get(12),new Triangle(Triangle.WHITE, 5, Triangle.RED));
        assertEquals (triangles.get(13),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(14),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(15),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(16),new Triangle(Triangle.BROWN, 3, Triangle.RED));
        assertEquals (triangles.get(17),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(18),new Triangle(Triangle.BROWN, 5, Triangle.RED));
        assertEquals (triangles.get(19),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(20),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(21),new Triangle(-1, 0, Triangle.BLACK));
        assertEquals (triangles.get(22),new Triangle(-1, 0, Triangle.RED));
        assertEquals (triangles.get(23),new Triangle(Triangle.WHITE, 2, Triangle.BLACK));
    }

    void getCurrentDice() {
    }

    void getCurrentPlayer() {
    }

    void isValidMove() {
    }

    void makeMove() {
    }

    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.init(Integer.parseInt(args[0]));
        test.getTriangle();
    }
}
