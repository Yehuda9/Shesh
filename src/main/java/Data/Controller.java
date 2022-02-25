package Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Controller {
    private final Map<String,Game> gameMap = new HashMap<>();

    @GetMapping("init")
    public String init() {
        String id = generateID();
        if (gameMap.isEmpty()) {
            gameMap.put(id,new Game(id));
        }
        for (Game g : gameMap.values()) {
            if (g.brownPlayer == null) {
                g.setBrownPlayer();
                return g.getGameID() + Triangle.BROWN;
            } else if (g.whitePlayer == null) {
                g.setWhitePlayer();
                return g.getGameID() + Triangle.WHITE;
            }
        }
        Game newGame = new Game(id);
        newGame.setBrownPlayer();
        gameMap.put(id,newGame);
        return newGame.getGameID() + Triangle.BROWN;
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("getEatenBrownCoins")
    public String getEatenBrownCoins(@RequestParam(value = "gameID") String gameID) {
        return String.valueOf(gameMap.get(gameID).getBrownPlayer().getEaten());

    }

    @GetMapping("getEatenWhiteCoins")
    public String getEatenWhiteCoins(@RequestParam(value = "gameID") String gameID) {
        return String.valueOf(gameMap.get(gameID).getWhitePlayer().getEaten());
    }

    @GetMapping("/triangle")
    public String getTriangle(@RequestParam(value = "index") int i, @RequestParam(value = "gameID") String gameID) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json =
                    mapper.writeValueAsString(gameMap.get(gameID).getTriangles()[i]);
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
                    mapper.writeValueAsString(gameMap.get(gameID).getCurrentDice().getCurrentDice());
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
            json = mapper.writeValueAsString(gameMap.get(gameID).getCurrentPlayerTurn());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("isValidMove")
    public boolean isValidMove(@RequestParam(value = "from") String from, @RequestParam(value = "to") int to,
                               @RequestParam(value = "playerID") String playerID) {
        String gameID = playerID.substring(0,playerID.length()-1);
        return gameMap.get(gameID).isValidMove(from, to, new Player(playerID));
    }

    @GetMapping("makeMove")
    public void makeMove(@RequestParam(value = "from") String from, @RequestParam(value = "to") int to,
                         @RequestParam(value = "playerID") String playerID) {
        String gameID = playerID.substring(0,playerID.length()-1);
        gameMap.get(gameID).makeMove(from, to, new Player(playerID));
    }

    @GetMapping("availableMoves")
    public Map<String, Set<MoveData>> availableMoves(@RequestParam(value = "gameID") String gameID) {
        return gameMap.get(gameID).availableMoves();
    }
}
