import java.util.ArrayList;
import java.util.Random;

public class Spielfeld {

    private int bomben = 10;
    private boolean explosion = false;
    private boolean beendet = false;
    private int aufgedeckt = 0;
    private int markierungen = 10;
    private int richtigMarkiert = 0;
    private Zelle[][] zellen = new Zelle[8][8];
    //private BenutzerScnhitStelle scnhitStelle = new BenutzerScnhitStelle(this);

    public void generateSpielFeld() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                zellen[i][j] = new Zelle(false, i, j);
            }
        }

        generateBomben();
    }

    public void generateBomben() {

        Random random = new Random();
        int xKordinate;
        int yKordinate;

        //Generiert Bomben
        for (int i = 0; i < bomben; i++) {

            do {
                xKordinate = random.nextInt(8);
                yKordinate = random.nextInt(8);
            } while (zellen[xKordinate][yKordinate].isBombe());
            zellen[xKordinate][yKordinate].setBombe(true);

            //Zählt die angrenzungen der Bomben an den Nahchbarnfelder
            Zelle[] nachbarsZellen = getNachbarsZellen(xKordinate, yKordinate);
            for (Zelle zelle : nachbarsZellen) {
                if (!zelle.isBombe())
                    zelle.addGrenztAn(1);
            }
        }
    }

    public void generate() {

        Random random = new Random();
        String[] kordinaten = new String[16];
        int xKordinate;
        int yKordinate;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                zellen[i][j] = new Zelle(false, i, j);
            }
        }


        //Generiert Bomben und zählt die angrenzungen der Bomben an den Nahchbarnfelder
        for (int i = 0; i < bomben; i++) {

            do {
                xKordinate = random.nextInt(8);
                yKordinate = random.nextInt(8);
                if (zellen[xKordinate][yKordinate].isBombe()) {
                    System.out.println("!!!!!!!!!!!!!!!! contains");
                }
            } while (zellen[xKordinate][yKordinate].isBombe());

            kordinaten[i] = xKordinate + "" + yKordinate; //speichert die Kordinaten um sicher zu stellen, dass die kordinaten nur einmal erscheinen
            zellen[xKordinate][yKordinate].setBombe(true);

            Zelle[] nachbarsZellen = getNachbarsZellen(xKordinate, yKordinate);

            for (Zelle zelle : nachbarsZellen) {
                if (!zelle.isBombe())
                zelle.addGrenztAn(1);
            }
        }
    }

    public void selectZelle(String[] parts) {

        int xKordinate = 0;
        int yKordinate = 0;
        if (parts.length == 3) {
            xKordinate = Integer.parseInt(parts[1]);
            yKordinate = Integer.parseInt(parts[2]);
        }

        switch (parts[0]) {
            case "m":
                markiereFeld(xKordinate, yKordinate);
                System.out.println(zellen[xKordinate][yKordinate].toString());
                break;
            case "t":
                zellenAufdecken(xKordinate, yKordinate);
                //System.out.println("!!!!!!!!!! getNeighbors: " + getNeighbors(xKordinate, yKordinate));
                System.out.println(zellen[xKordinate][yKordinate].toString());
                break;
            case "e":
                beendet = true;
                break;
        }
    }

    public void markiereFeld(int xKordinate, int yKordinate) {

        if (zellen[xKordinate][yKordinate].isMarkiert() && zellen[xKordinate][yKordinate].isBombe()) {
            zellen[xKordinate][yKordinate].markieren();
            markierungen++;
            richtigMarkiert--;
        } else if (zellen[xKordinate][yKordinate].isMarkiert()) {
            zellen[xKordinate][yKordinate].markieren();
            markierungen++;
        } else if (!zellen[xKordinate][yKordinate].isMarkiert()) {
            if (markierungen > 0) {
                zellen[xKordinate][yKordinate].markieren();
                markierungen--;
                if (zellen[xKordinate][yKordinate].isBombe()) {
                    richtigMarkiert++;
                }
            }
        }
    }

    public void zellenAufdecken(int xKordinate, int yKordinate) {

        if (zellen[xKordinate][yKordinate].isBombe()) {
            explosion = true;
            //System.exit(1);
        } else {
            if (zellen[xKordinate][yKordinate].getGrenztAn() > 0 && !zellen[xKordinate][yKordinate].isAufgedeckt()) {
                zellen[xKordinate][yKordinate].aufdecken();
                aufgedeckt++;
            } else {
                Zelle[] nachbarsZellen = getNachbarsZellen(xKordinate, yKordinate);

                for (Zelle zelle : nachbarsZellen) {
                    //Wenn die zelle keine Bombe ist oder noch nicht aufgedeckt ist und an einer Bombe grenzt
                    //wird diese einfach aufgedeckt
                    if ((!zelle.isBombe() && !zelle.isAufgedeckt()) && zelle.getGrenztAn() != 0) {
                        zelle.aufdecken();
                        aufgedeckt++;
                    }
                    //Ist die Zelle keine Bombe und grenzt an keiner bombe und ist nicht schon aufgedekct
                    //so werden wieder die nachbars zellen aufgedeckt bis es die Zellen an einer Bombe grenzen.
                    //Hier wir die Funktion rekursiv immer wieder aufgerufen solange die Zelle alle Nachbaren
                    //ohne Bombe aufgedeckt hat.
                    if (zelle.getGrenztAn() == 0 && !zelle.isBombe() && !zelle.isAufgedeckt()) {
                        zelle.aufdecken();
                        aufgedeckt++;
                        zellenAufdecken(zelle.getxKordinate(), zelle.getyKoridnate());
                    }
                }
            }
        }
    }

    private Zelle[] getNachbarsZellen(int x, int y) {

        ArrayList<Zelle> nachbarsZellen = new ArrayList<>();

        for (int i = x - 1; i <= x + 1; i++) {
            if (i >= 0 && i < 8) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (j >= 0 && j < 8) {
                        nachbarsZellen.add(zellen[i][j]);
                    }
                }
            }
        }
        return nachbarsZellen.toArray(new Zelle[nachbarsZellen.size()]);
    }


    public Zelle[][] getZellen() {
        return zellen;
    }

    public int getMarkierungen() {
        return markierungen;
    }

    public int getRichtigMarkiert() {
        return richtigMarkiert;
    }

    public int getAufgedeckt() {
        return aufgedeckt;
    }

    public boolean isBeendet() {
        return beendet;
    }

    public boolean isExplosion() {
        return explosion;
    }
}
