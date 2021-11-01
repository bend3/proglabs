package swingmvclab;

public class NeptunExistsException extends Exception {
    @Override
    public String toString() {
        return "Student with this Neptun code already exists";
        //        return super.toString();
    }
}
