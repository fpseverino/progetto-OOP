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
    private Nave[] naviGiocatore;
    private Nave[] naviComputer;
    private Griglia grigliaNaviGiocatore;
    private Griglia grigliaNaviComputer;
    private Griglia grigliaColpiGiocatore;
    private Griglia grigliaColpiComputer;
    private int numeroTurni = 0;

    public Partita(int dimensioneGriglia, int numeroNavi) throws IllegalArgumentException {
        if (dimensioneGriglia < 5 || dimensioneGriglia > 26) {
            throw new IllegalArgumentException("La dimensione della griglia deve essere compresa tra 5 e 26");
        }
        if (numeroNavi < 1 || numeroNavi > dimensioneGriglia) {
            throw new IllegalArgumentException("Il numero di navi deve essere compreso tra 1 e la dimensione della griglia (" + dimensioneGriglia + ")");
        }
        this.dimensioneGriglia = dimensioneGriglia;
        this.numeroNavi = numeroNavi;
        this.navi = new Nave[numeroNavi];
        this.grigliaNaviGiocatore = new Griglia(dimensioneGriglia);
        this.grigliaNaviComputer = new Griglia(dimensioneGriglia);
        this.grigliaColpiGiocatore = new Griglia(dimensioneGriglia);
        this.grigliaColpiComputer = new Griglia(dimensioneGriglia);
    }

    public void initNavi(Scanner scanner) {
        for (int i = 0; i < numeroNavi; i++) {
            System.out.print("Inserisci il tipo della nave " + (i + 1) + ": ");
            String tipo = scanner.nextLine();
            boolean tipoUsato = false;
            for (int j = 0; j < i; j++) {
                if (navi[j].getTipo().equals(tipo)) {
                    tipoUsato = true;
                    break;
                }
            }
            while (tipoUsato) {
                System.out.println("Il tipo della nave è già stato usato");
                System.out.print("Inserisci il tipo della nave " + (i + 1) + ": ");
                tipo = scanner.nextLine();
                tipoUsato = false;
                for (int j = 0; j < i; j++) {
                    if (navi[j].getTipo().equals(tipo)) {
                        tipoUsato = true;
                        break;
                    }
                }
            }
            System.out.print("Inserisci la dimensione della nave " + (i + 1) + ": ");
            int dimensione = scanner.nextInt();
            while (dimensione > dimensioneGriglia) {
                System.out.println("La dimensione della nave non può essere maggiore della dimensione della griglia");
                System.out.print("Inserisci la dimensione della nave " + (i + 1) + ": ");
                dimensione = scanner.nextInt();
            }
            scanner.nextLine();
            navi[i] = new Nave(tipo, dimensione);
        }

        naviGiocatore = new Nave[numeroNavi];
        naviComputer = new Nave[numeroNavi];
        for (int i = 0; i < numeroNavi; i++) {
            naviGiocatore[i] = new Nave(navi[i].getTipo(), navi[i].getDimensione());
            naviComputer[i] = new Nave(navi[i].getTipo(), navi[i].getDimensione());
        }
    }

    public Nave[] getNavi() {
        return navi;
    }

    public Griglia getGrigliaNaviGiocatore() {
        return grigliaNaviGiocatore;
    }

    public Griglia getGrigliaNaviComputer() {
        return grigliaNaviComputer;
    }
}