import java.util.Timer;
import java.util.TimerTask;

public class Time{
    static Game game;

    public TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( game.input.equals("") )
            {
                Risk.timeOut();
            }
        }
    };

    public java.util.Timer time = new java.util.Timer();



    Time(Game game){
        this.game=game;

    }

    public void startTimer(){
        time.schedule( task, 30*1000 );
    }
    public void cancelTimer(){
        time.cancel();
    }

}