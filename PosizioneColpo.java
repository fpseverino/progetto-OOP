//
//  PosizioneColpo.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class PosizioneColpo extends Posizione {
    private boolean colpita;

    public PosizioneColpo(char colonna, int riga, boolean colpita) {
        super(colonna, riga);
        this.colpita = colpita;
    }
}
