import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class RiskBot extends TelegramLongPollingBot{


    protected RiskBot(DefaultBotOptions options){
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update){

        /*Check if update has message and message has text*/
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage sendMessage = new SendMessage()
                    .setChatId(chat_id)
                    .setText(message);


            try{
                execute(sendMessage);
            } catch(TelegramApiException ex){
                ex.printStackTrace();
            }

        }
    }

    @Override
    public String getBotUsername(){
        //TODO
        return "RiskBot";
    }

    @Override
    public String getBotToken(){
        //TODO
        return "aaaaaaaaa";
    }

    public Scanner getScanner(String message){
        Scanner sc = new Scanner(message);
        return sc;
    }
}
