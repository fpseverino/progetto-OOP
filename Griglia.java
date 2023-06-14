//
//  Griglia.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Griglia implements java.io.Serializable {
    public static final int MIN_DIMENSIONE = 3;
    public static final int MAX_DIMENSIONE = 26;

    private final int dimensione;
    private Posizione[][] griglia;
    private Nave[] navi;
    private int punteggio = 0;

    public Griglia(int dimensione, Nave[] navi) throws PosizioneNonValidaException, GrigliaNonValidaException {
        if (dimensione < MIN_DIMENSIONE || dimensione > MAX_DIMENSIONE)
            throw new GrigliaNonValidaException("La dimensione della griglia deve essere compresa tra " + MIN_DIMENSIONE + " e " + MAX_DIMENSIONE);
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

    public int getPunteggio() {
        return punteggio;
    }

    public void posizionaNavi(Nave[] navi, Scanner scanner) {
        // Posizionamento delle navi dell'utente
        for (Nave nave : navi) {
            print();
            String input;
            char c;
            int num;
            Posizione posizione = null;
            Direzione direzione = null;
            while (!isPosizioneValida(nave, posizione, direzione)) {
                System.out.print("Inserisci la posizione della nave " + nave.getNome() + " (lunghezza " + nave.getDimensione() + "): ");
                input = scanner.nextLine();
                while (!Posizione.isPosizione(input)) {
                    System.out.println("La posizione non è valida");
                    System.out.print("Inserisci la posizione della nave " + nave.getNome() + " (lunghezza " + nave.getDimensione() + "): ");
                    input = scanner.nextLine();
                }
                c = input.charAt(0);
                num = Integer.parseInt(input.substring(1));
                try {
                    posizione = new Posizione(c, num);
                } catch (PosizioneNonValidaException e) { System.out.println(e.getMessage()); }
                if (nave.getDimensione() == 1) {
                    direzione = Direzione.VERTICALE; // La direzione non è importante per le navi di dimensione 1
                } else {
                    boolean direzioneValida = false;
                    while (!direzioneValida) {
                        try {
                            System.out.print("Inserisci la direzione della nave " + nave.getNome() + " (Verticale/Orizzontale): ");
                            direzione = Direzione.fromString(scanner.nextLine());
                            direzioneValida = true;
                        } catch (DirezioneNonValidaException e) { System.out.println(e.getMessage());; }
                    }
                }
                if (!isPosizioneValida(nave, posizione, direzione)) System.out.println("La posizione non è valida");
            }
            posizionaNave(nave, posizione, direzione);
        }
        print();
    }

    public void posizionaNavi(Nave[] navi) {
        // Posizionamento delle navi del computer
        for (Nave nave : navi) {
            try {
                Posizione posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
                Direzione direzione = Direzione.values()[(int) (Math.random() * 2)];
                while (!isPosizioneValida(nave, posizione, direzione)) {
                    posizione = new Posizione((int) (Math.random() * dimensione), (int) (Math.random() * dimensione));
                    direzione = Direzione.values()[(int) (Math.random() * 2)];
                }
                posizionaNave(nave, posizione, direzione);
            } catch (PosizioneNonValidaException e) { System.out.println(e.getMessage()); }
        }
    }

    public void posizionaNave(Nave nave, Posizione posizione, Direzione direzione) {
        int dimensioneNave = nave.getDimensione();
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (direzione == Direzione.VERTICALE) {
            for (int i = riga; i < riga + dimensioneNave; i++) {
                griglia[i][colonna].setOccupazione(Posizione.Occupazione.NAVE);
                griglia[i][colonna].setNomeNave(nave.getNome());
            }
        } else {
            for (int i = colonna; i < colonna + dimensioneNave; i++) {
                griglia[riga][i].setOccupazione(Posizione.Occupazione.NAVE);
                griglia[riga][i].setNomeNave(nave.getNome());
            }
        }
    }

    public boolean isPosizioneValida(Nave nave, Posizione posizione, Direzione direzione) {
        // Controlla che sia una posizione valida per posizionare la nave
        if (posizione == null || direzione == null)
            return false;
        int dimensioneNave = nave.getDimensione();
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (colonna < 0 || colonna >= this.dimensione || riga < 0 || riga >= this.dimensione)
            return false;
        if (dimensioneNave == 1)
            return griglia[riga][colonna].getOccupazione() == Posizione.Occupazione.ACQUA;
        if (direzione == Direzione.VERTICALE) {
            if (riga + dimensioneNave > this.dimensione)
                return false;
            for (int i = riga; i < riga + dimensioneNave; i++)
                if (griglia[i][colonna].getOccupazione() == Posizione.Occupazione.NAVE)
                    return false;
        } else {
            if (colonna + dimensioneNave > this.dimensione)
                return false;
            for (int i = colonna; i < colonna + dimensioneNave; i++)
                if (griglia[riga][i].getOccupazione() == Posizione.Occupazione.NAVE)
                    return false;
        }
        return true;
    }

    public boolean isPosizioneValida(Posizione posizione) {
        // Controlla che sia una posizione valida per sparare
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (colonna < 0 || colonna >= dimensione || riga < 0 || riga >= dimensione)
            return false;
        return griglia[riga][colonna].getOccupazione() == Posizione.Occupazione.ACQUA || griglia[riga][colonna].getOccupazione() == Posizione.Occupazione.NAVE;
    }

    public void sparaColpo(Posizione posizione) throws PosizioneNonValidaException {
        // Spara un colpo alla posizione specificata dall'utente
        if (!isPosizioneValida(posizione))
            throw new PosizioneNonValidaException("Posizione non valida");
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (colonna < 0 || colonna >= dimensione || riga < 0 || riga >= dimensione)
            return;
        switch (griglia[riga][colonna].getOccupazione()) {
            case NAVE:
                griglia[riga][colonna].setNomeNave(null);
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                punteggio += 5;
                break;
            case ACQUA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                punteggio -= 2;
                break;
            default:
                break;
        }
    }

    public void sparaColpo() throws PosizioneNonValidaException {
        // Spara un colpo in una posizione casuale (usato dal computer)
        int colonna = (int) (Math.random() * dimensione);
        int riga = (int) (Math.random() * dimensione);
        if (!isPosizioneValida(new Posizione(riga, colonna))) {
            sparaColpo();
            return;
        }
        switch (griglia[riga][colonna].getOccupazione()) {
            case NAVE:
                griglia[riga][colonna].setNomeNave(null);
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.COLPITA);
                punteggio += 5;
                break;
            case ACQUA:
                griglia[riga][colonna].setOccupazione(Posizione.Occupazione.MANCATA);
                punteggio -= 2;
                break;
            default:
                break;
        }
    }

    public void checkColpo(Posizione posizione, Griglia grigliaComputer) {
        // Copia il colpo sparato sulla griglia colpi dell'utente nella griglia del computer
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
        // Setta le navi affondate
        for (Nave nave : navi) {
            if (!nave.isAffondata()) {
                boolean trovata = false;
                for (int i = 0; i < dimensione; i++)
                    for (int j = 0; j < dimensione; j++)
                        if (griglia[i][j].getNomeNave() == nave.getNome() && griglia[i][j].getOccupazione() == Posizione.Occupazione.NAVE)
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
            System.out.println(nave.getNome() + " (" + nave.getDimensione() + "): " + (nave.isAffondata() ? "Affondata" : "Integra"));
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
                switch (griglia[i][j].getOccupazione()) {
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
                System.out.print(casella + griglia[i][j].getOccupazione().getLabel() + Display.ANSI_RESET + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class GrigliaNonValidaException extends Exception {
    public GrigliaNonValidaException(String message) {
        super(message);
    }
}