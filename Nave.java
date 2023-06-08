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

    public Nave(String nome, int dimensione) throws IllegalArgumentException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Il nome della nave non può essere nullo o vuoto");
        if (dimensione < 1)
            throw new IllegalArgumentException("La dimensione della nave deve essere maggiore di 0");
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
