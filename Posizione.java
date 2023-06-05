//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Posizione {
    private final int colonna;
    private final int riga;
    private String nomeNave;

    public enum Occupazione {
        ACQUA('~'),
        NAVE('O'),
        MANCATA('X'),
        COLPITA('0');

        public final char label;

        Occupazione(char label) {
            this.label = label;
        }

        public char getLabel() {
            return label;
        }
    }

    private Occupazione occupazione;

    public Posizione(int colonna, int riga) throws IllegalArgumentException {
        if (colonna < 0 || riga < 0)
            throw new IllegalArgumentException("Posizione non valida");
        this.colonna = colonna;
        this.riga = riga;
        this.occupazione = Occupazione.ACQUA;
        this.nomeNave = null;
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

    public Occupazione getOccupazione() {
        return occupazione;
    }

    public void setOccupazione(Occupazione occupazione) {
        this.occupazione = occupazione;
    }

    public String getNomeNave() {
        return nomeNave;
    }

    public void setNomeNave(String nomeNave) {
        this.nomeNave = nomeNave;
    }
}