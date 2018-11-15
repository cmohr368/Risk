import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class RiskBot extends TelegramLongPollingBot{

    private String message;
    private long chat_id = 0;


    @Override
    public void onUpdateReceived(Update update){

        /*Check if update has message and message has text*/
        if(update.hasMessage() && update.getMessage().hasText()){
            message = update.getMessage().getText();
            chat_id = update.getMessage().getChatId();
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
        return "726796620:AAGdU-upSVgJHJaQa6jmqtTXMUxUAitbTkg";
    }

    public String getMessage(){
        return message;
    }

    public void sendMessage(String messageToSend){
        while(chat_id == 0){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(messageToSend);
        try{
            execute(message);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void clearMessage(){
        message = null;
    }
}
