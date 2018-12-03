import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class MainBotTests {

    @Test
    public void moveTroopsTest(){
        territory t1 = new territory("territory1","continent1");
        territory t2 = new territory("territory2","continent2");
        MainBot.moveTroops(3,t1,t2);
        assertEquals(t1.numArmies(),-3);
        assertEquals(t2.numArmies(),3);
    }

    @Test
    public void stringMessageTest(){
        RiskBot bot = null;
        String string = MainBot.getStringMessage(bot);
        assertTrue(string.isEmpty());

    }

    @Test
    public void intMessageTest(){
        RiskBot bot = null;
        int integer = MainBot.getIntMessage(bot);
        assertTrue(integer == -1);

    }

}
