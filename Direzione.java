//
//  Direzione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 02/06/23.
//

public enum Direzione {
    ORIZZONTALE,
    VERTICALE;

    public static Direzione fromString(String s) throws IllegalArgumentException {
        if (s.toLowerCase().equals("orizzontale")) return ORIZZONTALE;
        else if (s.toLowerCase().equals("verticale")) return VERTICALE;
        else throw new IllegalArgumentException("La stringa " + s + " non rappresenta una direzione valida");
    }

    public static Direzione fromChar(char c) throws IllegalArgumentException {
        if (c == 'o' || c == 'h' || c == 'O' || c == 'H') return ORIZZONTALE;
        else if (c == 'v' || c == 'V') return VERTICALE;
        else throw new IllegalArgumentException("Il carattere " + c + " non rappresenta una direzione valida");
    }
}
