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
    private Nave[] navi;

    public Griglia(int dimensione, Nave[] navi) {
        this.dimensione = dimensione;
        griglia = new Posizione[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                griglia[i][j] = new Posizione(i, j);
            }
        }
        this.navi = navi;
    }

    public int getDimensione() {
        return dimensione;
    }

    public Posizione[][] getGriglia() {
        return griglia;
    }

    public void posizionaNavi(Nave[] navi, Scanner scanner) {
        for (Nave nave : navi) {
            System.out.println();
            print();
            System.out.println();
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
                    scanner.nextLine();
                }
            }
            posizionaNave(nave, posizione, direzione);
        }
        print();
    }

    public void posizionaNavi(Nave[] navi) {
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

    public void sparaColpo(Posizione posizione) {
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (griglia[riga][colonna].getOccupazione()) {
            case NAVE:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                griglia[riga][colonna].setTipoNave(null);
                break;
            case ACQUA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
            case COLPITA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                griglia[riga][colonna].setTipoNave(null);
                break;
            case MANCATA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
        }
    }

    public void sparaColpo() {
        Posizione posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (griglia[riga][colonna].getOccupazione()) {
            case NAVE:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                griglia[riga][colonna].setTipoNave(null);
                break;
            case ACQUA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
            case COLPITA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                griglia[riga][colonna].setTipoNave(null);
                break;
            case MANCATA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
        }
    }

    public void checkColpo(Posizione posizione, Griglia grigliaComputer) {
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (grigliaComputer.getGriglia()[riga][colonna].getOccupazione()) {
            case NAVE:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                break;
            case ACQUA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
            case COLPITA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                break;
            case MANCATA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                break;
        }
    }

    public void checkAffondate() {
        // stampa il tipoNave di ogni posizione
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++)
                System.out.print(griglia[i][j].getTipoNave() + " ");
            System.out.println();
        }

        for (Nave nave : navi) {
            if (!nave.isAffondata()) {
                boolean trovata = false;
                for (int i = 0; i < dimensione; i++) {
                    for (int j = 0; j < dimensione; j++) {
                        if (griglia[i][j].getTipoNave() == nave.getTipo() && griglia[i][j].getOccupazione() == Posizione.Occupazione.NAVE) {
                            trovata = true;
                        }
                    }
                }
                if (!trovata)
                    nave.affonda();
            }
        }
    }

    public boolean naviTutteAffondate() {
        for (Nave nave : navi) {
            if (!nave.isAffondata())
                return false;
        }
        return true;
    }

    public void printRecapNavi() {
        for (Nave nave : navi)
            System.out.println(nave.getTipo() + " (" + nave.getDimensione() + "): " + (nave.isAffondata() ? "Affondata" : "Integra"));
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
                    case COLPITA:
                        System.out.print(Display.ANSI_RED + Display.COLPITA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    case MANCATA:
                        System.out.print(Display.ANSI_WHITE + Display.MANCATA_CHAR + Display.ANSI_RESET + " ");
                        break;
                    default:
                        System.out.print("? ");
                }
            }
            System.out.println();
        }
    }
}
