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

    public void posizionaNavi(Scanner scanner) {
        grigliaNaviGiocatore.posizionaNavi(navi, scanner);
        grigliaNaviComputer.posizionaNavi(navi);
        printGriglie();
        grigliaNaviGiocatore.printRecapNavi("Navi giocatore:");
        grigliaNaviComputer.printRecapNavi("Navi computer:");
    }

    public void turno(Scanner scanner) {
        System.out.println("Turno " + ++numeroTurni);
        Posizione posizione = null;
        boolean colpoValido = false;
        while (!colpoValido) {
            try {
                System.out.print("Inserisci la posizione da colpire: ");
                String input = scanner.nextLine();
                if (input.equals("exit"))
                    break;
                while (!Posizione.isPosizione(input)) {
                    System.out.println("La posizione non è valida");
                    System.out.print("Inserisci la posizione da colpire: ");
                    input = scanner.nextLine();
                    if (input.equals("exit"))
                        break;
                }
                char c = input.charAt(0);
                int num = Integer.parseInt(input.substring(1));
                posizione = new Posizione(c, num);
                grigliaNaviComputer.sparaColpo(posizione);
                colpoValido = true;
            } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
        grigliaColpiGiocatore.checkColpo(posizione, grigliaNaviComputer);
        grigliaNaviGiocatore.sparaColpo();
        grigliaNaviGiocatore.checkAffondate();
        grigliaNaviComputer.checkAffondate();
        printGriglie();
        grigliaNaviGiocatore.printRecapNavi("Navi giocatore:");
        grigliaNaviComputer.printRecapNavi("Navi computer:");
    }

    public void gioca(Scanner scanner) {
        while (!grigliaNaviGiocatore.naviTutteAffondate() && !grigliaNaviComputer.naviTutteAffondate())
            turno(scanner);
        if (grigliaNaviGiocatore.naviTutteAffondate()) {
            System.out.println("Hai perso!");
            System.out.println("Numero turni: " + numeroTurni);
            System.out.println("Il tuo punteggio: " + grigliaNaviComputer.getPunteggio());
            System.out.println("Punteggio computer: " + grigliaNaviGiocatore.getPunteggio());
        } else {
            System.out.println("Hai vinto!");
            System.out.println("Numero turni: " + numeroTurni);
            System.out.println("Il tuo punteggio: " + grigliaNaviComputer.getPunteggio());
            System.out.println("Punteggio computer: " + grigliaNaviGiocatore.getPunteggio());
        }
    }

    public void printGriglie() {
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < grigliaNaviGiocatore.getDimensione(); i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.print("    ");
        for (int i = 0; i < grigliaColpiGiocatore.getDimensione(); i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < grigliaNaviGiocatore.getDimensione(); i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < grigliaNaviGiocatore.getDimensione(); j++) {
                String casella = "";
                switch (grigliaNaviGiocatore.getGriglia()[i][j].getOccupazione()) {
                    case NAVE:
                        casella += Display.ANSI_YELLOW;
                        break;
                    case ACQUA:
                        casella += Display.ANSI_BLUE;
                        break;
                    case COLPITA:
                        casella += Display.ANSI_RED;
                        break;
                    case MANCATA:
                        casella += Display.ANSI_WHITE;
                        break;
                }
                System.out.print(casella + grigliaNaviGiocatore.getGriglia()[i][j].getOccupazione().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.print(" " + (i + 1) + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < grigliaColpiGiocatore.getDimensione(); j++) {
                String casella = "";
                switch (grigliaColpiGiocatore.getGriglia()[i][j].getOccupazione()) {
                    case NAVE:
                        casella += Display.ANSI_YELLOW;
                        break;
                    case ACQUA:
                        casella += Display.ANSI_BLUE;
                        break;
                    case COLPITA:
                        casella += Display.ANSI_RED;
                        break;
                    case MANCATA:
                        casella += Display.ANSI_WHITE;
                        break;
                }
                System.out.print(casella + grigliaColpiGiocatore.getGriglia()[i][j].getOccupazione().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}