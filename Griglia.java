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
        for (int i = 0; i < dimensione; i++)
            for (int j = 0; j < dimensione; j++)
                griglia[i][j] = new Posizione(i, j);
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
            print();
            System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
            String input = scanner.nextLine();
            while (input.length() < 2 || !Character.isLetter(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
                System.out.println("La posizione non è valida");
                System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
                input = scanner.nextLine();
            }
            char c = input.charAt(0);
            int num = Integer.parseInt(input.substring(1));
            Posizione posizione = new Posizione(c, num);
            System.out.print("Inserisci la direzione della nave (Verticale/Orizzontale): ");
            Direzione direzione = Direzione.fromString(scanner.nextLine());
            while (!isPosizioneValida(nave, posizione, direzione)) {
                System.out.println("La posizione non è valida");
                System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
                input = scanner.nextLine();
                while (input.length() != 2 || !Character.isLetter(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
                    System.out.println("La posizione non è valida");
                    System.out.print("Inserisci la posizione della nave " + nave.getTipo() + " (lunghezza " + nave.getDimensione() + "): ");
                    input = scanner.nextLine();
                }
                c = input.charAt(0);
                num = Integer.parseInt(input.substring(1));
                posizione = new Posizione(c, num);
                System.out.print("Inserisci la direzione della nave (Verticale/Orizzontale): ");
                direzione = Direzione.fromString(scanner.nextLine());
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
                griglia[i][colonna].setCasella(Posizione.Casella.NAVE);
                griglia[i][colonna].setTipoNave(nave.getTipo());
            }
        } else {
            for (int i = colonna; i < colonna + dimensione; i++) {
                griglia[riga][i].setCasella(Posizione.Casella.NAVE);
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
                if (griglia[i][colonna].getCasella() == Posizione.Casella.NAVE)
                    return false;
        } else {
            if (colonna + dimensione > this.dimensione)
                return false;
            for (int i = colonna; i < colonna + dimensione; i++)
                if (griglia[riga][i].getCasella() == Posizione.Casella.NAVE)
                    return false;
        }
        return true;
    }

    public void sparaColpo(Posizione posizione) {
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (griglia[riga][colonna].getCasella()) {
            case NAVE:
                griglia[riga][colonna].setTipoNave(null);
                griglia[riga][colonna].setCasella(Posizione.Casella.COLPITA);
                break;
            case ACQUA:
                griglia[riga][colonna].setCasella(Posizione.Casella.MANCATA);
                break;
            default:
                break;
        }
    }

    public void sparaColpo() {
        Posizione posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (griglia[riga][colonna].getCasella()) {
            case NAVE:
                griglia[riga][colonna].setTipoNave(null);
                griglia[riga][colonna].setCasella(Posizione.Casella.COLPITA);
                break;
            case ACQUA:
                griglia[riga][colonna].setCasella(Posizione.Casella.MANCATA);
                break;
            default:
                break;
        }
    }

    public void checkColpo(Posizione posizione, Griglia grigliaComputer) {
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        switch (grigliaComputer.getGriglia()[riga][colonna].getCasella()) {
            case NAVE:
                griglia[riga][colonna].setCasella(Posizione.Casella.COLPITA);
                break;
            case ACQUA:
                griglia[riga][colonna].setCasella(Posizione.Casella.MANCATA);
                break;
            case COLPITA:
                griglia[riga][colonna].setCasella(Posizione.Casella.COLPITA);
                break;
            case MANCATA:
                griglia[riga][colonna].setCasella(Posizione.Casella.MANCATA);
                break;
        }
    }

    public void checkAffondate() {
        for (Nave nave : navi) {
            if (!nave.isAffondata()) {
                boolean trovata = false;
                for (int i = 0; i < dimensione; i++)
                    for (int j = 0; j < dimensione; j++)
                        if (griglia[i][j].getTipoNave() == nave.getTipo() && griglia[i][j].getCasella() == Posizione.Casella.NAVE)
                            trovata = true;
                if (!trovata)
                    nave.affonda();
            }
        }
    }

    public boolean naviTutteAffondate() {
        for (Nave nave : navi)
            if (!nave.isAffondata())
                return false;
        return true;
    }

    public void printRecapNavi(String header) {
        System.out.println(header);
        for (Nave nave : navi)
            System.out.println(nave.getTipo() + " (" + nave.getDimensione() + "): " + (nave.isAffondata() ? "Affondata" : "Integra"));
        System.out.println();
    }

    public void print() {
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < dimensione; i++)
            System.out.print((char) ('A' + i) + " ");
        System.out.println();
        for (int i = 0; i < dimensione; i++) {
            System.out.print(i + 1 + " ");
            if (i < 9) System.out.print(" ");
            for (int j = 0; j < dimensione; j++) {
                String casella = "";
                switch (griglia[i][j].getCasella()) {
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
                System.out.print(casella + griglia[i][j].getCasella().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
