import java.util.ArrayList;
import java.util.Scanner;

public class CreditMgr {

    public static void buyCredit(Credit wallet, int amount){
        wallet.addCredit(amount*100);
    }

    public static void buyCard(Game game,boolean infantry, boolean calvary, boolean artillery){
        Scanner sc = new Scanner(System.in);
        if(infantry){
            game.currentPlayer().wallet.spendCredit(100);
            game.currentPlayer().addCard(1);
        }

        if(calvary){
            game.currentPlayer().wallet.spendCredit(200);
            game.currentPlayer().addCard(2);
        }

        if(artillery){
            game.currentPlayer().wallet.spendCredit(300);
            game.currentPlayer().addCard(3);
        }
    }

    public static void buyUndo(Game game, int amount){

        game.currentPlayer().addUndos(amount);
        game.currentPlayer().wallet.spendCredit(amount*100);
    }



    public static void transferCredit(Game game, Player temp, int amount){
        transfer(game, temp, amount);
    }



    public static Player findPlayer(Game game, String name){
        Player temp=game.currentPlayer();
        for(int i=0; i<game.numPlayers;i++){
            if(game.players.get(i).name.equals(name)){temp= game.players.get(i);}
        }
        return temp;
    }
    public static void transfer(Game game,Player p2, int amount){
        game.currentPlayer().wallet.spendCredit(amount);
        p2.wallet.spendCredit(amount);
    }
    public static boolean checkCredit(Game game, int amount){
        if(game.currentPlayer().wallet.credit>=amount) {
            return true;
        }
        return false;
    }
    public static String printCredit(Game game){return "(current credit: "+game.currentPlayer().wallet.credit+" )";}


    public static void buying(Game game, RiskBot myBot){
        myBot.sendMessage("Would you like to purchase credit? (y/n)");
        String answer=MainBot.getStringMessage(myBot);
        if(answer.equals("y")){
            buyCredit(game.currentPlayer().wallet, myBot);
        }

        if(checkCredit(game, 100)){
            myBot.sendMessage("Would you like to purchase any cards?(y/n)");
            answer=MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                buyCard(game, myBot);
            }
        }
        if(checkCredit(game, 100)){
            myBot.sendMessage("Would you like to purchase any undos?(y/n)");
            answer=MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                buyUndo(game, myBot);
            }
        }
        if(checkCredit(game, 0)){
            myBot.sendMessage("Would you like to transfer any credit?(y/n)");
            answer=MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                transferCredit(game, myBot);
            }
        }
    }

    public static void buyCredit(Credit wallet, RiskBot myBot){
        myBot.sendMessage("How much credit would you like to purchase? (100 credits per $1)");
        int amount=MainBot.getIntMessage(myBot);
        wallet.addCredit(amount*100);
    }

    public static void buyCard(Game game, RiskBot myBot){
        if(checkCredit(game, 100)){
            myBot.sendMessage("\nWould you like to buy a infantry card?(y/n) (100 credits) "+printCredit(game));
            String answer= MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(100);
                game.currentPlayer().addCard(1);
            }
        }

        if(checkCredit(game, 200)){
            myBot.sendMessage("\nWould you like to buy a calvary card?(y/n) (200 credits) "+printCredit(game));
            String answer= MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(200);
                game.currentPlayer().addCard(2);
            }
        }

        if(checkCredit(game, 300)){
            myBot.sendMessage("\nWould you like to buy a artillery card?(y/n) (300 credits) "+printCredit(game));
            String answer= MainBot.getStringMessage(myBot);
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(300);
                game.currentPlayer().addCard(3);
            }
        }
    }

    public static void buyUndo(Game game, RiskBot myBot){
        myBot.sendMessage("How many undos would you like to purchase(100 credits each) "+printCredit(game));
        int amount=MainBot.getIntMessage(myBot);
        while(amount*100>game.currentPlayer().wallet.credit){
            myBot.sendMessage("You can not purchase that much, try again");
            amount=MainBot.getIntMessage(myBot);
        }
        game.currentPlayer().addUndos(amount);
        game.currentPlayer().wallet.spendCredit(amount*100);
    }

    public static void transferCredit(Game game, RiskBot myBot){
        myBot.sendMessage("What player would you like to send credit to?");
        String name=MainBot.getStringMessage(myBot);

        Player temp=findPlayer(game, name);

        myBot.sendMessage("How much credit would like to send? "+printCredit(game));
        int amount=MainBot.getIntMessage(myBot);
        transfer(game, temp, amount);
    }

    public static boolean undo(Game game, RiskBot myBot){
        if(game.currentPlayer().undos!=0){
            myBot.sendMessage("Would you like to use your undo? (y/n)");
            String answer=MainBot.getStringMessage(myBot);
            if(answer.equals("y")) {
                game.currentPlayer().useUndo();
                return true;
            }
        }
        return false;

    }
}
