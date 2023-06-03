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
        Partita partita = new Partita(5, 3);
        partita.initNavi(scanner);
        Griglia grigliaGiocatore = partita.getGrigliaNaviGiocatore();
        grigliaGiocatore.posizionaNavi(partita.getNavi(), scanner);
        grigliaGiocatore.print();
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
