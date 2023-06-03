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
        System.out.print("Inserisci la dimensione della griglia: ");
        int dimensioneGriglia = scanner.nextInt();
        System.out.print("Inserisci il numero di navi: ");
        int numeroNavi = scanner.nextInt();
        scanner.nextLine();

        Partita partita = new Partita(dimensioneGriglia, numeroNavi, scanner);

        Griglia grigliaNaviGiocatore = partita.getGrigliaNaviGiocatore();
        grigliaNaviGiocatore.posizionaNavi(partita.getNavi(), scanner);

        Griglia grigliaNaviComputer = partita.getGrigliaNaviComputer();
        grigliaNaviComputer.posizionaNavi(partita.getNavi());

        Griglia grigliaColpiGiocatore = partita.getGrigliaColpiGiocatore();

        System.out.println();
        printGriglie(grigliaNaviGiocatore, grigliaColpiGiocatore);
        System.out.println();
        System.out.println("Navi giocatore:");
        grigliaNaviGiocatore.printRecapNavi();
        System.out.println();
        System.out.println("Navi computer:");
        grigliaNaviComputer.printRecapNavi();
        System.out.println();

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (!grigliaNaviComputer.naviTutteAffondate() && !grigliaNaviGiocatore.naviTutteAffondate()) {
            System.out.print("Inserisci la posizione da colpire: ");
            String input = scanner.nextLine();
            if (input.equals("exit"))
                break;
            char c = input.charAt(0);
            int num = Integer.parseInt(input.substring(1));
            Posizione posizione = new Posizione(c, num);
            grigliaNaviComputer.sparaColpo(posizione);

            grigliaColpiGiocatore.checkColpo(posizione, grigliaNaviComputer);

            grigliaNaviGiocatore.sparaColpo();

            grigliaNaviGiocatore.checkAffondate();
            grigliaNaviComputer.checkAffondate();

            System.out.println();
            printGriglie(grigliaNaviGiocatore, grigliaColpiGiocatore);
            System.out.println();

            System.out.println("Navi giocatore:");
            grigliaNaviGiocatore.printRecapNavi();
            System.out.println();

            System.out.println("Navi computer:");
            grigliaNaviComputer.printRecapNavi();
            System.out.println();
        }
        scanner.close();
        System.out.println("Grazie per aver giocato!");
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
                    case COLPITA:
                        System.out.print(Display.ANSI_RED + Display.COLPITA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case MANCATA:
                        System.out.print(Display.ANSI_WHITE + Display.MANCATA_CHAR + Display.ANSI_RESET + " ");
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
                    case COLPITA:
                        System.out.print(Display.ANSI_RED + Display.COLPITA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case MANCATA:
                        System.out.print(Display.ANSI_WHITE + Display.MANCATA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    default:
                        System.out.print("? ");
                }
            System.out.println();
        }
    }
}
