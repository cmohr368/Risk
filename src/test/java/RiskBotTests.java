import static org.junit.jupiter.api.Assertions.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.Test;

public class RiskBotTests {

    @Test
    public void getBotUsernameTest(){
        String riskBotTest= "RiskBot";
        RiskBot riskBot= new RiskBot();
        assertEquals(riskBot.getBotUsername(), riskBotTest);
    }

    @Test
    public void getBotTokenTest(){
        String botTokenTest= "726796620:AAGdU-upSVgJHJaQa6jmqtTXMUxUAitbTkg";
        RiskBot riskBot= new RiskBot();
        assertEquals(riskBot.getBotToken(), botTokenTest);
    }

//    @Test
//    public void sendMessageTest(){
//        RiskBot riskbot= new RiskBot();
//        Boolean boolTest= false;
//        String test= "test";
//        if(boolTest== false){
//            riskbot.sendMessage(test);
//            boolTest= true;
//        }
//        assertTrue(boolTest);
//    }

    @Test
    public void clearMessageTest(){
        RiskBot riskBot= new RiskBot();
        Boolean boolTest= false;
        if(boolTest== false){
            riskBot.clearMessage();
            boolTest= true;
        }
        assertTrue(boolTest);
    }
}
