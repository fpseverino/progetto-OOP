//
//  Main.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Main {
    static public void main(String[] args) {
        stampaTitolo();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci la dimensione della griglia: ");
        int dimensione = scanner.nextInt();
        System.out.print("Inserisci il numero di navi: ");
        int numeroNavi = scanner.nextInt();
        scanner.nextLine();
        Partita partita = new Partita(dimensione, numeroNavi);
        partita.initNavi(scanner);
        Griglia grigliaNaviGiocatore = partita.getGrigliaNaviGiocatore();
        grigliaNaviGiocatore.posizionaNavi(partita.getNavi(), scanner);
        scanner.close();
    }

    public static void stampaTitolo(){
        System.out.println("" +
                "\n" +
                "██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n" +
                "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n" +
                "██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n" +
                "██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n" +
                "██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n" +
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░");
    }
}
