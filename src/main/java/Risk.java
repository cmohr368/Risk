
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Risk  {
    public static void main(String[] args) {
        int numPlayers;
        boolean gameOver=false;
        
        ArrayList<Integer> deck = shuffleDeck();
        
        ArrayList<territory> territories = new ArrayList<>();
        
        createTerritories(territories);
        System.out.println("WELCOME TO RISK");
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players?");
        numPlayers=sc.nextInt();
        
        sc.nextLine();
        
        ArrayList<Player> players = createPlayers(numPlayers);
        
        
        int startingNum=chooseFirstPlayer(numPlayers, players);
                
        System.out.println("\n"+players.get(startingNum).getName()+" will start");
        System.out.println("\nWould you like to auto assign the territories?(y/n)");
        String answer= sc.nextLine();
        if(answer.equals("y")){
            random(players, territories, startingNum);
        }
        else{
            claiming(players, territories, startingNum);
        }
        
        int playerTurn=startingNum;
        
        
        
        while(!gameOver){
            
            reEnforce(players.get(playerTurn),players, territories, playerTurn);
            
            attack(players.get(playerTurn), players, territories, playerTurn, deck);
            
            if(players.get(playerTurn).territories.size()==42){
                gameOver=true;
            }
            else{
                fortify(players.get(playerTurn), players, territories, playerTurn);
                playerTurn++;
                if(playerTurn==numPlayers){
                    playerTurn=0;
                }
            }
            
        }
        System.out.println(players.get(playerTurn).getName()+" WINS!!!");
        
    }
    
    public static ArrayList<Integer> shuffleDeck(){
        int deckSize= 42;

        ArrayList<Integer> deck= new ArrayList<>();

        for(int i= 0; i<deckSize; i++){
            if(i<20){
                deck.add(1);
            } else if(i>19 && i<35){
                deck.add(2);
            } else if(i>34 && i<42){
                deck.add(3);
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public static int getCard(ArrayList<Integer> list){
        
        if(list.get(0)== 1){
            list.remove(0);
            return 1;
        } 
        else if(list.get(0)== 2){
            list.remove(0);
            return 2;
        } 
        else{
            list.remove(0);
            return 3;
        }
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
        ArrayList<Integer> startingNum = new ArrayList<>();
        
        
        for(int i=0; i<numPlayers;i++){
            int temp=(int)(Math.random()*6)+1;
            System.out.println(players.get(i).getName()+" rolled a "+temp);
            if(temp>max){
                max=temp;
                startingNum.clear();
                startingNum.add(i);
            }
            
            else if(temp==max){
                startingNum.add(i);
            }
        }
        if(startingNum.size()>1){
            ArrayList<Player> ties=new ArrayList<>();
            for(int x=0; x<startingNum.size();x++){
                ties.add(players.get(startingNum.get(x)));
                System.out.print(players.get(startingNum.get(x)).getName()+" | ");
            }
            System.out.println("tied and will reroll");
            int tempNum= chooseFirstPlayer(ties.size(),ties);
            tempNum=startingNum.get(tempNum);
            startingNum.clear();
            startingNum.add(tempNum);
        }
        return startingNum.get(0);
    }
    
    //works only for player groups of 2, 3, and 4 for now because 42 is only divisible by those
    public static void random(ArrayList<Player> players, ArrayList<territory> territories, int playerNum){
        int count=0;
        int numTerritories=0;
        if(players.size()==2){
            numTerritories=21;
        }
        else if(players.size()==3){
            numTerritories=14;
        }
        else if(players.size()==6){
            numTerritories=7;
        }
        for(int i=0;i<players.size();i++){
            for(int x=count;x<numTerritories+count;x++){
                territories.get(x).addArmy();
                players.get(i).addTerritory(territories.get(x));
                players.get(i).placeArmy();
                territories.get(x).occupy();
                territories.get(x).setPlayerNum(i);
            }
            count+=numTerritories;
        }
        
        for(int i=0;i<players.size();i++){
            int counter=0;
            while(players.get(i).unPlacedArmies!=0){
                players.get(i).territories.get(counter).addArmy();
                players.get(i).placeArmy();
                counter++;
                if(players.get(i).territories.size()==counter){
                    counter=0;
                }
            }
        }
        
    }
    
    public static void claiming(ArrayList<Player> players, ArrayList<territory> territories, int playerNum ){
        Scanner sc = new Scanner(System.in);
        int claimed=0;
        while(players.get(playerNum).unplacedArmies()!=0){
            printTerritories(players, territories);
            System.out.println("\n"+players.get(playerNum).getName()+" place an army: (armies left "+players.get(playerNum).unplacedArmies()+")");
            int territoryNum=sc.nextInt();
            
            //add if number is greater then 41 and if its not a number
            
            if(claimed<42&&territories.get(territoryNum).isOccupied()||territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                while(claimed<42&&territories.get(territoryNum).isOccupied()||territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                    if(territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                        System.out.println("Territory is already claimed by other player, please choose a different territory");
                    }
                    else if(claimed<42&&territories.get(territoryNum).isOccupied()){
                        System.out.println("All territories must be claimed before re-enforements can be add, please choose a different territory");
                    }
                    territoryNum=sc.nextInt();
                }
            }
            
            claimed++;
            territories.get(territoryNum).addArmy();
            players.get(playerNum).placeArmy();
            
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
    
    public static void printTerritories(ArrayList<Player> players, ArrayList<territory> territories){
        String continent;
        
        System.out.print("\nTerritories:");
        int i=0;
        while(i<territories.size()-1){
            continent=territories.get(i).getContinent();
            System.out.println("\n"+continent+":");
            do{
                if(!territories.get(i).isOccupied()){
                    System.out.print(i+". "+territories.get(i).getName()+ " | ");
                }
                else if(territories.get(i).isOccupied()){
                    System.out.print(i+". "+territories.get(i).getName()+ " - "+players.get(territories.get(i).getPlayerNum()).getName() +"("+territories.get(i).numArmies()+") | ");
                }
                i++;
            }while(i<territories.size()&&continent==territories.get(i).getContinent());
        }
    }
    
    public static void reEnforce(Player p1, ArrayList<Player> players, ArrayList<territory> territories, int playerNum){
        Scanner sc = new Scanner(System.in);
        int armies=(p1.territories.size())/3;
        if(armies<3){armies=3;}
        if(p1.northCount==9){armies+=5;}
        if(p1.southCount==4){armies+=2;}
        if(p1.europeCount==7){armies+=5;}
        if(p1.africaCount==6){armies+=3;}
        if(p1.asiaCount==12){armies+=7;}
        if(p1.australiaCount==4){armies+=2;}
        
        printTerritories(players, territories);
        
        System.out.println("\nInfantry Cards - "+p1.infantryCount+" | Cavalry Cards - "+p1.cavalryCount+" | Artillery Cards - "+p1.artilleryCount);
        
        if(p1.infantryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your infantry cards?(y/n)");
            String answer= sc.nextLine();
            if(answer=="y"){
                armies+=3;
                p1.useInfantry();
            }
        }
        
        if(p1.cavalryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your cavalry cards?(y/n)");
            String answer= sc.nextLine();
            if(answer=="y"){
                armies+=15;
                p1.useCavalry();
            }
        }
        
        if(p1.artilleryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your artillery cards?(y/n)");
            String answer= sc.nextLine();
            if(answer=="y"){
                armies+=30;
                p1.useArtillery();
            }
        }
        
        //add ability to send more then 1 army
        System.out.println("\n"+p1.getName()+" you may place "+armies+" armies");
        for(int i=0;i<armies;i++){
            int territoryNum=sc.nextInt();
            if(territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                while(territories.get(territoryNum).isOccupied()&&!(territories.get(territoryNum).getPlayerNum()==playerNum)){
                    System.out.println("Territory is already claimed by other player, please choose a different territory");
                    territoryNum=sc.nextInt();
                }
            }
            territories.get(territoryNum).addArmy();
        }
    }
    
    public static void attack(Player p1, ArrayList<Player> players, ArrayList<territory> territories, int playerNum, ArrayList<Integer> deck){
        printTerritories(players, territories);
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWould you like to attack this turn?(y/n)");
        String answer= sc.nextLine();
        while(answer.equals("y")){
            System.out.println("\nChoose territory to attack with");
            int territoryNum= sc.nextInt();
            if(territories.get(territoryNum).getPlayerNum()!=playerNum){
                while(territories.get(territoryNum).getPlayerNum()!=playerNum){
                    System.out.println("\nPlease choose your own territory");
                    territoryNum= sc.nextInt();
                }
            }
            
            //add cant attack your own territory
            System.out.println("\nChoose territory to attack");
            int attackNum= sc.nextInt();
            if(!territories.get(territoryNum).isNeighbor(territories.get(attackNum))||territories.get(attackNum).getPlayerNum()==playerNum){
                while(!territories.get(territoryNum).isNeighbor(territories.get(attackNum))||territories.get(attackNum).getPlayerNum()==playerNum){
                    if(!territories.get(territoryNum).isNeighbor(territories.get(attackNum))){
                        System.out.println("\nYou can not reach that territory, choose another");
                    }
                    else if(territories.get(attackNum).getPlayerNum()==playerNum){
                        System.out.println("\nYou can't attack your own territory, choose another");
                    }
                    attackNum= sc.nextInt();
                }
            }
            
            attacking(players.get(playerNum), players.get(territories.get(attackNum).getPlayerNum()), territories.get(territoryNum),territories.get(attackNum), territories.get(territoryNum).numArmies(), territories.get(attackNum).numArmies(), deck, playerNum);
            printTerritories(players,territories);
            System.out.println("\nWould you like to attack again?(y/n)");
            sc.nextLine();
            answer= sc.nextLine();
            
        }
    }
    
    public static void attacking(Player p1, Player p2, territory t1, territory t2, int numArmies, int num2Armies, ArrayList<Integer> deck, int playerNum){
        Scanner sc = new Scanner(System.in);
        
        ArrayList<Integer> t1Num= new ArrayList<>();
        ArrayList<Integer> t2Num= new ArrayList<>();
        
        int t1Deaths=0;
        int t2Deaths=0;
        
        boolean t1Wins=false;
        
        if(t1.numArmies()>3){
            System.out.println("\nHow many armies would you like to attack with?(max 3)");
        }
        else{
            System.out.println("\nHow many armies would you like to attack with?(max "+(t1.numArmies()-1)+")");
        }
        int attackArmies= sc.nextInt();
        if(attackArmies>3&&attackArmies>(t1.numArmies()-1)){
            while(attackArmies>3&&attackArmies>(t1.numArmies()-1)){
                if(t1.numArmies()>3){
                    System.out.println("\nEnter 3 or less");
                }
                else{
                    System.out.println("\nEnter a number less then "+(t1.numArmies()));
                }
                attackArmies= sc.nextInt();
            }
        }
        int defendArmies;
        if(t2.numArmies()>1){
            System.out.println("\n"+p2.getName()+" how many armies would you like to defend with?(max 2)");
            defendArmies= sc.nextInt();
            if(defendArmies>2){
                while(defendArmies>2){
                    System.out.println("\n"+p2.getName()+" enter 2 or less");
                    defendArmies= sc.nextInt();
                }
            }
        }
        else{
            System.out.println("\n"+p2.getName()+" you can defend with only one army");
            defendArmies=1;
        }
        
        
        
        t1Num=t1.attack(attackArmies);
        t2Num=t2.defend(defendArmies);
        
        
        Collections.sort(t1Num);
        Collections.sort(t2Num);
        Collections.reverse(t1Num);
        Collections.reverse(t2Num);
        
        System.out.print(p1.getName()+" rolled | ");
        
        for(int x=0;x<t1Num.size();x++){
            System.out.print(t1Num.get(x)+" | ");
        }
        
        System.out.print("\n"+p2.getName()+" rolled | ");
        
        for(int y=0;y<t2Num.size();y++){
            System.out.print(t2Num.get(y)+" | ");
        }
        
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
            else{
               t1Deaths++;
            }
        }
        System.out.println("\n"+p1.getName()+" lost "+t1Deaths+" armies");
        System.out.println(p2.getName()+" lost "+t2Deaths+" armies");
        
        if(t2.numArmies()==t2Deaths){
            t1Wins=true;
        }
        
        t1.deleteArmy(t1Deaths);
        t2.deleteArmy(t2Deaths);
        
        if(t1Wins){
            System.out.println(p1.getName()+" won the battle and gained the territory!");
            System.out.println("How many armies would you like to send to "+t2.getName()+"(max "+(t1.armies-1)+")");
            int armiesMoving= sc.nextInt();
            if(armiesMoving>t1.armies-1){
                while(armiesMoving>t1.armies-1){
                    System.out.println("\nYou can enter a max of "+(t1.armies-1)+" troops, try again");
                    armiesMoving= sc.nextInt();
                }
            }
            moveTroops(armiesMoving,t1,t2);
            t2.setPlayerNum(playerNum);
            p1.addTerritory(t2);
            p2.looseTerritory(t2);
            
            int card=getCard(deck);
            if(card==1){
                System.out.println("\n"+p1.getName()+" gained an infantry card");
            }
        
            if(card==2){
                System.out.println("\n"+p1.getName()+" gained an cavalry card");
            }
        
            if(card==3){
                System.out.println("\n"+p1.getName()+" gained an artillery card");
            }
            p1.addCard(card);
        }
    }
    
    public static void fortify(Player p1, ArrayList<Player> players, ArrayList<territory> territories, int playerNum){
        ArrayList<Integer> movedTo= new ArrayList<>();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\n"+p1.getName()+" you may now fortify you territories");
        System.out.println("Would you like to move any armies?(y/n)");
        String answer= sc.nextLine();
        
        while(answer.equals("y")){
            System.out.println("\nChoose territory to move troops from");
            int territoryNum= sc.nextInt();
            if(territories.get(territoryNum).getPlayerNum()!=playerNum){
                while(territories.get(territoryNum).getPlayerNum()!=playerNum){
                    System.out.println("\nPlease choose your own territory");
                    territoryNum= sc.nextInt();
                }
            }
            if(movedTo.contains(territoryNum)){
                while(movedTo.contains(territoryNum)){
                    System.out.println("\nYou cannot move troops from a territory you already moved troops too, enter another number");
                    territoryNum= sc.nextInt();
                }
            }
                
            System.out.println("\nChoose territory to move them too");
            int territory2Num= sc.nextInt();
            if(!territories.get(territoryNum).isNeighbor(territories.get(territory2Num))&&territories.get(territoryNum).getPlayerNum()!=playerNum){
                while(!territories.get(territoryNum).isNeighbor(territories.get(territory2Num))&&territories.get(territoryNum).getPlayerNum()!=playerNum){
                    if(!territories.get(territoryNum).isNeighbor(territories.get(territory2Num))){
                        System.out.println("\nYou can not reach that territory, choose another");
                    }
                    else if(territories.get(territoryNum).getPlayerNum()!=playerNum){
                        System.out.println("\nYou must move them to a territory you own, choose another");
                    }
                    territory2Num= sc.nextInt();
                }
            }
            movedTo.add(territory2Num);
            
            System.out.println("\nHow many troops would you like to move?");
            int troops= sc.nextInt();
            if(!(troops<territories.get(territoryNum).numArmies())){
                while(!(troops<territories.get(territoryNum).numArmies())){
                    System.out.println("\nYou do not have enough troops to do that, enter another number");
                    troops= sc.nextInt();
                }
            }
            moveTroops(troops, territories.get(territoryNum), territories.get(territory2Num));
            sc.nextLine();
            System.out.println("\nWould you like to move more troops?(y/n)");
            answer= sc.nextLine();
        }
    }
    
    public static void moveTroops(int troops, territory location1, territory location2){
        location1.deleteArmy(troops);
        location2.addArmies(troops);
    }
    
    public static ArrayList<territory> createTerritories(ArrayList<territory> territories){
        territory temp1=new territory("Alaska","North America");
        territory temp2=new territory("Alberta","North America");
        territory temp3=new territory("Central America","North America");
        territory temp4=new territory("Eastern United States","North America");
        territory temp5=new territory("Greenland","North America");
        territory temp6=new territory("Northwest Territory","North America");
        territory temp7=new territory("Ontario","North America");
        territory temp8=new territory("Quebec","North America");
        territory temp9=new territory("Western United States","North America");
        
        territory temp10=new territory("Argentina","South America");
        territory temp11=new territory("Brazil","South America");
        territory temp12=new territory("Peru","South America");
        territory temp13=new territory("Venezuela","South America");
        
        territory temp14=new territory("Great Britain","Europe");
        territory temp15=new territory("Iceland","Europe");
        territory temp16=new territory("Northern Europe","Europe");
        territory temp17=new territory("Scandinavia","Europe");
        territory temp18=new territory("Southern Europe","Europe");
        territory temp19=new territory("Ukraine","Europe");
        territory temp20=new territory("Western Europe","Europe");
        
        territory temp21=new territory("Congo","Africa");
        territory temp22=new territory("East Afica","Africa");
        territory temp23=new territory("Egypt","Africa");
        territory temp24=new territory("Madagascar","Africa");
        territory temp25=new territory("North Africa","Africa");
        territory temp26=new territory("South Africa","Africa");
        
        territory temp27=new territory("Afghanistan","Asia");
        territory temp28=new territory("China","Asia");
        territory temp29=new territory("India","Asia");
        territory temp30=new territory("Irkutsk","Asia");
        territory temp31=new territory("Japan","Asia");
        territory temp32=new territory("Kamchatka","Asia");
        territory temp33=new territory("Middle East","Asia");
        territory temp34=new territory("Mongolia","Asia");
        territory temp35=new territory("Siam","Asia");
        territory temp36=new territory("Siberia","Asia");
        territory temp37=new territory("Ural","Asia");
        territory temp38=new territory("Yakutsk","Asia");
        
        territory temp39=new territory("Eastern Australia","Australia");
        territory temp40=new territory("Indonesia","Australia");
        territory temp41=new territory("New Guinea","Australia");
        territory temp42=new territory("Western Australia","Australia");
        
        temp1.addNeighbor(temp2);
        temp1.addNeighbor(temp6);
        temp1.addNeighbor(temp32);
        
        temp2.addNeighbor(temp1);
        temp2.addNeighbor(temp6);
        temp2.addNeighbor(temp7);
        temp2.addNeighbor(temp9);
        
        temp3.addNeighbor(temp4);
        temp3.addNeighbor(temp9);
        temp3.addNeighbor(temp13);
        
        temp4.addNeighbor(temp3);
        temp4.addNeighbor(temp7);
        temp4.addNeighbor(temp8);
        temp4.addNeighbor(temp9);
        
        temp5.addNeighbor(temp6);
        temp5.addNeighbor(temp7);
        temp5.addNeighbor(temp8);
        temp5.addNeighbor(temp15);
        
        temp6.addNeighbor(temp1);
        temp6.addNeighbor(temp2);
        temp6.addNeighbor(temp5);
        temp6.addNeighbor(temp7);
        
        temp7.addNeighbor(temp2);
        temp7.addNeighbor(temp4);
        temp7.addNeighbor(temp5);
        temp7.addNeighbor(temp6);
        temp7.addNeighbor(temp8);
        temp7.addNeighbor(temp9);
        
        temp8.addNeighbor(temp4);
        temp8.addNeighbor(temp5);
        temp8.addNeighbor(temp7);
        
        temp9.addNeighbor(temp2);
        temp9.addNeighbor(temp3);
        temp9.addNeighbor(temp4);
        temp9.addNeighbor(temp7);
        
        temp10.addNeighbor(temp11);
        temp10.addNeighbor(temp12);
        
        temp11.addNeighbor(temp10);
        temp11.addNeighbor(temp12);
        temp11.addNeighbor(temp13);
        temp11.addNeighbor(temp25);
        
        temp12.addNeighbor(temp10);
        temp12.addNeighbor(temp11);
        temp12.addNeighbor(temp13);
        
        temp13.addNeighbor(temp3);
        temp13.addNeighbor(temp11);
        temp13.addNeighbor(temp12);
        
        temp14.addNeighbor(temp15);
        temp14.addNeighbor(temp16);
        temp14.addNeighbor(temp17);
        temp14.addNeighbor(temp20);
        
        temp15.addNeighbor(temp5);
        temp15.addNeighbor(temp14);
        temp15.addNeighbor(temp17);
        
        temp16.addNeighbor(temp14);
        temp16.addNeighbor(temp17);
        temp16.addNeighbor(temp18);
        temp16.addNeighbor(temp19);
        temp16.addNeighbor(temp20);
        
        temp17.addNeighbor(temp14);
        temp17.addNeighbor(temp15);
        temp17.addNeighbor(temp16);
        temp17.addNeighbor(temp19);
        
        temp18.addNeighbor(temp16);
        temp18.addNeighbor(temp19);
        temp18.addNeighbor(temp20);
        temp18.addNeighbor(temp23);
        temp18.addNeighbor(temp25);
        
        temp19.addNeighbor(temp16);
        temp19.addNeighbor(temp17);
        temp19.addNeighbor(temp18);
        temp19.addNeighbor(temp27);
        temp19.addNeighbor(temp33);
        temp19.addNeighbor(temp37);
        
        temp20.addNeighbor(temp14);
        temp20.addNeighbor(temp16);
        temp20.addNeighbor(temp18);
        temp20.addNeighbor(temp25);
        
        temp21.addNeighbor(temp22);
        temp21.addNeighbor(temp25);
        temp21.addNeighbor(temp26);
        
        temp22.addNeighbor(temp21);
        temp22.addNeighbor(temp23);
        temp22.addNeighbor(temp24);
        temp22.addNeighbor(temp25);
        temp22.addNeighbor(temp26);
        temp22.addNeighbor(temp33);
        
        temp23.addNeighbor(temp22);
        temp23.addNeighbor(temp25);
        temp23.addNeighbor(temp18);
        temp23.addNeighbor(temp33);
        
        temp24.addNeighbor(temp22);
        temp24.addNeighbor(temp26);
        
        temp25.addNeighbor(temp21);
        temp25.addNeighbor(temp22);
        temp25.addNeighbor(temp23);
        temp25.addNeighbor(temp18);
        temp25.addNeighbor(temp20);
        temp25.addNeighbor(temp11);
        
        temp26.addNeighbor(temp21);
        temp26.addNeighbor(temp22);
        temp26.addNeighbor(temp24);
        
        temp27.addNeighbor(temp28);
        temp27.addNeighbor(temp29);
        temp27.addNeighbor(temp33);
        temp27.addNeighbor(temp37);
        temp27.addNeighbor(temp19);
        
        temp28.addNeighbor(temp27);
        temp28.addNeighbor(temp29);
        temp28.addNeighbor(temp34);
        temp28.addNeighbor(temp35);
        temp28.addNeighbor(temp36);
        temp28.addNeighbor(temp37);
        
        temp29.addNeighbor(temp27);
        temp29.addNeighbor(temp28);
        temp29.addNeighbor(temp33);
        temp29.addNeighbor(temp35);
        
        temp30.addNeighbor(temp32);
        temp30.addNeighbor(temp34);
        temp30.addNeighbor(temp36);
        temp30.addNeighbor(temp38);
        
        temp31.addNeighbor(temp32);
        temp31.addNeighbor(temp34);
        
        temp32.addNeighbor(temp31);
        temp32.addNeighbor(temp34);
        temp32.addNeighbor(temp30);
        temp32.addNeighbor(temp38);
        temp32.addNeighbor(temp1);
        
        temp33.addNeighbor(temp27);
        temp33.addNeighbor(temp29);
        temp33.addNeighbor(temp22);
        temp33.addNeighbor(temp23);
        temp33.addNeighbor(temp18);
        temp33.addNeighbor(temp19);
        
        temp34.addNeighbor(temp28);
        temp34.addNeighbor(temp30);
        temp34.addNeighbor(temp31);
        temp34.addNeighbor(temp32);
        temp34.addNeighbor(temp36);
        
        temp35.addNeighbor(temp28);
        temp35.addNeighbor(temp29);
        temp35.addNeighbor(temp40);
        
        temp36.addNeighbor(temp37);
        temp36.addNeighbor(temp27);
        temp36.addNeighbor(temp28);
        temp36.addNeighbor(temp34);
        temp36.addNeighbor(temp30);
        temp36.addNeighbor(temp38);
        
        temp37.addNeighbor(temp27);
        temp37.addNeighbor(temp28);
        temp37.addNeighbor(temp36);
        temp37.addNeighbor(temp19);
        
        temp38.addNeighbor(temp30);
        temp38.addNeighbor(temp32);
        temp38.addNeighbor(temp36);
        
        temp39.addNeighbor(temp41);
        temp39.addNeighbor(temp42);
        
        temp40.addNeighbor(temp41);
        temp40.addNeighbor(temp42);
        temp40.addNeighbor(temp35);
        
        temp41.addNeighbor(temp39);
        temp41.addNeighbor(temp40);
        temp41.addNeighbor(temp42);
        
        temp42.addNeighbor(temp39);
        temp42.addNeighbor(temp40);
        temp42.addNeighbor(temp41);
        
        territories.add(temp1);
        territories.add(temp2);
        territories.add(temp3);
        territories.add(temp4);
        territories.add(temp5);
        territories.add(temp6);
        territories.add(temp7);
        territories.add(temp8);
        territories.add(temp9);
        territories.add(temp10);
        territories.add(temp11);
        territories.add(temp12);
        territories.add(temp13);
        territories.add(temp14);
        territories.add(temp15);
        territories.add(temp16);
        territories.add(temp17);
        territories.add(temp18);
        territories.add(temp19);
        territories.add(temp20);
        territories.add(temp21);
        territories.add(temp22);
        territories.add(temp23);
        territories.add(temp24);
        territories.add(temp25);
        territories.add(temp26);
        territories.add(temp27);
        territories.add(temp28);
        territories.add(temp29);
        territories.add(temp30);
        territories.add(temp31);
        territories.add(temp32);
        territories.add(temp33);
        territories.add(temp34);
        territories.add(temp35);
        territories.add(temp36);
        territories.add(temp37);
        territories.add(temp38);
        territories.add(temp39);
        territories.add(temp40);
        territories.add(temp41);
        territories.add(temp42);
        
        return territories;
    }
}
