public class Robot extends Jatekos {
    int id;

    static private int robotId = 0;
    public Robot() {
        super();
        this.id = robotId++;
    }

    @Override
    public void lep() {
        System.out.println(this + " kor: " + asztal.getKor());
    }

    @Override
    public String toString() {
        return "Robot" + id;
    }
}
