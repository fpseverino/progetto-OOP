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
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        Partita partita = new Partita(10, 3);
        partita.initNavi(scanner);
        Griglia grigliaGiocatore = partita.getGrigliaNaviGiocatore();
        Griglia grigliaComputer = partita.getGrigliaNaviComputer();
        grigliaGiocatore.posizionaNavi(partita.getNavi(), scanner);
        grigliaComputer.posizionaNaviComputer(partita.getNavi());
        grigliaGiocatore.print();
        printGriglie(grigliaGiocatore, grigliaComputer);
        scanner.close();
    }

    public static void printTitolo(){
        System.out.println("" +
                "\n" +
                "██████╗░░█████╗░████████╗████████╗██╗░░░░░███████╗░██████╗██╗░░██╗██╗██████╗░\n" +
                "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║░░░░░██╔════╝██╔════╝██║░░██║██║██╔══██╗\n" +
                "██████╦╝███████║░░░██║░░░░░░██║░░░██║░░░░░█████╗░░╚█████╗░███████║██║██████╔╝\n" +
                "██╔══██╗██╔══██║░░░██║░░░░░░██║░░░██║░░░░░██╔══╝░░░╚═══██╗██╔══██║██║██╔═══╝░\n" +
                "██████╦╝██║░░██║░░░██║░░░░░░██║░░░███████╗███████╗██████╔╝██║░░██║██║██║░░░░░\n" +
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░");
    }

    public static void printGriglie(Griglia grigliaGiocatore, Griglia grigliaComputer){
        System.out.print("   ");
        for (int i = 0; i < grigliaGiocatore.getDimensione(); i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.print("    ");
        for (int i = 0; i < grigliaComputer.getDimensione(); i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < grigliaGiocatore.getDimensione(); i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < grigliaGiocatore.getDimensione(); j++)
                switch (grigliaGiocatore.getGriglia()[i][j].getOccupazione()) {
                    case NAVE:
                        System.out.print(Display.ANSI_YELLOW + Display.NAVE_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case ACQUA:
                        System.out.print(Display.ANSI_BLUE + Display.ACQUA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case COLPITO:
                        System.out.print(Display.ANSI_RED + Display.COLPITO_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case MANCATO:
                        System.out.print(Display.ANSI_WHITE + Display.MANCATO_CHAR + Display.ANSI_RESET + " ");
                        break;
                    default:
                        System.out.print("? ");
                }
            System.out.print(" " + (i + 1) + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < grigliaComputer.getDimensione(); j++)
                switch (grigliaComputer.getGriglia()[i][j].getOccupazione()) {
                    case NAVE:
                        System.out.print(Display.ANSI_YELLOW + Display.NAVE_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case ACQUA:
                        System.out.print(Display.ANSI_BLUE + Display.ACQUA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case COLPITO:
                        System.out.print(Display.ANSI_RED + Display.COLPITO_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case MANCATO:
                        System.out.print(Display.ANSI_WHITE + Display.MANCATO_CHAR + Display.ANSI_RESET + " ");
                        break;
                    default:
                        System.out.print("? ");
                }
            System.out.println();
        }
    }
}
