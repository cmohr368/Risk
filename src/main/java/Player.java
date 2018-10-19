
import java.util.ArrayList;
import java.util.List;

class Player {
    String name;
    ArrayList<territory> territories;
    int unPlacedArmies;
    int territoriesConquered;
    
    int northCount;
    int europeCount;
    int southCount;
    int africaCount;
    int asiaCount;
    int australiaCount;
    
    int infantryCount;
    int cavalryCount;
    int artilleryCount;

    Credit wallet;
    int undos;

    
    Player(String a, int b){
        name=a;
        unPlacedArmies=b;
        territories = new ArrayList<>();



        northCount=0;
        europeCount=0;
        southCount=0;
        africaCount=0;
        asiaCount=0;
        australiaCount=0;
         
        infantryCount=0;
        cavalryCount=0;
        artilleryCount=0;

        territoriesConquered=0;

        wallet=new Credit();
        undos=0;
    }
    
    public String getName(){
        return name;
    }
    
    public void addCard(int card){
        if(card==1){infantryCount++;}
        else if(card==2){cavalryCount++;}
        else{artilleryCount++;}
    }
    
    public void addTerritory(territory a){
        territories.add(a);
        if(a.continent=="North America"){
            northCount++;
        }
        if(a.continent=="Europe"){   
            europeCount++;
        }
        if(a.continent=="South America"){   
            southCount++;
        }
        if(a.continent=="Africa"){   
            africaCount++;
        }
        if(a.continent=="Asia"){   
            asiaCount++;
        }
        if(a.continent=="Australia"){   
            australiaCount++;
        }
    }
    public void looseTerritory(territory a){
        territories.remove(a);
        if(a.continent=="North America"){
            northCount--;
        }
        if(a.continent=="Europe"){   
            europeCount--;
        }
        if(a.continent=="South America"){   
            southCount--;
        }
        if(a.continent=="Africa"){   
            africaCount--;
        }
        if(a.continent=="Asia"){   
            asiaCount--;
        }
        if(a.continent=="Australia"){   
            australiaCount--;
        }
    }

    public void conquered(){territoriesConquered++;}
    
    public void placeArmy(){
        unPlacedArmies--;
    }
    public int unplacedArmies(){
        return unPlacedArmies;
    }
    
    public ArrayList<territory> getTerritories() {
        return territories;
    }

    public void useCavalry() {
        cavalryCount-=3;
    }

    public void useArtillery() {
        cavalryCount-=3;
    }

    public void useInfantry() {cavalryCount-=3;}

    public void addUndos(int amount) {
        undos+=amount;
    }
    public void useUndo(){undos--;}
}
