//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave {
    private final String tipo;
    private final int dimensione;
    private Posizione posizioneIniziale;
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

    public Posizione getPosizioneIniziale() {
        return posizioneIniziale;
    }

    public void setPosizioneIniziale(Posizione posizioneIniziale) {
        this.posizioneIniziale = posizioneIniziale;
    }

    public Direzione getDirezione() {
        return direzione;
    }

    public void setDirezione(Direzione direzione) {
        this.direzione = direzione;
    }
}
