//
//  Partita.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Partita {
    private final int dimensioneGriglia;
    private final int numeroNavi;
    private Nave[] navi;
    private Giocatore giocatore;
    private Giocatore computer;
    private int numeroTurni = 0;

    public Partita(int dimensioneGriglia, int numeroNavi) {
        this.dimensioneGriglia = dimensioneGriglia;
        this.numeroNavi = numeroNavi;
        this.navi = new Nave[numeroNavi];
        this.giocatore = new Giocatore(dimensioneGriglia, false);
        this.computer = new Giocatore(dimensioneGriglia, true);
    }

    public void initNavi(Scanner scanner) {
        for (int i = 0; i < numeroNavi; i++) {
            System.out.print("Inserisci il tipo della nave " + (i + 1) + ": ");
            String tipo = scanner.nextLine();
            int dimensione;
            System.out.print("Inserisci la dimensione della nave " + (i + 1) + ": ");
            dimensione = scanner.nextInt();
            while (dimensione > dimensioneGriglia) {
                System.out.println("La dimensione della nave non può essere maggiore della dimensione della griglia");
                System.out.print("Inserisci la dimensione della nave " + (i + 1) + ": ");
                dimensione = scanner.nextInt();
            }
            scanner.nextLine();
            navi[i] = new Nave(tipo, dimensione);
        }
    }

    public Nave[] getNavi() {
        return navi;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }
}