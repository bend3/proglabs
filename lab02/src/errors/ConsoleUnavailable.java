package errors;


public class ConsoleUnavailable extends Error{
    @Override
    public String toString() {
        return "Console unavailable";
    }
}
