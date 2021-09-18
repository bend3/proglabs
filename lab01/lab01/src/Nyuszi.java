
public class Nyuszi extends Jatekos {
    private String color;
    public Nyuszi(String color) {
        super();
        this.color = color;
    }

    @Override
    public void lep() {
        System.out.print(color + " kor: " + asztal.getKor());
        if (asztal.getTet() < 50) {
            asztal.emel(5);
            System.out.println("");
        } else {
            System.out.println(" Húha!");
        }
    }
}
