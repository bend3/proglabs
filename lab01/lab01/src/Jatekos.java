

public abstract class Jatekos {
    protected Asztal asztal;
    public Jatekos() {
    }

    public void lep() {
        System.out.println("kor: " + asztal.getKor() + " tet: " + asztal.getTet());
    }

    public void setAsztal(Asztal a) {
        a.addJatekos(this);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(System.identityHashCode(this) + "" + this);
    }
}
