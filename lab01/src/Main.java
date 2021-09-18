public class Main {
    public static void main(String[] args){
        Asztal asztal = new Asztal();
        asztal.ujJatek();
        try {
            asztal.kor();    
        } catch (NincsJatekos e) {
            System.out.println("Anyád faszáért akarsz 0 játékossal játszani");
        };
        new Kezdo("Tomi").setAsztal(asztal);
        new Kezdo("Yomama").setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Mester(15).setAsztal(asztal);
        new Nyuszi("kék").setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        new Robot().setAsztal(asztal);
        asztal.ujJatek();
        
        
        for (int i = 0; i < 100; i++) {
            try {
            asztal.kor();    
            } catch (NincsJatekos e) {
                System.out.println("Anyád faszáért akarsz 0 játékossal játszani");
            };
        }
        asztal = new Asztal();
        System.gc();
    }
}
