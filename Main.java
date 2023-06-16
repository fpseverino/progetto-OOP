//
//  Main.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;
import java.io.*;
/**
 * Classe principale del programma.
 * Gestisce l'avvio del gioco e le operazioni di inizializzazione delle partite.
 */
public class Main {
    /**
     * Punto di ingresso principale del programma.
     * Avvia il gioco e gestisce il menu principale.
     * 
     * @param args Gli argomenti passati al programma.
     */
    static public void main(String[] args) {
        printTitolo();
        Scanner scanner = new Scanner(System.in);
        boolean esci = false;
        while (!esci) {
            switch (menu(scanner)) {
                case 1:
                    Partita nuovaPartita = initPartita(scanner);
                    nuovaPartita.posizionaNavi(scanner);
                    try {
                        nuovaPartita.gioca(scanner);
                    } catch (PosizioneNonValidaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    Partita partitaCaricata = null;
                    try {
                        partitaCaricata = caricaPartita(scanner);
                    } catch (FileNotFoundException e) {
                        System.out.println("File non trovato");
                    } catch (IOException e) {
                        System.out.println("Errore di I/O");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Classe non trovata");
                    }
                    if (partitaCaricata != null)
                        try {
                            partitaCaricata.gioca(scanner);
                        } catch (PosizioneNonValidaException e) {
                            System.out.println(e.getMessage());
                        }
                    break;
                case 3:
                    System.out.println("\nUscita\n");
                    esci = true;
                    break;
            }
        }
        scanner.close();
    }
    /**
     * Inizializza una nuova partita.
     * Chiede all'utente la dimensione della griglia e il numero di navi, quindi crea una nuova partita.
     * 
     * @param scanner L'oggetto Scanner per leggere l'input dell'utente.
     * @return La partita inizializzata, o null se si verificano errori durante l'inizializzazione.
     */
    public static Partita initPartita(Scanner scanner) {
        System.out.print("Inserisci la dimensione della griglia: ");
        while (!scanner.hasNextInt()) {
            System.out.println("La dimensione della griglia deve essere un numero intero");
            System.out.print("Inserisci la dimensione della griglia: ");
            scanner.next();
        }
        int dimensioneGriglia = scanner.nextInt();
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (dimensioneGriglia < Griglia.MIN_DIMENSIONE || dimensioneGriglia > Griglia.MAX_DIMENSIONE) {
            System.out.println("La dimensione della griglia deve essere compresa tra " + Griglia.MIN_DIMENSIONE + " e " + Griglia.MAX_DIMENSIONE);
            System.out.print("Inserisci la dimensione della griglia: ");
            while (!scanner.hasNextInt()) {
                System.out.println("La dimensione della griglia deve essere un numero intero");
                System.out.print("Inserisci la dimensione della griglia: ");
                scanner.next();
            }
            dimensioneGriglia = scanner.nextInt();
            if (scanner.hasNextLine())
                scanner.nextLine();
        }
        System.out.print("Inserisci il numero di navi: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Il numero di navi deve essere un numero intero");
            System.out.print("Inserisci il numero di navi: ");
            scanner.next();
        }
        int numeroNavi = scanner.nextInt();
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (numeroNavi < 1 || numeroNavi > dimensioneGriglia) {
            System.out.println("Il numero di navi deve essere compreso tra 1 e la dimensione della griglia (" + dimensioneGriglia + ")");
            System.out.print("Inserisci il numero di navi: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Il numero di navi deve essere un numero intero");
                System.out.print("Inserisci il numero di navi: ");
                scanner.next();
            }
            numeroNavi = scanner.nextInt();
            if (scanner.hasNextLine())
                scanner.nextLine();
        }
        System.out.print("Inserisci il nome del file di salvataggio: ");
        String nomeFile = scanner.nextLine();
        while (!nomeFile.endsWith(".dat")) {
            System.out.println("Il nome del file deve terminare con .dat");
            System.out.print("Inserisci il nome del file di salvataggio: ");
            nomeFile = scanner.nextLine(); 
        }
        try {
            return new Partita(dimensioneGriglia, numeroNavi, nomeFile, scanner);
        } catch (NaveNonValidaException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (PosizioneNonValidaException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (GrigliaNonValidaException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * Carica una partita esistente da un file di salvataggio.
     * Chiede all'utente il nome del file di salvataggio e tenta di caricare la partita corrispondente.
     * 
     * @param scanner L'oggetto Scanner per leggere l'input dell'utente.
     * @return La partita caricata, o null se si verificano errori durante il caricamento.
     * @throws ClassCastException Se il file di salvataggio non contiene una partita valida.
     * @throws ClassNotFoundException Se la classe della partita non viene trovata durante il caricamento.
     * @throws IOException Se si verifica un errore di I/O durante la lettura del file di salvataggio.
     */
    public static Partita caricaPartita(Scanner scanner) throws ClassCastException, ClassNotFoundException, IOException {
        System.out.print("Inserisci il nome del file di salvataggio (digita 'exit' per tornare indietro): ");
        String nomeFile = scanner.nextLine();
        if (nomeFile.equalsIgnoreCase("exit"))
            return null; 
        while (!nomeFile.endsWith(".dat")) {
            System.out.println("Il nome del file deve terminare con .dat");
            System.out.print("Inserisci il nome del file di salvataggio (digita 'exit' per tornare indietro): ");
            nomeFile = scanner.nextLine(); 
            if (nomeFile.equalsIgnoreCase("exit"))
                return null; 
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(nomeFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Partita partita = (Partita) objectInputStream.readObject();
            objectInputStream.close();
            return partita;
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file");
        } catch (ClassNotFoundException e) {
            System.out.println("Errore durante la lettura del file");
        } catch (ClassCastException e) {
            System.out.println("Il file non contiene una partita");
        }
        return null;
    }
    
    /**
     * Stampa il titolo del gioco sulla console.
     */
    public static void printTitolo(){
        System.out.println("" +
                "\n" + Display.ANSI_CYAN +
                "██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n" +
                "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n" +
                "██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n" +
                "██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n" +
                "██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n" +
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░" +
                Display.ANSI_RESET);
    }
    /**
     * Visualizza il menu principale e legge la scelta dell'utente.
     * 
     * @param scanner L'oggetto Scanner per leggere l'input dell'utente.
     * @return La scelta dell'utente.
     */
    public static int menu(Scanner scanner) {
        System.out.println("\n*** MENU ***");
        System.out.println("1. Nuova partita");
        System.out.println("2. Carica partita");
        System.out.println("3. Esci");
        System.out.print("Inserisci la tua scelta: ");
        while (!scanner.hasNextInt()) {
            System.out.println("La scelta deve essere un numero intero");
            System.out.print("Inserisci la tua scelta: ");
            scanner.next();
        }
        int scelta = scanner.nextInt();
        if (scanner.hasNextLine())
            scanner.nextLine();
        while (scelta < 1 || scelta > 3) {
            System.out.println("La scelta deve essere compresa tra 1 e 3");
            System.out.print("Inserisci la tua scelta: ");
            while (!scanner.hasNextInt()) {
                System.out.println("La scelta deve essere un numero intero");
                System.out.print("Inserisci la tua scelta: ");
                scanner.next();
            }
            scelta = scanner.nextInt();
            if (scanner.hasNextLine())
                scanner.nextLine();
        }
        return scelta;
    }
}
