//
//  Griglia.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

/**
 * La classe Griglia rappresenta una griglia per il gioco delle navi. 
 * Fornisce metodi per posizionare le navi, sparare alle posizioni e controllare lo stato della griglia.
 * La griglia è composta da posizioni, e ogni posizione può essere vuota o occupata da una nave.
 * La classe tiene traccia anche del punteggio del giocatore.
 */
public class Griglia implements java.io.Serializable {
    /**
     * La dimensione minima consentita per la griglia.
     */
    public static final int MIN_DIMENSIONE = 3;

    /**
     * La dimensione massima consentita per la griglia.
     */
    public static final int MAX_DIMENSIONE = 26;

    private final int dimensione;
    private Posizione[][] griglia;
    private Nave[] navi;
    private int punteggio = 0;

    /**
     * Costruisce una griglia con la dimensione e le navi specificate.
     *
     * @param dimensione la dimensione della griglia
     * @param navi       l'array di navi (scelte all'inizio della partita) da posizionare sulla griglia
     * @throws PosizioneNonValidaException se la dimensione della griglia non è valida
     * @throws GrigliaNonValidaException   se la griglia non è valida
     */
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

    /**
     * Restituisce la dimensione della griglia.
     *
     * @return la dimensione della griglia
     */
    public int getDimensione() {
        return dimensione;
    }

    /**
     * Restituisce l'array della griglia.
     *
     * @return l'array della griglia
     */
    public Posizione[][] getGriglia() {
        return griglia;
    }

    /**
     * Restituisce il punteggio del giocatore.
     *
     * @return il punteggio del giocatore
     */
    public int getPunteggio() {
        return punteggio;
    }

    /**
     * Posiziona le navi sulla griglia in base all'input dell'utente.
     *
     * @param navi    l'array di navi (scelte all'inizio della partita) da posizionare sulla griglia
     * @param scanner l'oggetto scanner utilizzato per l'input dell'utente
     */
    public void posizionaNavi(Nave[] navi, Scanner scanner) {
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

    /**
     * Posiziona le navi sulla griglia in modo casuale (metodo usato dal computer).
     *
     * @param navi l'array di navi (scelte all'inizio della partita) da posizionare sulla griglia
     */
    public void posizionaNavi(Nave[] navi) {
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

    /**
     * Posiziona una nave sulla griglia.
     *
     * @param nave       la nave da posizionare
     * @param posizione  la posizione iniziale della nave
     * @param direzione  la direzione della nave (verticale o orizzontale)
     */
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

    /**
     * Verifica se una determinata posizione è valida per posizionare una nave sulla griglia.
     *
     * @param nave       La nave da posizionare.
     * @param posizione  La posizione di partenza della nave.
     * @param direzione  La direzione della nave.
     * @return True se la posizione è valida, False altrimenti.
     */
    public boolean isPosizioneValida(Nave nave, Posizione posizione, Direzione direzione) {
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

    /**
     * Verifica se una determinata posizione è valida per sparare o se si è già sparato in quella posizione.
     *
     * @param posizione La posizione da verificare.
     * @return True se la posizione è valida, False altrimenti.
     */
    public boolean isPosizioneValida(Posizione posizione) {
        int colonna = posizione.getColonna();
        int riga = posizione.getRiga();
        if (colonna < 0 || colonna >= dimensione || riga < 0 || riga >= dimensione)
            return false;
        return griglia[riga][colonna].getOccupazione() == Posizione.Occupazione.ACQUA || griglia[riga][colonna].getOccupazione() == Posizione.Occupazione.NAVE;
    }

    /**
     * Spara un colpo in una determinata posizione.
     *
     * @param posizione La posizione in cui sparare.
     * @throws PosizioneNonValidaException se la posizione non è valida.
     */
    public void sparaColpo(Posizione posizione) throws PosizioneNonValidaException {
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

    /**
     * Spara un colpo in una posizione casuale sulla griglia (metodo utilizzato dal computer).
     *
     * @throws PosizioneNonValidaException se la posizione generata casualmente non è valida.
     */
    public void sparaColpo() throws PosizioneNonValidaException {
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

    /**
     * Copia il colpo sparato sulla griglia colpi dell'utente nella griglia del computer.
     *
     * @param posizione       La posizione in cui è stato sparato il colpo.
     * @param grigliaComputer La griglia del computer avversario.
     */
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

    /**
     * Controlla se ci sono navi affondate e le marca come affondate se nessuna posizione è più occupata dalla nave.
     */
    public void checkAffondate() {
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

    /**
     * Verifica se tutte le navi sono affondate.
     *
     * @return true se tutte le navi sono affondate, false altrimenti.
     */
    public boolean naviTutteAffondate() {
        for (Nave nave : navi)
            if (!nave.isAffondata())
                return false;
        return true;
    }

    /**
     * Stampa un riepilogo delle navi sulla griglia.
     *
     * @param header L'intestazione del riepilogo.
     */
    public void printRecapNavi(String header) {
        System.out.println(header);
        for (Nave nave : navi)
            System.out.println(nave.getNome() + " (" + nave.getDimensione() + "): " + (nave.isAffondata() ? "Affondata" : "Integra"));
        System.out.println();
    }

    /**
     * Stampa la griglia con le posizioni delle navi e i colpi sparati.
     */
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

/**
 * Eccezione che viene lanciata quando viene creata una griglia non valida.
 */
class GrigliaNonValidaException extends Exception {
    public GrigliaNonValidaException(String message) {
        super(message);
    }
}