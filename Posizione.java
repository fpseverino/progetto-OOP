//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Posizione {
    private final int colonna;
    private final int riga;

    public enum Occupazione {
        ACQUA,
        NAVE,
        MANCATO,
        COLPITO;

        private String tipoNave;
    }

    private Occupazione occupazione;

    public Posizione(int colonna, int riga) throws IllegalArgumentException {
        if (colonna < 0 || riga < 0)
            throw new IllegalArgumentException("Posizione non valida");
        this.colonna = colonna;
        this.riga = riga;
        this.occupazione = Occupazione.ACQUA;
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

    public String getTipoNave() throws IllegalStateException {
        if (this.occupazione == Occupazione.ACQUA || this.occupazione == Occupazione.MANCATO)
            throw new IllegalStateException("Posizione non occupata da una nave");
        return occupazione.tipoNave;
    }

    public void setTipoNave(String tipoNave) throws IllegalStateException {
        if (this.occupazione == Occupazione.ACQUA || this.occupazione == Occupazione.MANCATO)
            throw new IllegalStateException("Posizione non occupata da una nave");
        this.occupazione.tipoNave = tipoNave;
    }
}