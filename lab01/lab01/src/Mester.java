public class Mester extends Jatekos{
    private int mesterFokozat;
    public Mester(int mesterFokozat) {
        super();
        this.mesterFokozat = mesterFokozat;
    }
    @Override
    public void lep() {
        System.out.println(mesterFokozat + " kor: " + asztal.getKor());
        asztal.emel(asztal.getTet()*((double) mesterFokozat / 100));
    }
}
