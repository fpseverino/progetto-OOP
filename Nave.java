//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave implements java.io.Serializable {
    private final String nome;
    private final int dimensione;
    private boolean affondata = false;

    public Nave(String nome, int dimensione) throws NaveNonValidaException {
        if (nome == null || nome.isBlank())
            throw new NaveNonValidaException("Il nome della nave non può essere nullo o vuoto");
        if (dimensione < 1)
            throw new NaveNonValidaException("La dimensione della nave deve essere maggiore di 0");
        this.nome = nome;
        this.dimensione = dimensione;
    }

    public String getNome() {
        return nome;
    }

    public int getDimensione() {
        return dimensione;
    }

    public boolean isAffondata() {
        return affondata;
    }

    public void affonda() {
        this.affondata = true;
    }
}

class NaveNonValidaException extends Exception {
    public NaveNonValidaException() {}

    public NaveNonValidaException(String message) {
        super(message);
    }
}