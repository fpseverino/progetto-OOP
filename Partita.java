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

    public Partita(int dimensioneGriglia, int numeroNavi, Scanner scanner) throws IllegalArgumentException {
        if (numeroNavi < 1 || numeroNavi > dimensioneGriglia)
            throw new IllegalArgumentException("Il numero di navi deve essere compreso tra 1 e la dimensione della griglia (" + dimensioneGriglia + ")");
        this.dimensioneGriglia = dimensioneGriglia;
        this.numeroNavi = numeroNavi;
        this.navi = new Nave[numeroNavi];
        initNavi(scanner);
        this.naviGiocatore = new Nave[numeroNavi];
        for (int i = 0; i < numeroNavi; i++)
            naviGiocatore[i] = new Nave(navi[i].getNome(), navi[i].getDimensione());
        this.naviComputer = new Nave[numeroNavi];
        for (int i = 0; i < numeroNavi; i++)
            naviComputer[i] = new Nave(navi[i].getNome(), navi[i].getDimensione());
        this.grigliaNaviGiocatore = new Griglia(dimensioneGriglia, naviGiocatore);
        this.grigliaNaviComputer = new Griglia(dimensioneGriglia, naviComputer);
        this.grigliaColpiGiocatore = new Griglia(dimensioneGriglia, null);
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

    public Griglia getGrigliaColpiGiocatore() {
        return grigliaColpiGiocatore;
    }

    public void initNavi(Scanner scanner) {
        for (int i = 0; i < numeroNavi; i++) {
            System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
            String nome;
            while (nomeNaveIsUsato(nome = scanner.nextLine(), i)) {
                System.out.println("Il nome " + nome + " è già stato usato");
                System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
            }
            System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
            int dimensione = scanner.nextInt();
            if (scanner.hasNextLine())
                scanner.nextLine();
            while (dimensione > dimensioneGriglia || dimensione < 1) {
                System.out.println("La dimensione della nave non può essere maggiore della dimensione della griglia (" + dimensioneGriglia + ") o minore di 1");
                System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
                dimensione = scanner.nextInt();
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
            navi[i] = new Nave(nome, dimensione);
        }
    }

    public boolean nomeNaveIsUsato(String nome, int numeroNaviInserite) {
        for (int j = 0; j < numeroNaviInserite; j++)
            if (navi[j].getNome().equals(nome))
                return true;
        return false;
    }
}