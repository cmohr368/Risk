import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class shuffleTest {

    @Test
    public void shuffleTest(){
        int deckSize = 42;
        assertEquals(Risk.shuffleDeck().size(), deckSize);
    }
}
