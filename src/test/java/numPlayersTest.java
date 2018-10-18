//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//public class numPlayersTest{
//
//    @Test
//    public void numPlayersTest(){
//        //Testing with 3 players to make sure there are 3 players
//        ArrayList<Player> players= new ArrayList<>();
//        String a= new String("a");
//        String b= new String("b");
//        String c= new String("c");
//        Player player1= new Player(a, 1);
//        Player player2= new Player(b, 2);
//        Player player3= new Player(c, 3);
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//
//        ArrayList<territory> territories= new ArrayList<>();
//        Risk.createTerritories(territories);
//        //Game.createTerritories(territories);
//        int playerNum1= 2, playerNum2= 3, playerNum3= 4;
//        int num= getSize(players, territories, playerNum2);
//        assertEquals(num, playerNum2);
//    }
//
//    private int getSize(ArrayList<Player> players, ArrayList<territory> territories, int numplayers) {
//        int num= players.size();
//        return num;
//    }
//}