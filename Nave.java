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
        this.posizione = null;
        this.direzione = null;
    }

    public Nave(String tipo, int dimensione, Posizione posizione, Direzione direzione) {
        this.tipo = tipo;
        this.dimensione = dimensione;
        this.posizione = posizione;
        this.direzione = direzione;
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

    public String grafica() {
        StringBuilder grafica = new StringBuilder();
        for (int i = 0; i < dimensione; i++) {
            grafica.append(Griglia.NAVE);
        }
        return grafica.toString();
    }

    public String toString() {
        return tipo + " (" + dimensione + ")";
    }
}
