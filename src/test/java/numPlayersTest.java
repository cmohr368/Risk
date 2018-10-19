import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class numPlayersTest{

    @Test
    public void numPlayersTest(){
        Game game= new Game();
        int numPlayers= 4;
        game.setNumPlayers(numPlayers);
        assertEquals(game.numPlayers, numPlayers);
    }
}
