package lab03.exceptions;

public class InvalidCommandException extends Exception {
    @Override
    public void printStackTrace() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Invalid Command";
    }
}
