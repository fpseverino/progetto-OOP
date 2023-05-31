//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave {
    private String tipo;
    private int dimensione;
    private Posizione posizione;
    private boolean orientamentoOrizzontale;
    private boolean affondata;

    public Nave(String tipo, int dimensione, Posizione posizione, boolean orientamentoOrizzontale) {
        this.tipo = tipo;
        this.dimensione = dimensione;
        this.posizione = posizione;
        this.orientamentoOrizzontale = orientamentoOrizzontale;
        this.affondata = false;
    }
}
