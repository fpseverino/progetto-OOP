//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave {
    private final String tipo;
    private final int dimensione;
    private Posizione posizione;
    private Direzione direzione;

    public Nave(String tipo, int dimensione) {
        this.tipo = tipo;
        this.dimensione = dimensione;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDimensione() {
        return dimensione;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public Direzione getDirezione() {
        return direzione;
    }

    public void setDirezione(Direzione direzione) {
        this.direzione = direzione;
    }
}
