//
//  Main.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;
import java.io.*;

public class Main {
    static public void main(String[] args) {
        printTitolo();
        Scanner scanner = new Scanner(System.in);
        boolean esci = false;
        while (!esci) {
            switch (menu(scanner)) {
                case 1:
                    Partita nuovaPartita = initPartita(scanner);
                    nuovaPartita.posizionaNavi(scanner);
                    nuovaPartita.gioca(scanner);
                    break;
                case 2:
                    Partita partitaCaricata = caricaPartita(scanner);
                    if (partitaCaricata != null)
                        partitaCaricata.gioca(scanner);
                    break;
                case 3:
                    System.out.println("Uscita");
                    esci = true;
                    break;
            }
        }
        scanner.close();
    }

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
        return new Partita(dimensioneGriglia, numeroNavi, nomeFile, scanner);
    }

    public static Partita caricaPartita(Scanner scanner) {
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
    

    public static void printTitolo(){
        System.out.println("" +
                "\n" + Display.ANSI_CYAN +
                "██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n" +
                "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n" +
                "██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n" +
                "██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n" +
                "██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n" +
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░\n" +
                Display.ANSI_RESET);
    }

    public static int menu(Scanner scanner) {
        System.out.println("*** MENU ***");
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
