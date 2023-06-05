//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave {
    private final String nome;
    private final int dimensione;
    private boolean affondata = false;

    public Nave(String nome, int dimensione) {
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
