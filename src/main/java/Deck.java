import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Integer> deck;
    Deck(){
        deck=shuffleDeck();
    }

    public ArrayList<Integer> shuffleDeck(){
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

    public int getCard(){

        if(deck.get(0)== 1){
            deck.remove(0);
            return 1;
        }
        else if(deck.get(0)== 2){
            deck.remove(0);
            return 2;
        }
        else{
            deck.remove(0);
            return 3;
        }
    }

    public void setDeck(ArrayList<Integer> deck){
        this.deck=deck;
    }

}
