/**
 * Created by taabaga3 on 01.12.2017.
 */
public class MineSweeper {

    private Spielfeld spielfeld;
    private BenutzerScnhitStelle scnhitStelle;

    public static void main(String[] args) {
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.spielen();
    }

    private void spielen() {
        boolean nochmalSpielen = true;
        while (nochmalSpielen) {
            spielfeld = new Spielfeld();
            scnhitStelle = new BenutzerScnhitStelle(spielfeld);
            spielfeld.generateSpielFeld();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.println("Zelle " + j + ":" + i + " " + spielfeld.getZellen()[i][j].toString());
                }
            }

            scnhitStelle.willkommen();
            while (((spielfeld.getMarkierungen() != 0 || spielfeld.getAufgedeckt() != 54) && !spielfeld.isExplosion()) && !spielfeld.isBeendet()) {
                scnhitStelle.displayFeldLösung();
                System.out.println();
                scnhitStelle.displayFeld();
                scnhitStelle.spielRegeln();
                scnhitStelle.readConsole();
            }
            if (spielfeld.isExplosion()) {
                scnhitStelle.displayFeldLösung();
                scnhitStelle.explodiert();
            } else if (spielfeld.isBeendet()) {
                scnhitStelle.beenden();
            } else {
                scnhitStelle.displayFeld();
                scnhitStelle.gewonnen();
            }

            nochmalSpielen = scnhitStelle.nochmalSpielen();
        }
        //System.out.print(Arrays.deepToString(spielfeld.getZellen()));
    }
}
