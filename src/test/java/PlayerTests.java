import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerTests {
    String name= "test";
    int unplacedArmies= 5;
    ArrayList<territory> territories= new ArrayList<>();
    int totalInfantry= 10, totalCavalry= 10, totalArtillery= 10;

    @Test
    public void getNameTest(){
        Player player= new Player(name, unplacedArmies);
        assertEquals(player.getName(), name);
    }

    @Test
    public void addCardTest(){
        Player player= new Player(name, unplacedArmies);
        int card1= 1, card2= 2, card3= 3;
        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card3);
        assertEquals(player.infantryCount, 1);
        assertEquals(player.cavalryCount, 1);
        assertEquals(player.artilleryCount, 1);
    }

    @Test
    public void addTerritoryTest(){
        Player p1 = new Player("Name",5);
        territory t1 = new territory("Name", "North America");
        territory t2 = new territory("Name", "Europe");
        territory t3 = new territory("Name", "South America");
        territory t4 = new territory("Name", "Australia");
        territory t5 = new territory("Name", "Africa");
        territory t6 = new territory("Name", "Asia");
        p1.addTerritory(t1);
        p1.addTerritory(t2);
        p1.addTerritory(t3);
        p1.addTerritory(t4);
        p1.addTerritory(t5);
        p1.addTerritory(t6);
        assertTrue(p1.northCount == 1);
        assertTrue(p1.europeCount == 1);
        assertTrue(p1.africaCount == 1);
        assertTrue(p1.southCount == 1);
        assertTrue(p1.australiaCount == 1);
        assertTrue(p1.asiaCount == 1);
    }

    @Test
    public void looseTerritoryTest(){
        Player p1 = new Player("Name",5);
        territory t1 = new territory("Name", "North America");
        territory t2 = new territory("Name", "Europe");
        territory t3 = new territory("Name", "South America");
        territory t4 = new territory("Name", "Australia");
        territory t5 = new territory("Name", "Africa");
        territory t6 = new territory("Name", "Asia");
        p1.looseTerritory(t1);
        p1.looseTerritory(t2);
        p1.looseTerritory(t3);
        p1.looseTerritory(t4);
        p1.looseTerritory(t5);
        p1.looseTerritory(t6);
        assertTrue(p1.northCount == -1);
        assertTrue(p1.europeCount == -1);
        assertTrue(p1.africaCount == -1);
        assertTrue(p1.southCount == -1);
        assertTrue(p1.australiaCount == -1);
        assertTrue(p1.asiaCount == -1);
    }

    @Test
    public void conqueredTest(){
        Player player= new Player(name, unplacedArmies);
        player.conquered();
        assertEquals(player.territoriesConquered, 1);
    }

    @Test
    public void placeArmyTest(){
        Player player= new Player(name, unplacedArmies);
        player.placeArmy();
        assertEquals(player.unPlacedArmies, unplacedArmies- 1);
    }

    @Test
    public void unplacedArmiesTest(){
        Player player= new Player(name, unplacedArmies);
        assertEquals(player.unplacedArmies(), unplacedArmies);
    }

    @Test
    public void getTerritoriesTest(){
        Player player= new Player(name, unplacedArmies);
        //player.territories= new ArrayList<>();
        assertArrayEquals(player.getTerritories().toArray(), player.territories.toArray());
    }

    @Test
    public void useInfantryTest(){
        Player player= new Player(name, unplacedArmies);
        //player.infantryCount= totalInfantry;
        player.cavalryCount= totalInfantry;
        player.useInfantry();
        //assertEquals(player.infantryCount, totalInfantry- 3);
        assertEquals(player.cavalryCount, totalInfantry- 3);
    }

    @Test
    public void useCavalryTest(){
        Player player= new Player(name, unplacedArmies);
        player.cavalryCount= totalCavalry;
        player.useCavalry();
        assertEquals(player.cavalryCount, totalCavalry- 3);
    }

    @Test
    public void useArtilleryTest(){
        Player player= new Player(name, unplacedArmies);
        //player.artilleryCount= totalArtillery;
        player.cavalryCount= totalArtillery;
        player.useArtillery();
//        assertEquals(player.artilleryCount, totalArtillery- 3);
        assertEquals(player.cavalryCount, totalArtillery- 3);
    }

    @Test
    public void addUndosTest(){
        String test= "test";
        int b= 0;
        Player player= new Player(test, b);
        player.undos= 5;
        player.addUndos(1);
        assertEquals(player.undos, 6);
    }

    @Test
    public void useUndoTest(){
        String test= "test";
        int b= 0;
        Player player= new Player(test, b);
        player.undos= 5;
        player.useUndo();
        assertEquals(player.undos, 4);
    }
}
