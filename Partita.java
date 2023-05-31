//
//  Partita.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Partita {
    private int dimensioneGriglia;
    private Nave[] navi;
    private PosizioneColpo[] posizioniColpite;
    private int numeroTurni;

    public Partita(int dimensioneGriglia, int numeroNavi) {
        this.dimensioneGriglia = dimensioneGriglia;
        this.posizioniColpite = new PosizioneColpo[dimensioneGriglia * dimensioneGriglia];
        this.numeroTurni = 0;
    }
}