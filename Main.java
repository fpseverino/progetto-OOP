//
//  Main.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Main {
    static public void main(String[] args) {
        printTitolo();
        Scanner scanner = new Scanner(System.in);
        Partita partita = initPartita(scanner);
        partita.posizionaNavi(scanner);
        partita.gioca(scanner);
        scanner.close();
        System.out.println("Grazie per aver giocato!");
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
        return new Partita(dimensioneGriglia, numeroNavi, scanner);
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
}
