
import errors.ConsoleUnavailable;
import exceptions.UnknownCommand;

public class Main {
    public static void main(String[] args) throws Exception {
        ProcessCommands processCommands = new ProcessCommands();


        while (true) {
            try {
                String[] input = Input.getCommand(processCommands.getDirectoryPath());
                processCommands.process(input);
            } catch (UnknownCommand unknownCommand) {
                System.out.println(unknownCommand);
            } catch (ConsoleUnavailable consoleUnavailable){
                System.out.println(consoleUnavailable);
                System.exit(1);
            }
        }
    }
}