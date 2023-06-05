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
        System.out.print("Inserisci la dimensione della griglia: ");
        int dimensioneGriglia = scanner.nextInt();
        if (scanner.hasNextLine())
            scanner.nextLine();
        System.out.print("Inserisci il numero di navi: ");
        int numeroNavi = scanner.nextInt();
        if (scanner.hasNextLine())
            scanner.nextLine();

        Partita partita = new Partita(dimensioneGriglia, numeroNavi, scanner);

        Griglia grigliaNaviGiocatore = partita.getGrigliaNaviGiocatore();
        grigliaNaviGiocatore.posizionaNavi(partita.getNavi(), scanner);

        Griglia grigliaNaviComputer = partita.getGrigliaNaviComputer();
        grigliaNaviComputer.posizionaNavi(partita.getNavi());

        Griglia grigliaColpiGiocatore = partita.getGrigliaColpiGiocatore();

        printGriglie(grigliaNaviGiocatore, grigliaColpiGiocatore);
        grigliaNaviGiocatore.printRecapNavi("Navi giocatore:");
        grigliaNaviComputer.printRecapNavi("Navi computer:");

        while (!grigliaNaviComputer.naviTutteAffondate() && !grigliaNaviGiocatore.naviTutteAffondate()) {
            System.out.print("Inserisci la posizione da colpire: ");
            String input = scanner.nextLine();
            if (input.equals("exit"))
                break;
            while (input.length() >= 2 || !Character.isLetter(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
                System.out.println("La posizione non è valida");
                System.out.print("Inserisci la posizione da colpire: ");
                input = scanner.nextLine();
                if (input.equals("exit"))
                    break;
            }
            char c = input.charAt(0);
            int num = Integer.parseInt(input.substring(1));
            Posizione posizione = new Posizione(c, num);
            grigliaNaviComputer.sparaColpo(posizione);

            grigliaColpiGiocatore.checkColpo(posizione, grigliaNaviComputer);

            grigliaNaviGiocatore.sparaColpo();

            grigliaNaviGiocatore.checkAffondate();
            grigliaNaviComputer.checkAffondate();

            printGriglie(grigliaNaviGiocatore, grigliaColpiGiocatore);
            grigliaNaviGiocatore.printRecapNavi("Navi giocatore:");
            grigliaNaviComputer.printRecapNavi("Navi computer:");
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
                "╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚══════╝╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░\n");
    }

    public static void printGriglie(Griglia grigliaGiocatore, Griglia grigliaComputer) {
        System.out.println();
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
            for (int j = 0; j < grigliaGiocatore.getDimensione(); j++) {
                String casella = "";
                switch (grigliaGiocatore.getGriglia()[i][j].getCasella()) {
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
                System.out.print(casella + grigliaGiocatore.getGriglia()[i][j].getCasella().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.print(" " + (i + 1) + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < grigliaComputer.getDimensione(); j++) {
                String casella = "";
                switch (grigliaComputer.getGriglia()[i][j].getCasella()) {
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
                System.out.print(casella + grigliaComputer.getGriglia()[i][j].getCasella().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
