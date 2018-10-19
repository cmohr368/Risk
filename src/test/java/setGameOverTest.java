import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class setGameOverTest {

    @Test
    public void setGameOverTest(){
        Game game= new Game();
        game.gameOver= true;
        assertTrue(game.gameOver);
    }
}
