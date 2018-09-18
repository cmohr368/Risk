package risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class Risk  {
    public static void main(String[] args) {
        
        int numPlayers;
        ArrayList<territory> territories = new ArrayList<>();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players?");
        numPlayers=sc.nextInt();
        
        ArrayList<Player> players = createPlayers(numPlayers);
        
        
        int startingNum=chooseFirstPlayer(numPlayers, players);
                
        System.out.println(players.get(startingNum).getName()+" will start");
    }
    
    public static ArrayList<territory> createTerritories(ArrayList<territory> territories){
        territory temp=new territory();
        territories.add(temp);
        
        
        return territories;
    }
    
    public static ArrayList<Player> createPlayers(int numPlayers){
        Scanner sc = new Scanner(System.in);
        
        int startingArmies=0;
        if(numPlayers==2){startingArmies=40;}
        if(numPlayers==3){startingArmies=35;}
        if(numPlayers==4){startingArmies=30;}
        if(numPlayers==5){startingArmies=25;}
        if(numPlayers==6){startingArmies=20;}
        
        ArrayList<Player> players=new ArrayList<>();
                
        for(int i=0; i<numPlayers;i++){
            String name;
            int playerNum=i+1;
            System.out.println("Insert name of player "+playerNum+"?");
            name=sc.next();
            Player a=new Player(name, startingArmies);
            players.add(a);
        }
        return players;
    }
    
    //rolls dice for each player and returns the player number of the winner
    public static int chooseFirstPlayer(int numPlayers, ArrayList<Player> players){
        int max=0;
        int startingNum=0;
        
        
        for(int i=0; i<numPlayers;i++){
            int temp=(int)(Math.random()*6)+1;
            System.out.println(players.get(i).getName()+" rolled a "+temp);
            
            //if there is a tie choose which player wins the tie with a 50/50 chance
            if(temp==max&&i>0){
                int tie=(int)(Math.random()*2);
                if(tie==0){
                    System.out.println(players.get(i).getName()+" won the tie over "+ players.get(startingNum).getName());
                    startingNum=i;
                }
                else{
                    System.out.println(players.get(startingNum).getName()+" won the tie over "+ players.get(i).getName());
                }
            }
            
            if(temp>max){
                max=temp;
                startingNum=i;
            }
        }

        return startingNum;
    }
    
    public static void claiming(ArrayList<Player> players, ArrayList<territory> territories, int playerNum ){
        Scanner sc = new Scanner(System.in);
        while(players.get(playerNum).unplacedArmies()!=0){
            System.out.println("Place an Army");
            int territoryNum=sc.nextInt();
            
            if(territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                System.out.println("Territory is already claimed by other player, please choose a different territory");
                territoryNum=sc.nextInt();
            }
            
            territories.get(territoryNum).addArmy();
            
            if(!territories.get(territoryNum).isOccupied()){
                territories.get(territoryNum).occupy();
                territories.get(territoryNum).setPlayerNum(playerNum);
                players.get(playerNum).addTerritory(territories.get(territoryNum));
                
            }
            
            playerNum++;
            
            if(playerNum==players.size()){
                playerNum=0;
            }
         }   
        
    }
    
    public static void attack(Player p1, Player p2, territory t1, territory t2, int numArmies, int num2Armies){
        ArrayList<Integer> t1Num= new ArrayList<>();
        ArrayList<Integer> t2Num= new ArrayList<>();
        
        int t1Deaths=0;
        int t2Deaths=0;
        
        boolean t1Wins=false;
        boolean t2Wins=false;
        
        t1Num=t1.attack(numArmies);
        t2Num=t2.defend(num2Armies);
        System.out.print(p1.getName()+" rolled | ");
        for(int one=0;one<t1Num.size();one++){
            System.out.print(t1Num.get(one)+" | ");
        }
        System.out.println();
        
        System.out.print(p2.getName()+" rolled | ");
        for(int two=0;two<t2Num.size();two++){
            System.out.print(t1Num.get(two)+" | ");
        }
        System.out.println();
        
        Collections.sort(t1Num);
        Collections.sort(t2Num);
        
        int numBattles;
        if(t1Num.size()<t2Num.size()){
            numBattles=t1Num.size();
        }
        else{
            numBattles=t2Num.size();
        }
        
        for(int i=0;i<numBattles;i++){
            if(t1Num.get(i)>t2Num.get(i)){
               t2Deaths++; 
            }
            if(t2Num.get(i)>t1Num.get(i)){
               t1Deaths++;
            }
        }
        
        if(t1Num.size()==t1Deaths){
            t2Wins=true;
        }
        else if(t2Num.size()==t2Deaths){
            t1Wins=true;
        }
        
        
        t1.deleteArmy(t1Deaths);
        t2.deleteArmy(t2Deaths);
        
        if(t2Wins){
            System.out.println(p2.getName()+" succesffully defended their territory!");
        }
        else if(t1Wins){
            p1.addTerritory(t2);
            p2.looseTerritory(t2);
            System.out.println(p2.getName()+" won the battle and gained the territory!");
        }
        else{
            attack(p1, p2, t1, t2, t1Num.size(), t2Num.size());
        }
    }
}
