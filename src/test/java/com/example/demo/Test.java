package com.example.demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println(id);
            playersID.add(id);
        }
    }

    void getEatenBrownCoins() {
    }

    void getEatenWhiteCoins() {
    }

    void getTriangle() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < 24; i++) {
            URL url = new URL("http://localhost:8081/triangle?index=" + i + "&gameID=" + playersID.get(0));
            String s = get(url);
            //mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
            Triangle triangle = mapper.readValue(s, Triangle.class);
            System.out.println(s);

        }
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
