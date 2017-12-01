import java.util.Scanner;

public class BenutzerScnhitStelle {

    private final Scanner scanner = new Scanner(System.in);

    private Spielfeld spielfeld = new Spielfeld();

    public void willkommen() {
        System.out.println("Willkomen bei Minesweeper");
    }

    public void spielRegeln() {
        System.out.println("Geben Sie ein Kommando ein:");
        System.out.println("t x y (z.B. T 2 3 testet Feld Zeile 2, Spalte 3 auf Mine)");
        System.out.println("m x y (z.B. M 6 1 kehrt Markierung Feld Zeile 6, Spalte 1)");
        System.out.println("Mit 'e' können Sie das Spiel beenden");
    }

    public void beenden() {
        System.out.println("Sie haben das Spiel beendet");
    }

    public void readConsole() {
        String eingabe = scanner.nextLine();
    }

    public void displayFeld() {

    }
}
