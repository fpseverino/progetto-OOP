//
//  Griglia.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Griglia {
    private final int dimensione;
    private Posizione[][] griglia;

    public Griglia(int dimensione) {
        this.dimensione = dimensione;
        griglia = new Posizione[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                griglia[i][j] = new Posizione(i, j);
            }
        }
    }

    public int getDimensione() {
        return dimensione;
    }

    public Posizione[][] getGriglia() {
        return griglia;
    }

    public void posizionaNavi(Nave[] navi, Scanner scanner) {
        for (Nave nave : navi) {
            print();
            System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
            String input = scanner.next();
            char c = input.charAt(0);
            int num = Integer.parseInt(input.substring(1));
            Posizione posizione = new Posizione(c, num);
            System.out.print("Inserisci la direzione della nave (Verticale/Orizzontale): ");
            Direzione direzione = Direzione.fromString(scanner.next());
            while (!isPosizioneValida(nave, posizione, direzione)) {
                System.out.println("La posizione non è valida");
                System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
                input = scanner.next();
                c = input.charAt(0);
                num = Integer.parseInt(input.substring(1));
                posizione = new Posizione(c, num);
                System.out.print("Inserisci la direzione della nave (Verticale/Orizzontale): ");
                direzione = Direzione.fromString(scanner.next());
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // consume any remaining input
                }
            }
            posizionaNave(nave, posizione, direzione);
        }
    }

    public void posizionaNave(Nave nave, Posizione posizione, Direzione direzione) {
        int dimensione = nave.getDimensione();
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (direzione == Direzione.VERTICALE) {
            for (int i = riga; i < riga + dimensione; i++) {
                griglia[i][colonna].setOccupazione(Posizione.Occupazione.NAVE);
                griglia[i][colonna].setTipoNave(nave.getTipo());
            }
        } else {
            for (int i = colonna; i < colonna + dimensione; i++) {
                griglia[riga][i].setOccupazione(Posizione.Occupazione.NAVE);
                griglia[riga][i].setTipoNave(nave.getTipo());
            }
        }
    }

    public void posizionaNaviComputer(Nave[] navi) {
        for (Nave nave : navi) {
            Posizione posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
            Direzione direzione = Direzione.values()[(int) (Math.random() * 2)];
            while (!isPosizioneValida(nave, posizione, direzione)) {
                posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
                direzione = Direzione.values()[(int) (Math.random() * 2)];
            }
            posizionaNave(nave, posizione, direzione);
        }
    }

    public boolean isPosizioneValida(Nave nave, Posizione posizione, Direzione direzione) {
        int dimensione = nave.getDimensione();
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (colonna < 0 || colonna >= this.dimensione || riga < 0 || riga >= this.dimensione)
            return false;
        if (direzione == Direzione.VERTICALE) {
            if (riga + dimensione > this.dimensione)
                return false;
            for (int i = riga; i < riga + dimensione; i++)
                if (griglia[i][colonna].getOccupazione() == Posizione.Occupazione.NAVE)
                    return false;
        } else {
            if (colonna + dimensione > this.dimensione)
                return false;
            for (int i = colonna; i < colonna + dimensione; i++)
                if (griglia[riga][i].getOccupazione() == Posizione.Occupazione.NAVE)
                    return false;
        }
        return true;
    }

    public void print() {
        System.out.print("   ");
        for (int i = 0; i < dimensione; i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < dimensione; i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < dimensione; j++) {
                switch (griglia[i][j].getOccupazione()) {
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
            }
            System.out.println();
        }
    }
}
