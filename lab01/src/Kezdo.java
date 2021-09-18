public class Kezdo extends Jatekos{
    private String nev;
    public Kezdo(String nev) {
        super();
        this.nev = nev;
    }

    @Override
    public void lep() {
        System.out.println(this + " kor: " + asztal.getKor());
        if (asztal.getKor() % 2 == 0) {
            asztal.emel(1);
        }
    }

    @Override
    public String toString() {
        return nev;
    }
}
