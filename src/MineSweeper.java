/**
 * Created by taabaga3 on 01.12.2017.
 */
public class MineSweeper {

    public static void main(String[] args) {
        Spielfeld spielfeld = new Spielfeld();
        BenutzerScnhitStelle scnhitStelle = new BenutzerScnhitStelle(spielfeld);
        spielfeld.generate();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println("Zelle " + j + ":" + i + " " + spielfeld.getZellen()[i][j].toString());
            }
        }
        scnhitStelle.willkommen();
        scnhitStelle.displayFeldDev();
        System.out.println();
        scnhitStelle.displayFeld();
        while (true) {
            scnhitStelle.spielRegeln();
            scnhitStelle.readConsole();
            scnhitStelle.displayFeld();
        }
        //System.out.print(Arrays.deepToString(spielfeld.getZellen()));
    }
}
