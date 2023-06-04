//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Posizione {
    private final int colonna;
    private final int riga;
    private String tipoNave;

    public enum Casella {
        ACQUA('~'),
        NAVE('O'),
        MANCATA('X'),
        COLPITA('0');

        public final char label;

        Casella(char label) {
            this.label = label;
        }

        public char getLabel() {
            return label;
        }
    }

    private Casella casella;

    public Posizione(int colonna, int riga) throws IllegalArgumentException {
        if (colonna < 0 || riga < 0)
            throw new IllegalArgumentException("Posizione non valida");
        this.colonna = colonna;
        this.riga = riga;
        this.casella = Casella.ACQUA;
        this.tipoNave = null;
    }

    public Posizione(char colonna, int riga) throws IllegalArgumentException {
        this(Character.toUpperCase(colonna) - 'A', riga - 1);
    }

    public int getColonna() {
        return colonna;
    }

    public int getRiga() {
        return riga;
    }

    public Casella getCasella() {
        return casella;
    }

    public void setCasella(Casella casella) {
        this.casella = casella;
    }

    public String getTipoNave() {
        return tipoNave;
    }

    public void setTipoNave(String tipoNave) {
        this.tipoNave = tipoNave;
    }
}