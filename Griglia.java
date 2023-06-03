//
//  Griglia.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Griglia {
    public static final char ACQUA = '~';
    // public static final char NAVE = '☐';
    // public static final char COLPITO = '☒';
    // public static final char MANCATO = '☸';
    public static final char NAVE = 'O';
    public static final char COLPITO = '0';
    public static final char MANCATO = 'X';

    private final int dimensione;
    private char[][] griglia;

    public Griglia(int dimensione) {
        this.dimensione = dimensione;
        griglia = new char[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++)
            for (int j = 0; j < dimensione; j++)
                griglia[i][j] = ACQUA;
    }

    public Griglia(Griglia griglia) {
        this.dimensione = griglia.dimensione;
        this.griglia = new char[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++)
            System.arraycopy(griglia.griglia[i], 0, this.griglia[i], 0, dimensione);
    }

    public void posizionaNavi(Nave[] navi, Scanner scanner) {
        stampa();
        for (Nave nave : navi) {
            System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (" + nave.getDimensione() + "): ");
            String posizione = scanner.nextLine();
            Posizione posizioneNave = new Posizione(posizione.charAt(0), Integer.parseInt(posizione.substring(1)));
            System.out.print("Inserisci la direzione della nave " + nave.getTipo() + " (orizzontale/verticale): ");
            Direzione direzione = Direzione.fromString(scanner.nextLine());
            Nave naveDaPosizionare = new Nave(nave.getTipo(), nave.getDimensione());
            naveDaPosizionare.setPosizione(posizioneNave);
            naveDaPosizionare.setDirezione(direzione);
            while (!posizioneValida(naveDaPosizionare)) {
                System.out.println("La nave non può essere posizionata in questa posizione");
                System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (" + nave.getDimensione() + "): ");
                posizione = scanner.nextLine();
                posizioneNave = new Posizione(posizione.charAt(0), Integer.parseInt(posizione.substring(1)));
                System.out.print("Inserisci la direzione della nave " + nave.getTipo() + " (orizzontale/verticale): ");
                direzione = Direzione.fromString(scanner.nextLine());
                naveDaPosizionare.setPosizione(posizioneNave);
                naveDaPosizionare.setDirezione(direzione);
            }
            if (direzione == Direzione.ORIZZONTALE) {
                for (int i = 0; i < nave.getDimensione(); i++)
                    griglia[posizioneNave.getRiga()][posizioneNave.getColonna() + i] = NAVE;
            } else {
                for (int i = 0; i < nave.getDimensione(); i++)
                    griglia[posizioneNave.getRiga() + i][posizioneNave.getColonna()] = NAVE;
            }
            stampa();
        }
    }

    public boolean posizioneValida(Nave nave) {
        Posizione posizione = nave.getPosizione();
        Direzione direzione = nave.getDirezione();
        int dimensioneNave = nave.getDimensione();
        if (direzione == Direzione.ORIZZONTALE) {
            for (int i = 0; i < dimensioneNave; i++) {
                if (i + posizione.getColonna() >= dimensione)
                    return false;
                if (griglia[posizione.getRiga()][posizione.getColonna() + i] == NAVE)
                    return false;
            }
            return posizione.getColonna() + dimensioneNave <= dimensione;
        } else {
            for (int i = 0; i < dimensioneNave; i++) {
                if (i + posizione.getRiga() >= dimensione)
                    return false;
                if (griglia[posizione.getRiga() + i][posizione.getColonna()] == NAVE)
                    return false;
            }
            return posizione.getRiga() + dimensioneNave <= dimensione;
        }
    }

    public void stampa() {
        System.out.print("   ");
        for (int i = 0; i < dimensione; i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < dimensione; i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < dimensione; j++) {
                switch (griglia[i][j]) {
                    case NAVE:
                        System.out.print(Colore.ANSI_YELLOW + NAVE + Colore.ANSI_RESET + " ");
                        break;
                    case COLPITO:
                        System.out.print(Colore.ANSI_RED + COLPITO + Colore.ANSI_RESET + " ");
                        break;
                    case MANCATO:
                        System.out.print(Colore.ANSI_WHITE + MANCATO + Colore.ANSI_RESET + " ");
                        break;
                    case ACQUA:
                        System.out.print(Colore.ANSI_BLUE + ACQUA + Colore.ANSI_RESET + " ");
                        break;
                    default:
                        System.out.print(griglia[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
