//
//  Giocatore.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 02/06/23.
//

public class Giocatore {
    private Griglia griglia;
    private boolean computer;

    public Giocatore(int dimensione, boolean computer) {
        this.griglia = new Griglia(dimensione);
        this.computer = computer;
    }

    public Griglia getGriglia() {
        return griglia;
    }
}
