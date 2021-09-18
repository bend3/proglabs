import java.util.Random;

class Asztal{
    private Jatekos[] jatekosok;
    private double tet;
    private int kor;
    private double goal;

    private int jatekosokSzama() {
        int res = 0;
        for (int i = 0; i < jatekosok.length; i++) {
            if (jatekosok[i] != null) {
                res++;
            }
        }
        return res;
    }
    
    public Asztal() {
        jatekosok = new Jatekos[10];
        tet = 0;
        kor = 0;
        goal = new Random().nextDouble()*100;
    }

    public void ujJatek() {
        tet = 0;
        kor = 0;
        goal = new Random().nextDouble()*100;
    }

    public void addJatekos(Jatekos j) {
        boolean vanHely = jatekosokSzama() < jatekosok.length;
        if (!vanHely) {
            System.out.println("Az asztal betelt fuck your mom");
            return;
        }
        insert: for (int i = 0; i < jatekosok.length; i++) {
            if (jatekosok[i] == null) {
                jatekosok[i] = j;
                j.asztal = this;
                break insert;
            }
        }
    }

    public int getKor() {
        return kor;
    }

    public void emel(double d) {
        tet += d;
    }

    public double getTet() {
        return tet;
    }

    public void kor() throws NincsJatekos{
        if (jatekosokSzama() == 0) {
            throw new NincsJatekos();
        }
        if (tet > goal) {
            System.out.println("Vége a játéknak, yo mama so fat she invented gravity");
            return;
        }
        loop: for (int i = 0; i < jatekosok.length; i++) {
            if (jatekosok[i] != null) {
                jatekosok[i].lep();
                if (goal < tet) {
                    if (tet < goal * 1.1) {
                        System.out.println("jatekos " + i +" nyert");
                    } else {
                        System.out.println("yomama nyert");
                    }

                    break loop;
                }
            }
        }
        kor++;

    }
}