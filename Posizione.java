//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Posizione {
    private int colonna;
    private int riga;

    public Posizione(char colonna, int riga) {
        this.colonna = colonna - 'A';
        this.riga = riga - 1;
    }
}