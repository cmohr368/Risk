import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class shuffleTest {

    @Test
    public void shuffleTest(){
        int deckSize = 42;
        Deck deck= new Deck();
        assertEquals(deck.shuffleDeck().size(), deckSize);
    }
}
