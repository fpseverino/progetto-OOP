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
    private int numeroTurni = 0;

    public Partita(int dimensioneGriglia, int numeroNavi, Scanner scanner) {
        this.dimensioneGriglia = dimensioneGriglia;
        this.numeroNavi = numeroNavi;
        this.navi = new Nave[numeroNavi];
        initNavi(scanner);
        this.naviGiocatore = new Nave[numeroNavi];
        this.naviComputer = new Nave[numeroNavi];
        for (int i = 0; i < numeroNavi; i++)
            naviGiocatore[i] = new Nave(navi[i].getNome(), navi[i].getDimensione());
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

    public int getNumeroTurni() {
        return numeroTurni;
    }

    public void initNavi(Scanner scanner) {
        for (int i = 0; i < numeroNavi; i++) {
            System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
            String nome = scanner.nextLine();
            boolean nomeUsato = false;
            for (int j = 0; j < i; j++) {
                if (navi[j].getNome().equals(nome)) {
                    nomeUsato = true;
                    break;
                }
            }
            while (nomeUsato) {
                System.out.println("Il nome " + nome + " è già stato usato");
                System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
                nome = scanner.nextLine();
                nomeUsato = false;
                for (int j = 0; j < i; j++) {
                    if (navi[j].getNome().equals(nome)) {
                        nomeUsato = true;
                        break;
                    }
                }
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
}