package exceptions;

public class DirectoryExistsException extends Exception {
    @Override
    public String toString() {
        return "Directory already exist";
    }
}
