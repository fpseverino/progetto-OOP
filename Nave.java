//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//
/**
 * Rappresenta una nave nel gioco della battaglia navale.
 */
public class Nave implements java.io.Serializable {
    private final String nome;
    private final int dimensione;
    private boolean affondata = false;
    /**
     * Crea una nuova istanza di Nave.
     *
     * @param nome       Il nome della nave.
     * @param dimensione La dimensione della nave.
     * @throws NaveNonValidaException se il nome è nullo o vuoto, o se la dimensione è inferiore a 1.
     */
    public Nave(String nome, int dimensione) throws NaveNonValidaException {
        if (nome == null || nome.isBlank())
            throw new NaveNonValidaException("Il nome della nave non può essere nullo o vuoto");
        if (dimensione < 1)
            throw new NaveNonValidaException("La dimensione della nave deve essere maggiore di 0");
        this.nome = nome;
        this.dimensione = dimensione;
    }
    /**
     * Restituisce il nome della nave.
     *
     * @return Il nome della nave.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Restituisce la dimensione della nave.
     *
     * @return La dimensione della nave.
     */
    public int getDimensione() {
        return dimensione;
    }
    /**
     * Verifica se la nave è affondata.
     *
     * @return true se la nave è affondata, false altrimenti.
     */
    public boolean isAffondata() {
        return affondata;
    }
    /**
     * Marca la nave come affondata.
     */
    public void affonda() {
        this.affondata = true;
    }
}
/**
 * Eccezione che viene lanciata quando viene creata una nave non valida.
 */
class NaveNonValidaException extends Exception {
    public NaveNonValidaException() {}

    public NaveNonValidaException(String message) {
        super(message);
    }
}