//
//  Direzione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 02/06/23.
//

/**
 * Enumerazione che rappresenta le possibili direzioni per la posizione delle navi.
 */
public enum Direzione {
    ORIZZONTALE,
    VERTICALE;
    /**
     * Converte una stringa nel corrispondente valore di Direzione.
     *
     * @param direzione la stringa da convertire
     * @return il valore di Direzione corrispondente alla stringa
     * @throws DirezioneNonValidaException se la stringa non corrisponde a una direzione valida
     */
    public static Direzione fromString(String direzione) throws DirezioneNonValidaException {
        if (direzione.toLowerCase().startsWith("o") || direzione.toLowerCase().startsWith("h")) {
            return ORIZZONTALE;
        } else if (direzione.toLowerCase().startsWith("v")) {
            return VERTICALE;
        } else {
            throw new DirezioneNonValidaException();
        }
    }
}

/**
 * Eccezione che viene lanciata quando la direzione non è valida.
 */
class DirezioneNonValidaException extends Exception {
     /**
     * Costruttore di default che imposta il messaggio di errore predefinito.
     */
    public DirezioneNonValidaException() {
        super("Direzione non valida");
    }
}
