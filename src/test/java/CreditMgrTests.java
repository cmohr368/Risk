import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CreditMgrTests {

    @Test
    public void buyCreditTest(){
        Credit wallet= new Credit();
        int amount= 500;
        CreditMgr CreditMgrTest= new CreditMgr();
        CreditMgrTest.buyCredit(wallet, amount);
        int amount1= 500*100;
        assertEquals(wallet.credit, amount1);
    }

}
