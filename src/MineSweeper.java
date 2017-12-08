/**
 * Created by taabaga3 on 01.12.2017.
 */
public class MineSweeper {

    public static void main(String[] args) {
        boolean nochmalSpielen = true;
        while (nochmalSpielen) {
            Spielfeld spielfeld = new Spielfeld();
            BenutzerScnhitStelle scnhitStelle = new BenutzerScnhitStelle(spielfeld);
            spielfeld.generate();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.println("Zelle " + j + ":" + i + " " + spielfeld.getZellen()[i][j].toString());
                }
            }
            scnhitStelle.willkommen();
            while (((spielfeld.getRichtigMarkiert() != 10 || spielfeld.getAufgedeckt() != 54) && !spielfeld.isExplosion()) && !spielfeld.isBeendet()) {
                scnhitStelle.displayFeldDev();
                System.out.println();
                scnhitStelle.displayFeld();
                scnhitStelle.spielRegeln();
                scnhitStelle.readConsole();
            }
            if (spielfeld.isExplosion()) {
                scnhitStelle.explodiert();
            } else if (spielfeld.isBeendet()) {
                scnhitStelle.beenden();
            } else {
                scnhitStelle.gewonnen();
            }

            nochmalSpielen = scnhitStelle.nochmalSpielen();
        }
        //System.out.print(Arrays.deepToString(spielfeld.getZellen()));
    }
}
