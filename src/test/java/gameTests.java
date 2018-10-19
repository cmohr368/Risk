import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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

//    @Test
//    public void getNumPlayersTest(){
//        Game game= new Game();
//    }
}
