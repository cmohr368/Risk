import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class createTerritoryTest {

    @Test
    public void createTerritoryTest(){
        ArrayList<territory> territories = new ArrayList<>();
        int totalTerritories = 42;
        Game game= new Game();
        game.createTerritories();

        assertEquals(game.territories.size(), totalTerritories);
    }
}
