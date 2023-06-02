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

    private final int dimensioneGriglia;
    private char[][] griglia;

    public Griglia(int dimensioneGriglia) {
        this.dimensioneGriglia = dimensioneGriglia;
        griglia = new char[dimensioneGriglia][dimensioneGriglia];
        for (int i = 0; i < dimensioneGriglia; i++) {
            for (int j = 0; j < dimensioneGriglia; j++) {
                griglia[i][j] = ACQUA;
            }
        }
    }

    public void stampa() {
        System.out.print("   ");
        for (int i = 0; i < dimensioneGriglia; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < dimensioneGriglia; i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < dimensioneGriglia; j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void posizionaNavi(Nave[] navi, Scanner scanner) {
        for (Nave nave : navi) {
            System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (" + nave.getDimensione() + "): ");
            String posizione = scanner.nextLine();
            Posizione posizioneNave = new Posizione(posizione.charAt(0), Integer.parseInt(posizione.substring(1)));
            System.out.print("Inserisci la direzione della nave " + nave.getTipo() + " (orizzontale/verticale): ");
            Direzione direzione = Direzione.valueOf(scanner.nextLine().toUpperCase());
            Nave naveDaPosizionare = new Nave(nave.getTipo(), nave.getDimensione());
            naveDaPosizionare.setPosizioneIniziale(posizioneNave);
            naveDaPosizionare.setDirezione(direzione);
            while (!posizioneValida(naveDaPosizionare)) {
                System.out.println("La nave non può essere posizionata in questa posizione");
                System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (" + nave.getDimensione() + "): ");
                posizione = scanner.nextLine();
                posizioneNave = new Posizione(posizione.charAt(0), Integer.parseInt(posizione.substring(1)));
                System.out.print("Inserisci la direzione della nave " + nave.getTipo() + " (orizzontale/verticale): ");
                direzione = Direzione.valueOf(scanner.nextLine().toUpperCase());
                naveDaPosizionare.setPosizioneIniziale(posizioneNave);
                naveDaPosizionare.setDirezione(direzione);
            }
            if (direzione == Direzione.ORIZZONTALE) {
                for (int i = 0; i < nave.getDimensione(); i++) {
                    griglia[posizioneNave.getRiga()][posizioneNave.getColonna() + i] = NAVE;
                }
            } else {
                for (int i = 0; i < nave.getDimensione(); i++) {
                    griglia[posizioneNave.getRiga() + i][posizioneNave.getColonna()] = NAVE;
                }
            }
            stampa();
        }
    }

    public boolean posizioneValida(Nave nave) {
        Posizione posizioneIniziale = nave.getPosizioneIniziale();
        Direzione direzione = nave.getDirezione();
        int dimensione = nave.getDimensione();
        if (direzione == Direzione.ORIZZONTALE) {
            for (int i = 0; i < dimensione; i++) {
                if (i + posizioneIniziale.getColonna() >= dimensioneGriglia) {
                    return false;
                }
                if (griglia[posizioneIniziale.getRiga()][posizioneIniziale.getColonna() + i] == NAVE) {
                    return false;
                }
            }
            return posizioneIniziale.getColonna() + dimensione <= dimensioneGriglia;
        } else {
            for (int i = 0; i < dimensione; i++) {
                if (i + posizioneIniziale.getRiga() >= dimensioneGriglia) {
                    return false;
                }
                if (griglia[posizioneIniziale.getRiga() + i][posizioneIniziale.getColonna()] == NAVE) {
                    return false;
                }
            }
            return posizioneIniziale.getRiga() + dimensione <= dimensioneGriglia;
        }
    }
}
