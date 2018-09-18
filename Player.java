package risk;

import java.util.ArrayList;

class Player {
    String name;
    ArrayList<territory> territories;
    ArrayList<Card> cards;
    int unPlacedArmies;
    
    int northACount;
    int europeCount;
    int southACount;
    int africaCount;
    int asiaCount;
    int australiaCount;
    
    Player(String a, int b){
        name=a;
        unPlacedArmies=b;
        territories = new ArrayList<>();
        cards = new ArrayList<>();
         northACount=0;
         europeCount=0;
         southACount=0;
         africaCount=0;
         asiaCount=0;
         australiaCount=0;
    }
    
    public String getName(){
        return name;
    }
    
    public void addTerritory(territory a){
        territories.add(a);
    }
    public void looseTerritory(territory a){
        territories.remove(a);
    }
    public void addCard(Card a){
        cards.add(a);
    }
    public void useCard(Card a){
        a.use();
        cards.remove(a);
    }
    public void placeArmy(){
        unPlacedArmies--;
    }
    public int unplacedArmies(){
        return unPlacedArmies;
    }
    
}
