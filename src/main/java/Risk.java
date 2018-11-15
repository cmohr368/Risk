
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Risk  {
    static Game game;

    static TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( game.input.equals("") )
            {
                timeOut();
            }
        }
    };

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        RiskBot myBot = new RiskBot();

        try {
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        game = new Game();

        System.out.println("WELCOME TO RISK");
        playing();
    }
    public static void playing(){
        Scanner sc = new Scanner(System.in);
        int startingNum = 0;

        /*
        System.out.println("Would you like to load a game?(y/n)");
        string load=sc.nextLine();

        if(load.equals("y")){
            load(Game game)
        }
        */

        //else{
            game.createTerritories();
            System.out.println("\nHow many players?");
            int numPlayers = sc.nextInt();
            game.setNumPlayers(numPlayers);

            sc.nextLine();

            game.createPlayers();

            startingNum = chooseFirstPlayer(game.getNumPlayers(), game.getPlayers());
            game.setPlayerTurn(startingNum);

            System.out.println("\n" + game.currentPlayer().getName() + " will start");
            System.out.println("\nWould you like to auto assign the territories?(y/n)");
            String answer = sc.nextLine();

            if (answer.equals("y")) {
                random();
            }

            else {
                claiming();
            }
        //}

        while(!game.getGameOver()){
            if(game.currentPlayer().territories.size()==42){
                game.setGameOver(true);
            }
            else if(game.stage==0){
                claiming();
                game.nextStage();
            }
            else if(game.stage==1){
                System.out.println(game.currentPlayer().name+" it is now your turn");
                CreditMgr.buying(game);
                check();
            }
            else{
                check();
            }
        }
        conquered();
        System.out.println(game.currentPlayer().getName()+" WINS!!!");
    }

    public static void loadGame(Game load){game= load;}
    
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
    public static void random(){
        int count=0;
        int numTerritories=0;
        if(game.players.size()==2){
            numTerritories=21;
        }
        else if(game.players.size()==3){
            numTerritories=14;
        }
        else if(game.players.size()==6){
            numTerritories=7;
        }
        for(int i=0;i<game.players.size();i++){
            for(int x=count;x<numTerritories+count;x++){
                game.territories.get(x).addArmy();
                game.players.get(i).addTerritory(game.territories.get(x));
                game.players.get(i).placeArmy();
                game.territories.get(x).occupy();
                game.territories.get(x).setPlayerNum(i);
                game.territories.get(x).setPlayerName(game.players.get(i).getName());
            }
            count+=numTerritories;
        }
        
        for(int i=0;i<game.players.size();i++){
            int counter=0;
            while(game.players.get(i).unPlacedArmies!=0){
                game.players.get(i).territories.get(counter).addArmy();
                game.players.get(i).placeArmy();
                counter++;
                if(game.players.get(i).territories.size()==counter){
                    counter=0;
                }
            }
        }
        
    }
    
    public static void claiming(){
        Scanner sc = new Scanner(System.in);
        int claimed=0;

        while(game.currentPlayer().unplacedArmies()!=0){

            printTerritories();
            System.out.println("\n"+game.currentPlayer().getName()+" place an army: (armies left "+game.currentPlayer().unplacedArmies()+")");
            int territoryNum=sc.nextInt();
            
            //add if number is greater then 41 and if its not a number
            territoryNum=checkTerritory(territoryNum, claimed);

            sc.nextLine();

            if(!(CreditMgr.undo(game))){
                //may cause issue, check if its being saved to game territories
                territory claim=game.getTerritories().get(territoryNum);
                claimed++;
                claim.addArmy();
                game.currentPlayer().placeArmy();

                if (!claim.isOccupied()) {
                    claim.occupy();
                    claim.setPlayerNum(game.getPlayerTurn());
                    claim.setPlayerName(game.players.get(game.getPlayerTurn()).getName());
                    game.currentPlayer().addTerritory(claim);

                }
                game.nextPlayer();
            }
         }
    }

    
    public static void reEnforce( ){
        Scanner sc = new Scanner(System.in);
        Player p1=game.currentPlayer();

        int armies= supportCount();
        armies+=redeemCards();
        
        System.out.println("\n"+p1.getName()+" you may place "+armies+" armies");
        for(int i=0;i<armies;i++){
            System.out.println("\nChoose a territory to place armies on ("+(armies-i)+" left)");
            int territoryNum=sc.nextInt();
            
            while(!playersTerritory(territoryNum)){
                System.out.println("\nTerritory is already claimed by other player, please choose a different territory");
                territoryNum=sc.nextInt();
            }
            
            System.out.println("\nHow many armies would you like to add (max "+(armies-i)+")");
            int numArmies=sc.nextInt();
            while(numArmies>armies-i) {
                System.out.println("\nChoose a number less then " + (armies - i));
                numArmies = sc.nextInt();
            }
            

            game.getTerritories().get(territoryNum).addArmies(numArmies);
            printTerritories();

            sc.nextLine();

            if(CreditMgr.undo(game)) {
                game.getTerritories().get(territoryNum).deleteArmy(numArmies);
                i--;
            }
            else{
                i+=numArmies;
            }

        }
        game.nextStage();
    }
    
    public static void attack(){
        printTerritories();
        Scanner sc = new Scanner(System.in);

        String answer= "";


        do{
            System.out.println("\nChoose territory to attack with");
            int territoryNum= sc.nextInt();

            while(!playersTerritory(territoryNum)){
                System.out.println("\nPlease choose your own territory");
                territoryNum= sc.nextInt();
            }

            //add cant attack your own territory
            System.out.println("\nChoose territory to attack");
            int attackNum= sc.nextInt();
            while(!areNeighbors(territoryNum,attackNum)||playersTerritory(attackNum)){
                if(!areNeighbors(territoryNum,attackNum)){
                    System.out.println("\nYou can not reach that territory, choose another");
                }
                else if(playersTerritory(attackNum)){
                    System.out.println("\nYou can't attack your own territory, choose another");
                }
                attackNum= sc.nextInt();
            }


            territory t1=game.territories.get(territoryNum);
            territory t2=game.territories.get(attackNum);
            Player p1= game.currentPlayer();
            Player p2= attacked(attackNum);

            int attackArmies=attackers(territoryNum, t1);
            int defendArmies=defenders(attackNum, t2, p2);

            attacking(t1, t2, attackArmies, defendArmies, p2);

            printTerritories();
            sc.nextLine();
            System.out.println("\nWould you like to attack again?(y/n)");
            answer= sc.nextLine();
            
        }while(answer.equals("y"));
        game.nextStage();
    }


    //needs to be cleaned more
    public static void attacking(territory t1, territory t2, int attackArmies, int defendArmies, Player p2){
        Scanner sc = new Scanner(System.in);
        Player p1=game.currentPlayer();
        int t1Deaths=0;
        int t2Deaths=0;
        int numBattles;
        boolean t1Wins=false;

        ArrayList<Integer> dice1=t1.attack(attackArmies);
        ArrayList<Integer> dice2=t2.defend(defendArmies);

        Collections.sort(dice1);
        Collections.sort(dice2);
        Collections.reverse(dice1);
        Collections.reverse(dice2);
        printDice(dice1, dice2, game.currentPlayer(), p2);

        //could be own method?
        if(dice1.size()<dice2.size()){
            numBattles=dice1.size();
        }
        else{
            numBattles=dice2.size();
        }
        for(int i=0;i<numBattles;i++){
            if(dice1.get(i)>dice2.get(i)){
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

        if(!(CreditMgr.undo(game))) {

            t1.deleteArmy(t1Deaths);
            t2.deleteArmy(t2Deaths);

            if (t1Wins) {
                System.out.println(p1.getName() + " won the battle and gained the territory!");
                System.out.println("How many armies would you like to send to " + t2.getName() + "(max " + (t1.armies - 1) + ")");
                int armiesMoving = sc.nextInt();
                while (armiesMoving > t1.armies - 1) {
                    System.out.println("\nYou can enter a max of " + (t1.armies - 1) + " troops, try again");
                    armiesMoving = sc.nextInt();
                }

                moveTroops(armiesMoving, t1, t2);
                t2.setPlayerNum(game.getPlayerTurn());
                t2.setPlayerName(game.players.get(game.getPlayerTurn()).getName());
                p1.addTerritory(t2);
                p2.looseTerritory(t2);

                p1.conquered();

                gainCard(p1);
            }
        }
    }

    //needs to be cleaned more
    public static void fortify(){
        ArrayList<Integer> movedTo= new ArrayList<>();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\n"+game.currentPlayer().getName()+" you may now fortify you territories");
        String answer="";
        
        do{
            int territoryNum=chooseMovers(movedTo);
            int territory2Num=movingTo(territoryNum);

            movedTo.add(territory2Num);
            
            System.out.println("\nHow many troops would you like to move?");
            int troops= sc.nextInt();
            if(!(troops<game.territories.get(territoryNum).numArmies())){
                while(!(troops<game.territories.get(territoryNum).numArmies())){
                    System.out.println("\nYou do not have enough troops to do that, enter another number");
                    troops= sc.nextInt();
                }
            }
            moveTroops(troops, game.territories.get(territoryNum), game.territories.get(territory2Num));
            printTerritories();
            sc.nextLine();



            if(CreditMgr.undo(game)) {
                moveTroops(troops, game.territories.get(territory2Num), game.territories.get(territoryNum));
                printTerritories();
            }

            System.out.println("\nWould you like to move more troops?(y/n)");
            answer= sc.nextLine();
        }while(answer.equals("y"));
        game.nextStage();
        game.nextPlayer();
    }



    public static boolean territoryTaken(int num){return game.territories.get(num).isOccupied();}

    public static boolean playersTerritory(int num){return game.territories.get(num).getPlayerNum()==game.getPlayerTurn();}

    public static void moveTroops(int troops, territory location1, territory location2){
        location1.deleteArmy(troops);
        location2.addArmies(troops);
    }

    public static boolean areNeighbors(int t1, int t2){return game.territories.get(t1).isNeighbor(game.territories.get(t2));}

    public static Player attacked(int num){return game.players.get(game.territories.get(num).getPlayerNum());}

    public static int supportCount(){
        Player p1=game.currentPlayer();

        int armies=(p1.territories.size())/3;
        if(armies<3){armies=3;}
        if(p1.northCount==9){armies+=5;}
        if(p1.southCount==4){armies+=2;}
        if(p1.europeCount==7){armies+=5;}
        if(p1.africaCount==6){armies+=3;}
        if(p1.asiaCount==12){armies+=7;}
        if(p1.australiaCount==4){armies+=2;}

        printTerritories();
        
        return armies;
    }
    
    public static int redeemCards(){
        Scanner sc = new Scanner(System.in);
        int armies=0;
        Player p1=game.currentPlayer();
        
        System.out.println("\nInfantry Cards - "+p1.infantryCount+" | Cavalry Cards - "+p1.cavalryCount+" | Artillery Cards - "+p1.artilleryCount);

        if(p1.infantryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your infantry cards?(y/n)");
            String answer= sc.nextLine();
            if(answer.equals("y")){
                armies+=3;
                p1.useInfantry();
            }
        }

        if(p1.cavalryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your cavalry cards?(y/n)");
            String answer= sc.nextLine();
            if(answer.equals("y")){
                armies+=15;
                p1.useCavalry();
            }
        }

        if(p1.artilleryCount>2){
            sc.nextLine();
            System.out.println("\nWould you like to redeem your artillery cards?(y/n)");
            String answer= sc.nextLine();
            if(answer.equals("y")){
                armies+=30;
                p1.useArtillery();
            }
        }
        return armies;
    }
    
    

    public static int checkTerritory(int territoryNum, int claimed){
        Scanner sc = new Scanner(System.in);
        while (claimed<42 && territoryTaken(territoryNum)||territoryTaken(territoryNum) && !(playersTerritory(territoryNum))) {
            if (territoryTaken(territoryNum) && !(playersTerritory(territoryNum))) {
                System.out.println("Territory is already claimed by other player, please choose a different territory");
            }
            else if (claimed<42 && territoryTaken(territoryNum)) {
                System.out.println("All territories must be claimed before re-enforements can be add, please choose a different territory");
            }
            territoryNum = sc.nextInt();
        }

        return territoryNum;
    }

    public static int attackers(int territoryNum, territory t1){
        Scanner sc= new Scanner(System.in);
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
        return attackArmies;
    }

    public static int defenders(int attackNum, territory t2, Player p2){
        int defendArmies=0;
        Scanner sc= new Scanner(System.in);

        if(t2.numArmies()>1){
            System.out.println("\n"+p2.getName()+" how many armies would you like to defend with?(max 2)");
            defendArmies= sc.nextInt();

            while(defendArmies>2){
                System.out.println("\n"+p2.getName()+" enter 2 or less");
                defendArmies= sc.nextInt();
            }

        }

        else{
            System.out.println("\n"+p2.getName()+" you can defend with only one army");
            defendArmies=1;
        }

        return defendArmies;
    }

    public static void printDice(ArrayList<Integer> dice1, ArrayList<Integer> dice2, Player p1, Player p2){
        System.out.print(p1.getName()+" rolled | ");

        for(int x=0;x<dice1.size();x++){
            System.out.print(dice1.get(x)+" | ");
        }

        System.out.print("\n"+p2.getName()+" rolled | ");

        for(int y=0;y<dice2.size();y++){
            System.out.print(dice2.get(y)+" | ");
        }
    }

    public static void gainCard(Player p1){
        int card = game.deck.getCard();
        if (card == 1) {
            System.out.println("\n" + p1.getName() + " gained an infantry card");
        }

        if (card == 2) {
            System.out.println("\n" + p1.getName() + " gained an cavalry card");
        }

        if (card == 3) {
            System.out.println("\n" + p1.getName() + " gained an artillery card");
        }
        p1.addCard(card);
    }

    public static int chooseMovers(ArrayList<Integer> movedTo){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nChoose territory to move troops from");
        int territoryNum= sc.nextInt();

        while(!playersTerritory(territoryNum)&& movedTo.contains(territoryNum)){
            if(!playersTerritory(territoryNum)) {
                System.out.println("\nPlease choose your own territory");
            }
            else {
                System.out.println("\nYou cannot move troops from a territory you already moved troops too, enter another number");
            }
            territoryNum= sc.nextInt();
        }
        return territoryNum;
    }

    public static int movingTo(int territoryNum){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nChoose territory to move them too");
        int territory2Num= sc.nextInt();

        while(!areNeighbors(territoryNum,territory2Num)&&!playersTerritory(territory2Num)){
            if(!areNeighbors(territoryNum,territory2Num)){
                System.out.println("\nYou can not reach that territory, choose another");
            }
            else if(!playersTerritory(territory2Num)){
                System.out.println("\nYou must move them to a territory you own, choose another");
            }
            territory2Num= sc.nextInt();
        }
        return territory2Num;
    }

    public static void conquered(){
        game.post();
    }
    
    //clean later
    public static void printTerritories(){
        String continent;

        System.out.print("\nTerritories:");
        int i=0;
        while(i<game.getTerritories().size()-1){
            continent=game.getTerritories().get(i).getContinent();
            System.out.println("\n"+continent+":");
            do{
                if(!game.getTerritories().get(i).isOccupied()){
                    System.out.print(i+". "+game.getTerritories().get(i).getName()+ " | ");
                }
                else if(game.getTerritories().get(i).isOccupied()){
                    System.out.print(i+". "+game.getTerritories().get(i).getName()+ " - "+game.getPlayers().get(game.getTerritories().get(i).getPlayerNum()).getName() +"("+game.territories.get(i).numArmies()+") | ");
                }
                i++;
            }while(i<game.getTerritories().size()&&continent==game.getTerritories().get(i).getContinent());
        }
    }

    public static void check(){
        Scanner sc = new Scanner(System.in);
        if(game.stage==1){
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            System.out.println("Do you want to Renforce?");
            game.input=sc.nextLine();
            timer.cancel();
            if(game.input.equals("y")) {
                reEnforce();
            }
            game.input="";
        }
        else if(game.stage==2) {
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            System.out.println("Do you want to Attack?");
            game.input=sc.nextLine();
            timer.cancel();
            if(game.input.equals("y")) {
                attack();
            }
            game.input="";
        }
        else if(game.stage==3){
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            System.out.println("Do you want to Fortify?");
            game.input=sc.nextLine();
            timer.cancel();
            if(game.input.equals("y")) {
                fortify();
            }
            game.input="";
        }
    }

    public static void check(RiskBot myBot){
        if(game.stage==1){
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            game.input = getStringMessage(myBot, "Do you want to reinforce?");
            timer.cancel();
            if(game.input.equals("y")) {
                reEnforce();
            }
            game.input="";
        }
        else if(game.stage==2) {
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            game.input = getStringMessage(myBot, "Do you want to attack?");
            timer.cancel();
            if(game.input.equals("y")) {
                attack();
            }
            game.input="";
        }
        else if(game.stage==3){
            Timer timer = new Timer();
            timer.schedule( task, 30*1000 );
            game.input = getStringMessage(myBot, "Do you want to fortify?");
            timer.cancel();
            if(game.input.equals("y")) {
                fortify();
            }
            game.input="";
        }
    }

    public static void timeOut(){
        System.out.print(game.currentPlayer().getName()+" took to long.");
        game.nextPlayer();
        game.stage=1;
        System.out.println(" Press Enter to start "+game.currentPlayer().getName()+"'s turn");
    }

    public static String getStringMessage(RiskBot bot, String sendMessage){
        String userInput = "";
        if(bot != null){
            do{
                bot.clearMessage();
                bot.sendMessage(sendMessage);

                while(bot.getMessage() == null){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                if(bot.getMessage() != null){
                    try{
                        userInput = bot.getMessage();
                    }catch(Exception e){
                        userInput = "";
                    }
                }

                }while (userInput.isEmpty());
        }

        return userInput;
    }

    public static Integer getIntMessage(RiskBot bot, String sendMessage){
        int userInput = -1;
        if(bot != null){
            do{
                bot.clearMessage();
                bot.sendMessage(sendMessage);

                while(bot.getMessage() == null){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                if(bot.getMessage() != null){
                    try{
                        userInput = Integer.parseInt(bot.getMessage());
                    }catch(Exception e){
                        userInput = -1;
                    }
                }

            }while (userInput == -1);
        }

        return userInput;
    }
}
