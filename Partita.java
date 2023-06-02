//
//  Partita.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Partita {
    private Giocatore giocatore;
    private Giocatore computer;
    private int dimensioneGriglia;
    private int numeroTurni;

    public Partita(Griglia grigliaGiocatore, Griglia grigliaComputer) {
        this.grigliaGiocatore = grigliaGiocatore;
        this.grigliaComputer = grigliaComputer;
        this.numeroTurni = 0;
    }
}