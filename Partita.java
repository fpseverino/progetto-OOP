//
//  Partita.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;
import java.io.*;

/**
 * Rappresenta una partita di gioco.
 * Le istanze di questa classe contengono tutte le informazioni necessarie per giocare una partita.
 * La partita può essere salvata su file e caricata da file.
 */
public class Partita implements Serializable {
    private final int dimensioneGriglia;
    private final int numeroNavi;
    private Nave[] navi; // Blueprint delle navi create dall'utente
    private Nave[] naviGiocatore; // Tiene traccia dello stato delle navi del giocatore
    private Nave[] naviComputer; // Tiene traccia dello stato delle navi del computer
    private Griglia grigliaNaviGiocatore; // Griglia che tiene traccia delle navi posizionate dall'utente e dei colpi sparati dal computer
    private Griglia grigliaNaviComputer; // Griglia che tiene traccia delle navi posizionate dal computer e dei colpi sparati dall'utente
    private Griglia grigliaColpiGiocatore; // Griglia che tiene traccia dei colpi sparati dall'utente, tenendo nascoste le navi del computer
    private int numeroTurni = 0;
    private final String nomeFile; // Il nome del file per il salvataggio della partita

    /**
     * Crea una nuova istanza di Partita.
     *
     * @param dimensioneGriglia La dimensione della griglia di gioco.
     * @param numeroNavi        Il numero di navi da posizionare.
     * @param nomeFile          Il nome del file per il salvataggio della partita.
     * @param scanner           L'istanza di Scanner per l'input da tastiera.
     * @throws NaveNonValidaException     se una nave creata dall'utente non è valida.
     * @throws PosizioneNonValidaException se viene inserita una posizione non valida.
     * @throws GrigliaNonValidaException  se viene creata una griglia non valida.
     */
    public Partita(int dimensioneGriglia, int numeroNavi, String nomeFile, Scanner scanner) throws NaveNonValidaException, PosizioneNonValidaException, GrigliaNonValidaException {
        if (numeroNavi < 1 || numeroNavi > dimensioneGriglia)
            throw new IllegalArgumentException("Il numero di navi deve essere compreso tra 1 e la dimensione della griglia (" + dimensioneGriglia + ")");
        this.dimensioneGriglia = dimensioneGriglia;
        this.numeroNavi = numeroNavi;
        this.navi = new Nave[numeroNavi];
        initNavi(scanner);
        this.naviGiocatore = new Nave[numeroNavi];
        // Copia delle navi create dall'utente, per tenere traccia delle navi colpite
        for (int i = 0; i < numeroNavi; i++)
            naviGiocatore[i] = new Nave(navi[i].getNome(), navi[i].getDimensione());
        this.naviComputer = new Nave[numeroNavi];
        for (int i = 0; i < numeroNavi; i++)
            naviComputer[i] = new Nave(navi[i].getNome(), navi[i].getDimensione());
        this.grigliaNaviGiocatore = new Griglia(dimensioneGriglia, naviGiocatore);
        this.grigliaNaviComputer = new Griglia(dimensioneGriglia, naviComputer);
        this.grigliaColpiGiocatore = new Griglia(dimensioneGriglia, null);
        this.nomeFile = nomeFile;
    }

    /**
     * Restituisce le navi create dall'utente.
     *
     * @return Le navi create dall'utente.
     */
    public Nave[] getNavi() {
        return navi;
    }

    /**
     * Restituisce la griglia delle navi del giocatore.
     *
     * @return La griglia delle navi del giocatore.
     */
    public Griglia getGrigliaNaviGiocatore() {
        return grigliaNaviGiocatore;
    }

    /**
     * Restituisce la griglia delle navi del computer.
     *
     * @return La griglia delle navi del computer.
     */
    public Griglia getGrigliaNaviComputer() {
        return grigliaNaviComputer;
    }

    /**
     * Restituisce la griglia dei colpi sparati dal giocatore.
     *
     * @return La griglia dei colpi sparati dal giocatore.
     */
    public Griglia getGrigliaColpiGiocatore() {
        return grigliaColpiGiocatore;
    }

    /**
     * Inizializza le navi su scelta dell'utente.
     *
     * @param scanner L'istanza di Scanner per l'input da tastiera.
     * @throws NaveNonValidaException se una nave creata dall'utente non è valida.
     */
    public void initNavi(Scanner scanner) throws NaveNonValidaException {
        // Scelta del nome e della dimensione delle navi
        for (int i = 0; i < numeroNavi; i++) {
            System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
            String nome;
            while (nomeNaveIsUsato(nome = scanner.nextLine(), i)) {
                System.out.println("Il nome " + nome + " è già stato usato");
                System.out.print("Inserisci il nome della nave " + (i + 1) + ": ");
            }
            System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("La dimensione della nave deve essere un numero intero");
                System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
                scanner.next();
            }
            int dimensione = scanner.nextInt();
            if (scanner.hasNextLine())
                scanner.nextLine();
            while (dimensione > dimensioneGriglia || dimensione < 1) {
                System.out.println("La dimensione della nave non può essere maggiore della dimensione della griglia (" + dimensioneGriglia + ") o minore di 1");
                System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("La dimensione della nave deve essere un numero intero");
                    System.out.print("Inserisci la dimensione della nave (" + nome + "): ");
                    scanner.next();
                }
                dimensione = scanner.nextInt();
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
            navi[i] = new Nave(nome, dimensione);
        }
    }

    /**
     * Verifica se il nome di una nave è già stato utilizzato.
     *
     * @param nome                Il nome della nave da verificare.
     * @param numeroNaviInserite  Il numero di navi già inserite.
     * @return true se il nome della nave è già stato utilizzato, false altrimenti.
     */
    public boolean nomeNaveIsUsato(String nome, int numeroNaviInserite) {
        for (int j = 0; j < numeroNaviInserite; j++)
            if (navi[j].getNome().equals(nome))
                return true;
        return false;
    }

    /**
     * Posiziona le navi del giocatore e del computer sulla griglia.
     *
     * @param scanner L'istanza di Scanner per l'input da tastiera.
     */
    public void posizionaNavi(Scanner scanner) {
        grigliaNaviGiocatore.posizionaNavi(navi, scanner);
        grigliaNaviComputer.posizionaNavi(navi);
    }

    /**
     * Esegue un turno di gioco.
     *
     * @param scanner L'istanza di Scanner per l'input da tastiera.
     * @return true se il gioco continua, false se l'utente sceglie di uscire.
     * @throws PosizioneNonValidaException se la posizione inserita non è valida.
     */
    public boolean turno(Scanner scanner) throws PosizioneNonValidaException {
        printGriglie();
        grigliaNaviGiocatore.printRecapNavi("Navi giocatore:");
        grigliaNaviComputer.printRecapNavi("Navi computer:");
        System.out.println("Turno " + ++numeroTurni);
        Posizione posizione = null;
        boolean colpoValido = false;
        while (!colpoValido) {
            try {
                System.out.print("Inserisci la posizione da colpire ('exit' per uscire): ");
                String input = scanner.nextLine();
                if (input.equals("exit"))
                    return false;
                while (!Posizione.isPosizione(input)) {
                    System.out.println("La posizione non è valida");
                    System.out.print("Inserisci la posizione da colpire ('exit' per uscire): ");
                    input = scanner.nextLine();
                    if (input.equals("exit"))
                        return false;
                }
                char c = input.charAt(0);
                int num = Integer.parseInt(input.substring(1));
                posizione = new Posizione(c, num);
                grigliaNaviComputer.sparaColpo(posizione);
                colpoValido = true;
            } catch (PosizioneNonValidaException e) { System.out.println(e.getMessage()); }
        }
        grigliaColpiGiocatore.checkColpo(posizione, grigliaNaviComputer);
        grigliaNaviGiocatore.sparaColpo();
        grigliaNaviGiocatore.checkAffondate();
        grigliaNaviComputer.checkAffondate();
        return true;
    }

    /**
     * Avvia il gioco.
     *
     * @param scanner L'istanza di Scanner per l'input da tastiera.
     * @throws PosizioneNonValidaException se la posizione inserita non è valida.
     */
    public void gioca(Scanner scanner) throws PosizioneNonValidaException {
        while (!grigliaNaviGiocatore.naviTutteAffondate() && !grigliaNaviComputer.naviTutteAffondate()) {
            if (!turno(scanner))
                return;
            salvaPartita();
        }
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

   /**
     * Salva la partita su file.
     */
    public void salvaPartita() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(nomeFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Stampa le griglie di gioco, una di fianco all'altra.
     */
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