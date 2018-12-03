import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiskTests {

    @Test
    public void riskchooseFirstPlayerTest(){
        Player p1 = new Player("name",10);
        Player p2 = new Player("name2",10);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        int num = Risk.chooseFirstPlayer(2, players);
        System.out.println(num);
        assertTrue(num == 0 || num == 1);
    }
}
