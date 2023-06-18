//
//  Posizione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

/**
 * Rappresenta una posizione all'interno di una griglia.
 * La posizione è identificata da una colonna e una riga.
 * Ogni posizione può essere occupata da una nave o essere vuota (acqua).
 */
public class Posizione implements java.io.Serializable {
    private final int colonna;
    private final int riga;
    private String nomeNave; // Il nome della nave che occupa la posizione, se presente

    /**
     * Enumerazione che rappresenta le possibili occupazioni di una posizione.
     */
    public enum Occupazione {
        /**
         * Posizione vuota (acqua).
         */
        ACQUA('~'),

        /**
         * Posizione occupata da una nave.
         */
        NAVE('O'),

        /**
         * Posizione occupata da una nave colpita.
         */
        MANCATA('X'),

        /**
         * Posizione vuota su cui è stato sparato un colpo.
         */
        COLPITA('0');

        private final char label;

        /**
         * Costruttore privato per l'enumerazione Occupazione.
         *
         * @param label il carattere di rappresentazione dell'occupazione.
         */
        Occupazione(char label) {
            this.label = label;
        }

        /**
         * Restituisce il carattere di rappresentazione dell'occupazione.
         *
         * @return il carattere di rappresentazione.
         */
        public char getLabel() {
            return label;
        }
    }

    private Occupazione occupazione;

    /**
     * Costruttore della classe Posizione.
     *
     * @param colonna la colonna della posizione.
     * @param riga    la riga della posizione.
     * @throws PosizioneNonValidaException se la colonna o la riga sono negative.
     */
    public Posizione(int colonna, int riga) throws PosizioneNonValidaException {
        if (colonna < 0 || riga < 0)
            throw new PosizioneNonValidaException("La colonna e la riga devono essere maggiori o uguali a 0");
        this.colonna = colonna;
        this.riga = riga;
        this.occupazione = Occupazione.ACQUA;
        this.nomeNave = null;
    }

    /**
     * Costruttore alternativo che accetta una colonna come carattere e una riga.
     * La colonna deve essere una lettera maiuscola.
     *
     * @param colonna la colonna della posizione come carattere.
     * @param riga    la riga della posizione.
     * @throws PosizioneNonValidaException se la colonna o la riga sono negative o se la colonna non è una lettera maiuscola.
     */
    public Posizione(char colonna, int riga) throws PosizioneNonValidaException {
        this(Character.isLetter(colonna) ? Character.toUpperCase(colonna) - 'A' : -1, riga - 1);
    }

    /**
     * Restituisce la colonna della posizione.
     *
     * @return la colonna della posizione.
     */
    public int getColonna() {
        return colonna;
    }

    /**
     * Restituisce la riga della posizione.
     *
     * @return la riga della posizione.
     */
    public int getRiga() {
        return riga;
    }

    /**
     * Restituisce l'occupazione della posizione.
     *
     * @return l'occupazione della posizione.
     */
    public Occupazione getOccupazione() {
        return occupazione;
    }

    /**
     * Imposta lo stato di occupazione della posizione.
     * 
     * @param occupazione lo stato di occupazione da impostare
     */
    public void setOccupazione(Occupazione occupazione) {
        this.occupazione = occupazione;
    }

    /**
     * Restituisce il nome della nave associata alla posizione.
     * 
     * @return il nome della nave
     */
    public String getNomeNave() {
        return nomeNave;
    }

    /**
     * Imposta il nome della nave associata alla posizione.
     * 
     * @param nomeNave il nome della nave da impostare
     */
    public void setNomeNave(String nomeNave) {
        this.nomeNave = nomeNave;
    }

    /**
     * Verifica se una stringa rappresenta una posizione valida (utilizzando una regex).
     * 
     * @param posizione la stringa da verificare
     * @return true se la stringa rappresenta una posizione valida, false altrimenti
     */
    public static boolean isPosizione(String posizione) {
        return posizione.matches("^[A-Za-z][1-9]([0-9])?$");
    }
}

/**
 * Eccezione che viene lanciata quando una posizione non è valida.
 */
class PosizioneNonValidaException extends Exception {
    /**
     * Crea un'istanza di PosizioneNonValidaException senza specificare un messaggio di errore.
     */
    public PosizioneNonValidaException() {}

    /**
     * Crea un'istanza di PosizioneNonValidaException con un messaggio di errore specificato.
     * 
     * @param message il messaggio di errore
     */
    public PosizioneNonValidaException(String message) {
        super(message);
    }
}