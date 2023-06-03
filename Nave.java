//
//  Nave.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

public class Nave {
    private final String tipo;
    private final int dimensione;
    private boolean affondata = false;

    public Nave(String tipo, int dimensione) {
        this.tipo = tipo;
        this.dimensione = dimensione;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDimensione() {
        return dimensione;
    }

    public boolean isAffondata() {
        return affondata;
    }

    public void setAffondata(boolean affondata) {
        this.affondata = affondata;
    }
}
