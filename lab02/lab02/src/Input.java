import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import errors.ConsoleUnavailable;

public class Input {
    private static String beforeInput = "Enter command here: ";
    public static String[] getCommand(String... args) throws ConsoleUnavailable, TimeoutException{
        TimerTask timerTask = new TimerTask(){
            int count = 0;
            @Override
            public void run() {
                if (count >= 0) {
                    System.out.println("\nExited due to inactivity");
                    System.exit(124);
                }
                System.out.println("\nPlease enter a command\nEnter command here: ");
                count++;
            }
            
        };
        Timer timer = new Timer("InputTimeout");
        long delay = 30000;
        // if there is no interaction for $delay ms then the program exits
        // if needed it can be easily changed to alert the user a given amoun of times before exiting
        timer.scheduleAtFixedRate(timerTask, delay, delay);
        if (args.length > 0) {
            beforeInput = args[0] + ">";
        } else {
            beforeInput = ">";
        }
        
        Console console = System.console();
        if (console == null) {
            throw new ConsoleUnavailable();
        }
        String splitChar = " ";
        System.out.print(beforeInput);
        String input = System.console().readLine();
        timer.cancel();
        return input.split(splitChar);
    }
}
