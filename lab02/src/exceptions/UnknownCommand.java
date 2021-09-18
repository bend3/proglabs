package exceptions;


public class UnknownCommand extends Exception{
    @Override
    public String toString() {
        return "Unknown command";
    }
}
