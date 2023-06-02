//
//  Griglia.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Griglia {
    public static final char ACQUA = '~';
    public static final char NAVE = '☐';
    public static final char COLPITO = '☒';
    public static final char MANCATO = '☸';

    private final int dimensione;
    private char[][] griglia;
    private int numeroNavi = 0;

    public Griglia(int dimensione) {
        this.dimensione = dimensione;
        this.griglia = new char[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                griglia[i][j] = ACQUA;
            }
        }
    }

    public Griglia(char[][] griglia) {
        this.dimensione = griglia.length;
        this.griglia = griglia;
    }

    public int getDimensione() {
        return dimensione;
    }

    public char[][] getGriglia() {
        return griglia;
    }

    public int getNumeroNavi() {
        return numeroNavi;
    }
}
