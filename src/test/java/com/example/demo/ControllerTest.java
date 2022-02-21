package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {Controller.class})
@WebMvcTest
class ControllerTest {
    Controller controller = new Controller();
    List<String> playersID = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Test
    void init() throws IOException {
        Application.main(new String[]{});
        connectNUsers(2);
    }

    private String get(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void connectNUsers(int n) throws IOException {
        String id;
        for (int i = 0; i < n; i++) {
            id = get(new URL("http://localhost:8081/init"));
            System.out.println(id);
            playersID.add(id);
        }
    }

    @Test
    void getEatenBrownCoins() {
    }

    @Test
    void getEatenWhiteCoins() {
    }

    @Test
    void getTriangle() throws IOException {
        URL url = new URL("http://localhost:8081/triangle?index=0" + "&gameID=" + playersID.get(0));
        System.out.println(get(url));

    }

    @Test
    void getCurrentDice() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void isValidMove() {
    }

    @Test
    void makeMove() {
    }
}