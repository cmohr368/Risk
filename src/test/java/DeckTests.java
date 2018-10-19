import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DeckTests {

    @Test
    public void shuffleDeckTest(){
        int deckSize = 42;
        Deck deck= new Deck();
        assertEquals(deck.shuffleDeck().size(), deckSize);
    }

    @Test
    public void getCardTest(){
        Deck deck= new Deck();
        int topCard= deck.getCard();
        if(topCard== 1){
            assertEquals(topCard, 1);
        } else if(topCard== 2){
            assertEquals(topCard, 2);
        } else {
            assertEquals(topCard, 3);
        }
    }
}
