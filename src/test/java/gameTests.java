import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class gameTests {

    @Test
    public void setNumPlayersTest(){
        Game game= new Game();
        int numPlayers= 4;
        game.setNumPlayers(numPlayers);
        assertEquals(game.numPlayers, numPlayers);
    }

    @Test
    public void setGameOverTestTrue(){
        Game game= new Game();
        boolean gameTrue= true;
        game.setGameOver(gameTrue);
        assertTrue(game.gameOver);
    }

    @Test
    public void setGameOverTestFalse(){
        Game game= new Game();
        boolean gameFalse= false;
        game.setGameOver(gameFalse);
        assertFalse(game.gameOver);
    }

    @Test
    public void setPlayerTurnTest(){
        Game game= new Game();
        int turn= 3;
        game.setPlayerTurn(turn);
        assertEquals(game.playerTurn, turn);
    }

    @Test
    public void getNumPlayersTest(){
        Game game= new Game();
        int num= 3;
        game.numPlayers= num;
        assertEquals(game.getNumPlayers(), num);
    }

    @Test
    public void getGameOverTestTrue(){
        Game game= new Game();
        boolean gameTrue= true;
        game.gameOver= gameTrue;
        assertTrue(game.getGameOver());
    }

    @Test
    public void getGameOverTestFalse(){
        Game game= new Game();
        boolean gameFalse= false;
        game.gameOver= gameFalse;
        assertFalse(game.getGameOver());
    }

    @Test
    public void getTerritoriesTest(){
        Game game= new Game();
        game.createTerritories();
        ArrayList<territory> testTerritories= game.getTerritories();
        assertArrayEquals(game.territories.toArray(), testTerritories.toArray());
    }

    @Test
    public void getPlayersTest(){
        Game game= new Game();

    }

    @Test
    public void getPlayerTurnTest(){
        Game game= new Game();
        int playerTurn= 3;
        game.setPlayerTurn(playerTurn);
        assertEquals(game.getPlayerTurn(), playerTurn);
    }

//    @Test
//    public void nextPlayerTest(){
//        Game game= new Game();
//
//    }

//    @Test
//    public void currentPlayerTest(){
//        Game game= new Game();
//        int currentPlayer= 3;
//        game.setPlayerTurn(currentPlayer);
//        assertEquals(game.currentPlayer(), currentPlayer);
//    }

    @Test
    public void nextStageTest(){
        Game game= new Game();

    }

    @Test
    public void createTerritoryTest(){
        ArrayList<territory> territories = new ArrayList<>();
        int totalTerritories = 42;
        Game game= new Game();
        game.createTerritories();

        assertEquals(game.territories.size(), totalTerritories);
    }
}
