import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TerritoryTests {
    String name= "test";
    String continent= "test";
    territory testTerritory= new territory(name, continent);

    @Test
    public void addArmyTest(){
        testTerritory.armies= 5;
        testTerritory.addArmy();
        int newArmySize= testTerritory.armies;
        assertEquals(newArmySize, testTerritory.armies);
    }

    @Test
    public void addArmiesTest(){
        testTerritory.armies= 5;
        int addArmies= 3;
        testTerritory.addArmies(addArmies);
        int newArmySize= testTerritory.armies;
        assertEquals(newArmySize, testTerritory.armies);
    }

    @Test
    public void deleteArmiesTest(){
        testTerritory.armies= 5;
        int deleteArmies= 3;
        testTerritory.deleteArmy(deleteArmies);
        int newArmySize= testTerritory.armies;
        assertEquals(newArmySize, testTerritory.armies);
    }


}
