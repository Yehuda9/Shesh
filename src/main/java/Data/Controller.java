package Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {
    private final List<Game> gameList = new ArrayList<>();
    private int numOfGames;

    @GetMapping("init")
    public String init() {
        String id = generateID();
        if (numOfGames == 0) {
            gameList.add(new Game(id));
        }
        for (Game g : gameList) {
            if (g.brownPlayer == null) {
                g.setBrownPlayer();
                return g.getGameID() + Triangle.BROWN;
            } else if (g.whitePlayer == null) {
                g.setWhitePlayer();
                return g.getGameID() + Triangle.WHITE;
            }
        }
        gameList.add(new Game(id));
        return numOfGames + "0";
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("getEatenBrownCoins")
    public String getEatenBrownCoins(@RequestParam(value = "gameID") String gameID) {
        return String.valueOf(gameList.get(gameList.indexOf(new Game(gameID))).getBrownPlayer().getEaten());
    }

    @GetMapping("getEatenWhiteCoins")
    public String getEatenWhiteCoins(@RequestParam(value = "gameID") String gameID) {
        return String.valueOf(gameList.get(gameList.indexOf(new Game(gameID))).getWhitePlayer().getEaten());
    }

    @GetMapping("/triangle")
    public String getTriangle(@RequestParam(value = "index") int i, @RequestParam(value = "gameID") String gameID) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            StringBuilder stringBuffer = new StringBuilder(gameID);
            gameID = stringBuffer.deleteCharAt(gameID.length() - 1).toString();
            json =
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameList.get(gameList.indexOf(new Game(gameID))).getTriangles()[i]);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("pour")
    public String getCurrentDice(@RequestParam(value = "gameID") String gameID) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json =
                    mapper.writeValueAsString(gameList.get(gameList.indexOf(new Game(gameID))).getCurrentDice().getCurrentDice());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("/currentPlayer")
    public String getCurrentPlayer(@RequestParam(value = "gameID") String gameID) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(gameList.get(gameList.indexOf(new Game(gameID))).getCurrentPlayerTurn());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("isValidMove")
    public boolean isValidMove(@RequestParam(value = "from") String from, @RequestParam(value = "to") int to,
                               @RequestParam(value = "player") Player player, @RequestParam(value = "gameID") String gameID) {
        return gameList.get(gameList.indexOf(new Game(gameID))).isValidMove(new MoveData(from, to, player));
    }
    @GetMapping("makeMove")
    public void makeMove(@RequestParam(value = "from") String from, @RequestParam(value = "to") int to,
                         @RequestParam(value = "gameID") String gameID) {
        gameList.get(gameList.indexOf(new Game(gameID))).makeMove(new MoveData(from, to, new Player(gameID)));
    }

}
