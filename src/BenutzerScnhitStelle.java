import java.util.Arrays;
import java.util.Scanner;

public class BenutzerScnhitStelle {

    private final Scanner scanner = new Scanner(System.in);
    private Spielfeld spielfeld;

    public BenutzerScnhitStelle(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public void willkommen() {
        System.out.println("Willkomen bei Minesweeper");
    }

    public void spielRegeln() {
        System.out.println("Geben Sie ein Kommando ein:");
        System.out.println("t x y (z.B. t 2 3 testet Feld Spalte 2, Zeile 3 auf Mine)");
        System.out.println("m x y (z.B. m 6 1 markiert Feld in der Spalte 6 und Zeile 1)");
        System.out.println("Mit 'e' können Sie das Spiel beenden");
    }

    public void explodiert() {
        System.out.println("Sie haben eine Miene aufgedeckt");
    }

    public void gewonnen() {
        System.out.println("Sie haben das Spiel gewonnen !!!!");
    }

    public void beenden() {
        System.out.println("Sie haben das Spiel beendet");
    }

    public boolean nochmalSpielen() {
        boolean antwort = false;
        System.out.println("Wollen sie noch mal Spielen? y/n");
        String eingabe = scanner.nextLine();
        if (eingabe.equals("y") || eingabe.equals("Y")) {
            antwort = true;
        } else if (eingabe.equals("n") || eingabe.equals("N")) {
            antwort = false;
        }
        return antwort;
    }

    public void readConsole() {

        String eingabe = scanner.nextLine();
        String[] args = eingabe.split(" ");

        String meldung = validate(args);
        while (!meldung.equals("") ) {
            System.out.println(meldung);
            spielRegeln();
            eingabe = scanner.nextLine();
            args = eingabe.split(" ");
            meldung = validate(args);
        }
        System.out.print(Arrays.asList(args).toString());
        spielfeld.selectZelle(args);
    }

    public String validate(String[] args) {

        String eingabe = "";

        try {
            if (args.length == 3) {
                if (args[0].equals("t") || args[0].equals("m")) {
                    if (Integer.parseInt(args[1]) > 8
                            || Integer.parseInt(args[1]) < 0 || Integer.parseInt(args[2]) > 8 || Integer.parseInt(args[2]) < 0) {
                        eingabe = "!!!!Eingabe nicht gültig!!!!";
                    }
                }
            } else if (args[0].equals("e")) {
                eingabe = "";
            } else {
                eingabe = "!!!!Eingabe nicht gültig!!!!";
            }
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            eingabe = "!!!!Eingabe nicht gültig!!!!";
        }
        return eingabe;
    }

    public void displayFeldLösung() {

        System.out.print(" ");
        for (int i = 0; i < 8; i++) {
            System.out.print("  " + i);
        }

        System.out.println();
        int counter = 0;

        for (int i = 0; i < 8; i++) {
            System.out.print(i + "  ");
            //X und Y achsen verkeht bei dieser For literation
            for (int j = 0; j < 8; j++) {
                if (spielfeld.getZellen()[j][i].isBombe()) {
                    System.out.print("*  ");
                    counter++;
                } else {
                    System.out.print(spielfeld.getZellen()[j][i].getGrenztAn() + "  ");
                }
                //spielfeld.getZellen()[i][j];
            }
            System.out.println();
        }
        System.out.println();

    }

    public void displayFeld() {

        System.out.print(" ");
        for (int i = 0; i < 8; i++) {
            System.out.print("  " + i);
        }

        System.out.println();
        int counter = 0;

        for (int i = 0; i < 8; i++) {
            System.out.print(i + "  ");
            //X und Y achsen verkeht bei dieser For literation
            for (int j = 0; j < 8; j++) {
                if (spielfeld.getZellen()[j][i].isMarkiert()) {
                    System.out.print("!  ");
                } else if (spielfeld.getZellen()[j][i].isAufgedeckt()) {
                    System.out.print(spielfeld.getZellen()[j][i].getGrenztAn() + "  ");
                } else {
                    System.out.print("   ");
                }
                //spielfeld.getZellen()[i][j];
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Markierung übrig: " + spielfeld.getMarkierungen());
        //System.out.println("Richtig Markiert: " + spielfeld.getRichtigMarkiert());

    }
}
