import com.amazonaws.services.dynamodbv2.xspec.M;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    int numPlayers;
    boolean gameOver;
    Deck deck;
    ArrayList<territory> territories;
    ArrayList<Player> players;
    int playerTurn;
    int stage;
    String input;
    //TwitterController tweet;

    Game(){
        stage=0;
        numPlayers=0;
        gameOver=false;
        playerTurn=0;
        deck=new Deck();
        territories=new ArrayList<>();
        players=new ArrayList<>();
        input="";

        //tweet = new TwitterController();
        //tweet.connectTwitter(tweet.getKeys());
    }
    public void setNumPlayers(int numPlayers){this.numPlayers=numPlayers;}

    public void setGameOver(boolean gameOver){this.gameOver=gameOver;}

    public void setPlayerTurn(int playerTurn){this.playerTurn=playerTurn;}

    public int getNumPlayers(){return numPlayers;}

    public boolean getGameOver(){return gameOver;}

    public ArrayList<territory> getTerritories(){return territories;}

    public ArrayList<Player> getPlayers(){return players;}

    public int getPlayerTurn(){return playerTurn;}

    public void nextPlayer(){
        playerTurn++;
        if(playerTurn==players.size()){playerTurn=0;}
    }

    public Player currentPlayer(){return players.get(playerTurn);}

    public void nextStage(){
        /*
        stage 0 = claiming
        stage 1 = reenforce
        stage 2 = attack
        stage 3 = fortify
         */
        stage++;

        if (stage==4){stage=1;}
    }

    public void createPlayers(){
        Scanner sc = new Scanner(System.in);

        int startingArmies=0;
        if(numPlayers==2){startingArmies=40;}
        if(numPlayers==3){startingArmies=35;}
        if(numPlayers==4){startingArmies=30;}
        if(numPlayers==5){startingArmies=25;}
        if(numPlayers==6){startingArmies=20;}

        for(int i=0; i<numPlayers;i++){
            String name;
            System.out.println("Insert name of player "+(i+1)+"?");
            name=sc.next();
            Player a=new Player(name, startingArmies);
            players.add(a);
        }
    }

    public void createPlayers(RiskBot myBot){

        int startingArmies=0;
        if(numPlayers==2){startingArmies=40;}
        if(numPlayers==3){startingArmies=35;}
        if(numPlayers==4){startingArmies=30;}
        if(numPlayers==5){startingArmies=25;}
        if(numPlayers==6){startingArmies=20;}

        for(int i=0; i<numPlayers;i++){
            String name;
            myBot.sendMessage("Insert name of player "+(i+1)+"?");
            name= MainBot.getStringMessage(myBot);
            Player a=new Player(name, startingArmies);
            players.add(a);
        }
    }

    public void post(){
        String tweet="";
        for(int i=0; i<players.size();i++){
            tweet+=players.get(i).name+" has conquered "+players.get(i).territoriesConquered+" territories/n";
        }
        //game.TwitterController.postTweet(tweet);
    }

    public void createTerritories(){
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
    }
}
