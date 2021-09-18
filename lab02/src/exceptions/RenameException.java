package exceptions;


public class RenameException extends Exception {
    @Override
    public String toString() {
        return "Unable to rename file";
    }
}
