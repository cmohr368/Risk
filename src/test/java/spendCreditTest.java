import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class spendCreditTest {

    @Test
    public void spendCreditTest(){
        Credit credit= new Credit();
        credit.spendCredit(5);
        assertEquals(credit.credit, -5);
    }
}
