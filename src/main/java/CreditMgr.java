import java.util.ArrayList;
import java.util.Scanner;

public class CreditMgr {
    public static void buying(Game game){
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to purchase credit? (y/n)");
        String answer=sc.nextLine();
        if(answer.equals("y")){
            buyCredit(game.currentPlayer().wallet);
        }

        if(checkCredit(game, 100)){
            System.out.println("Would you like to purchase any cards?(y/n)");
            answer=sc.nextLine();
            if(answer.equals("y")){
                buyCard(game);
            }
        }
        if(checkCredit(game, 100)){
            System.out.println("Would you like to purchase any undos?(y/n)");
            answer=sc.nextLine();
            if(answer.equals("y")){
                buyUndo(game);
            }
        }
        if(checkCredit(game, 0)){
            System.out.println("Would you like to transfer any credit?(y/n)");
            answer=sc.nextLine();
            if(answer.equals("y")){
                transferCredit(game);
            }
        }
    }

    public static void buyCredit(Credit wallet){
        Scanner sc = new Scanner(System.in);
        System.out.println("How much credit would you like to purchase? (100 credits per $1)");
        int amount=sc.nextInt();
        wallet.addCredit(amount*100);
    }

    public static void buyCard(Game game){
        Scanner sc = new Scanner(System.in);
        if(checkCredit(game, 100)){
            System.out.println("\nWould you like to buy a infantry card?(y/n) (100 credits) "+printCredit(game));
            String answer= sc.nextLine();
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(100);
                game.currentPlayer().addCard(1);
            }
        }

        if(checkCredit(game, 200)){
            System.out.println("\nWould you like to buy a calvary card?(y/n) (200 credits) "+printCredit(game));
            String answer= sc.nextLine();
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(200);
                game.currentPlayer().addCard(2);
            }
        }

        if(checkCredit(game, 300)){
            System.out.println("\nWould you like to buy a artillery card?(y/n) (300 credits) "+printCredit(game));
            String answer= sc.nextLine();
            if(answer.equals("y")){
                game.currentPlayer().wallet.spendCredit(300);
                game.currentPlayer().addCard(3);
            }
        }
    }

    public static void buyUndo(Game game){
        Scanner sc = new Scanner(System.in);
        System.out.println("How many undos would you like to purchase(100 credits each) "+printCredit(game));
        int amount=sc.nextInt();
        while(amount*100>game.currentPlayer().wallet.credit){
            System.out.println("You can not purchase that much, try again");
            amount=sc.nextInt();
        }
        game.currentPlayer().addUndos(amount);
        game.currentPlayer().wallet.spendCredit(amount*100);
    }

    public static void transferCredit(Game game){
        Scanner sc = new Scanner(System.in);
        System.out.println("What player would you like to send credit to?");
        String name=sc.nextLine();

        Player temp=findPlayer(game, name);

        System.out.println("How much credit would like to send? "+printCredit(game));
        int amount=sc.nextInt();
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

    public static boolean undo(Game game){
        Scanner sc = new Scanner(System.in);
        if(game.currentPlayer().undos!=0){
            System.out.println("Would you like to use your undo? (y/n)");
            String answer=sc.nextLine();
            if(answer.equals("y")) {
                game.currentPlayer().useUndo();
                return true;
            }
        }
        return false;

    }
}
