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

    @Test
    public void occupyTest(){
        territory t1 = new territory("name","continent");
        t1.occupy();
        assertTrue(t1.occupied);
        assertTrue(t1.isOccupied() == t1.occupied);
    }

    @Test
    public void attackTest(){
        territory t1 = new territory("name","continent");
        assertTrue(t1.attack(6).size() == 6);
    }

    @Test
    public void setPlayerNumTest(){
        territory t1 = new territory("name","continent");
        t1.setPlayerNum(5);
        assertTrue(t1.playerNum == 5);
        assertTrue(t1.playerNum == t1.getPlayerNum());
    }

    @Test
    public void setPlayerNameTest(){
        territory t1 = new territory("name","continent");
        String name = "name";
        t1.setPlayerName(name);
        assertTrue(t1.playerName.equals(name));
        assertTrue(t1.playerName == t1.getPlayerName());
    }

    @Test
    public void getContinentTest(){
        territory t1 = new territory("name","continent");
        assertTrue(t1.continent == "continent");
        assertTrue(t1.getContinent() == t1.continent);
    }

    @Test
    public void getNameTest(){
        territory t1 = new territory("name","continent");
        assertTrue(t1.name == "name");
    }

    @Test
    public void getNeighborsTest(){
        territory t1 = new territory("name","continent");
        territory t2 = new territory("name2","continent");
        territory t3 = new territory("name3","continent");
        ArrayList<territory> neighbors = new ArrayList<>();
        neighbors.add(t2);
        neighbors.add(t3);
        t1.addNeighbor(t2);
        t1.addNeighbor(t3);
        assertTrue(t1.isNeighbor(t2));

    }

    @Test
    public void defendTest(){
        territory t1 = new territory("name","continent");
        assertTrue(t1.attack(6).size() == 6);
    }


}
