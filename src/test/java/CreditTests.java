import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CreditTests {

    @Test
    public void addCreditTest(){
        Credit credit= new Credit();
        credit.addCredit(5);
        assertEquals(credit.credit, 5);
    }

    @Test
    public void spendCreditTest(){
        Credit credit= new Credit();
        credit.spendCredit(5);
        assertEquals(credit.credit, -5);
    }
}
