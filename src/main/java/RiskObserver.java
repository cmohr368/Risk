
import java.util.Observable;
import java.util.Observer;

public class RiskObserver implements Observer{
    private territory territoryUnderAttack;
    private String playerName;
    @Override
    public void update(Observable o, Object obj){
        territoryUnderAttack = (territory) o;
        playerName = territoryUnderAttack.getPlayerName();
        System.out.println(playerName + " is under attack!");
    }

}
