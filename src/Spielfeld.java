import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Spielfeld {

    private int bomben = 10;

    private Zelle[][] zellen = new Zelle[8][8];
    private BenutzerScnhitStelle scnhitStelle = new BenutzerScnhitStelle(this);

    public void generate() {

        Random random = new Random();
        int counter = 0;
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
                zelle.addGrenztAn(1);
            }

//            //Für die mitte des Spielfelds
//            if (xKordinate > 0 && yKordinate > 0 && xKordinate < 7 && yKordinate < 7) {
//                for (int j = -1; j < 2; j++) {
//                    for (int k = -1; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Für den oberen Rand
//            } else if (xKordinate > 0 && yKordinate == 0 && xKordinate < 7) {
//                for (int j = -1; j < 2; j++) {
//                    for (int k = 0; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Für den unteren Rand
//            } else if (xKordinate > 0 && yKordinate == 7 && xKordinate < 7) {
//                for (int j = -1; j < 2; j++) {
//                    for (int k = -1; k < 1; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Für den linken Rand
//            } else if (xKordinate == 0 && yKordinate > 0 && yKordinate < 7) {
//                for (int j = 0; j < 2; j++) {
//                    for (int k = -1; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Für den rechten Rand
//            } else if (xKordinate == 7 && yKordinate > 0 && yKordinate < 7) {
//                for (int j = -1; j < 1; j++) {
//                    for (int k = -1; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Oben links
//            } else if (xKordinate == 0 && yKordinate == 0) {
//                for (int j = 0; j < 2; j++) {
//                    for (int k = 0; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Oben rechts
//            } else if (xKordinate == 7 && yKordinate == 0) {
//                for (int j = -1; j < 1; j++) {
//                    for (int k = 0; k < 2; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Unten rechts
//            } else if (xKordinate == 7 && yKordinate == 7) {
//                for (int j = -1; j < 1; j++) {
//                    for (int k = -1; k < 1; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            //Unten links
//            } else if (xKordinate == 0 && yKordinate == 7) {
//                for (int j = 0; j < 2; j++) {
//                    for (int k = -1; k < 1; k++) {
//                        zellen[xKordinate + j][yKordinate + k].addGrenztAn(1);
//                    }
//                }
//            }

        }
    }

    public void selechtZelle(String[] parts) {

        int xKordinate = 0;
        int yKordinate = 0;
        if (parts.length == 3) {
            xKordinate = Integer.parseInt(parts[1]);
            yKordinate = Integer.parseInt(parts[2]);
        }
        switch (parts[0]) {
            case "m":
                zellen[xKordinate][yKordinate].markieren();
                System.out.println(zellen[xKordinate][yKordinate].toString());
                break;
            case "t":
                zellenAufdecken(xKordinate, yKordinate);
                //System.out.println("!!!!!!!!!! getNeighbors: " + getNeighbors(xKordinate, yKordinate));
                System.out.println(zellen[xKordinate][yKordinate].toString());
                break;
            case "e":
                System.exit(1);
        }
    }

    public void zellenAufdecken(int xKordinate, int yKordinate) {

        if (zellen[xKordinate][yKordinate].isBombe()) {
            scnhitStelle.beenden();
            System.exit(1);
        }

        if (zellen[xKordinate][yKordinate].getGrenztAn() > 0) {
            zellen[xKordinate][yKordinate].aufdecken();
        } else {

            Zelle[] nachbarsZellen = getNachbarsZellen(xKordinate, yKordinate);

            for (Zelle zelle : nachbarsZellen) {
                //Wenn die zelle keine Bombe ist oder noch nicht aufgedeckt ist und an einer Bombe grenzt
                //wird diese einfach aufgedeckt
                if ((!zelle.isBombe() || !zelle.isAufgedeckt()) && zelle.getGrenztAn() != 0) {
                    zelle.aufdecken();
                }
                //Ist die Zelle keine Bombe und grenzt an keiner bombe und ist nicht schon aufgedekct
                //so werden wieder die nachbars zellen aufgedeckt bis es die Zellen an einer Bombe grenzen.
                //Hier wir die Funktion rekursiv immer wieder aufgerufen solange die Zelle alle Nachbaren
                //ohne Bombe aufgedeckt hat.
                if (zelle.getGrenztAn() == 0 && !zelle.isBombe() && !zelle.isAufgedeckt()) {
                    zelle.aufdecken();
                    zellenAufdecken(zelle.getxKordinate(), zelle.getyKoridnate());
                }
            }
        }
    }

    public Zelle[] getNachbarsZellen(int x, int y) {

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

//    public Zelle[] getNachbarsZellen(int xKordinate, int yKordinate) {
//
//        List<Zelle> nachbarsZellen = new ArrayList<>();
//        //Zelle[] nachbarsZellen;
//
//        //Für die mitte des Spielfelds
//        if (xKordinate > 0 && yKordinate > 0 && xKordinate < 7 && yKordinate < 7) {
//            for (int j = -1; j < 2; j++) {
//                for (int k = -1; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Für den oberen Rand
//        } else if (xKordinate > 0 && yKordinate == 0 && xKordinate < 7) {
//            for (int j = -1; j < 2; j++) {
//                for (int k = 0; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Für den unteren Rand
//        } else if (xKordinate > 0 && yKordinate == 7 && xKordinate < 7) {
//            for (int j = -1; j < 2; j++) {
//                for (int k = -1; k < 1; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Für den linken Rand
//        } else if (xKordinate == 0 && yKordinate > 0 && yKordinate < 7) {
//            for (int j = 0; j < 2; j++) {
//                for (int k = -1; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Für den rechten Rand
//        } else if (xKordinate == 7 && yKordinate > 0 && yKordinate < 7) {
//            for (int j = -1; j < 1; j++) {
//                for (int k = -1; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Oben links
//        } else if (xKordinate == 0 && yKordinate == 0) {
//            for (int j = 0; j < 2; j++) {
//                for (int k = 0; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Oben rechts
//        } else if (xKordinate == 7 && yKordinate == 0) {
//            for (int j = -1; j < 1; j++) {
//                for (int k = 0; k < 2; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Unten rechts
//        } else if (xKordinate == 7 && yKordinate == 7) {
//            for (int j = -1; j < 1; j++) {
//                for (int k = -1; k < 1; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//            //Unten links
//        } else if (xKordinate == 0 && yKordinate == 7) {
//            for (int j = 0; j < 2; j++) {
//                for (int k = -1; k < 1; k++) {
//                    nachbarsZellen.add(zellen[xKordinate + j][yKordinate + k]);
//                }
//            }
//        }
//
//        return nachbarsZellen.toArray(new Zelle[nachbarsZellen.size()]);
//    }

    public Zelle[][] getZellen() {
        return zellen;
    }
}
