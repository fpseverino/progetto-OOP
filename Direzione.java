//
//  Direzione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 02/06/23.
//

public enum Direzione {
    ORIZZONTALE,
    VERTICALE;

    public static Direzione fromString(String direzione) throws IllegalArgumentException {
        if (direzione.toLowerCase().startsWith("o") || direzione.toLowerCase().startsWith("h")) {
            return ORIZZONTALE;
        } else if (direzione.toLowerCase().startsWith("v")) {
            return VERTICALE;
        } else {
            throw new IllegalArgumentException("Direzione non valida");
        }
    }
}

//denny pazzo non la smette di urlare e non mi fa concentrare a scrivere il codice per il progetto di OOP e mi sta facendo venire un mal di testa tremendo PORCO DIO