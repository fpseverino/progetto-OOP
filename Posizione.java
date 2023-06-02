//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Posizione {
    private final int colonna;
    private final int riga;

    public Posizione(char colonna, int riga) throws IllegalArgumentException {
        if (colonna < 'A' || riga < 1) {
            throw new IllegalArgumentException("Posizione non valida");
        }
        this.colonna = colonna - 'A';
        this.riga = riga - 1;
    }

    public Posizione(int colonna, int riga) throws IllegalArgumentException {
        if (colonna < 0 || riga < 0) {
            throw new IllegalArgumentException("Posizione non valida");
        }
        this.colonna = colonna;
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public int getRiga() {
        return riga;
    }

    public static int decodifica(char colonna) throws IllegalArgumentException {
        if (colonna < 'A') {
            throw new IllegalArgumentException("Posizione non valida");
        }
        return colonna - 'A';
    }

    public static char codifica(int colonna) throws IllegalArgumentException {
        if (colonna < 0) {
            throw new IllegalArgumentException("Posizione non valida");
        }
        return (char) (colonna + 'A');
    }

    public String toString() {
        return codifica(colonna) + "" + (riga + 1);
    }

    // TODO: implementare
    public static boolean dentroGriglia() {
        return true;
    }
}