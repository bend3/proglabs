package lab03.exceptions;

public class UnknownCommandException extends Exception {
    @Override
    public String toString() {
        return "Unknown command";
    }
}
