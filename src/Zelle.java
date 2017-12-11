public class Zelle {

    private boolean markiert;
    private boolean bombe;
    private boolean aufgedeckt;
    private int xKordinate;
    private int yKoridnate;
    private int grenztAn = 0;

    public Zelle(boolean bombe, int xKordinate, int yKoridnate) {
        this.bombe = bombe;
        this.xKordinate = xKordinate;
        this.yKoridnate = yKoridnate;
    }

    public void aufdecken() {
        this.aufgedeckt = true;
    }

    public void markieren() {
        if (this.markiert) {
            this.markiert = false;
        } else {
            this.markiert = true;
        }
    }

    public boolean isMarkiert() {
        return markiert;
    }

    public boolean isBombe() {
        return bombe;
    }

    public boolean isAufgedeckt() {
        return aufgedeckt;
    }

    public int getGrenztAn() {
        return grenztAn;
    }

    public int getXKordinate() {
        return xKordinate;
    }

    public int getYKoridnate() {
        return yKoridnate;
    }

    public void setBombe(boolean bombe) {
        this.bombe = bombe;
    }

    public void addGrenztAn(int grenztAn) {
        this.grenztAn += grenztAn;
    }

    @Override
    public String toString() {
        return "Zelle{" +
                "markiert=" + markiert +
                ", bombe=" + bombe +
                ", aufgedeckt=" + aufgedeckt +
                ", xKordinate=" + xKordinate +
                ", yKoridnate=" + yKoridnate +
                ", grenztAn=" + grenztAn +
                '}';
    }
}
