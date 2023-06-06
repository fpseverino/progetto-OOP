//
//  Direzione.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 02/06/23.
//

import java.util.Scanner;

public enum Direzione {
    ORIZZONTALE,
    VERTICALE;
    static Scanner scanner = new Scanner(System.in);
    

    public static Direzione fromString(String direzione) throws IllegalArgumentException {
        if (direzione.toLowerCase().startsWith("o") || direzione.toLowerCase().startsWith("h")) {
            return ORIZZONTALE;
        } else if (direzione.toLowerCase().startsWith("v")) {
            return VERTICALE;
        } else {
             System.out.println("La direzione non è valida, inserisci una direzione valida");
                direzione = scanner.nextLine();
                while (!direzione.toLowerCase().startsWith("o") && !direzione.toLowerCase().startsWith("h") && !direzione.toLowerCase().startsWith("v")) {
                    System.out.println("La direzione non è valida, inserisci una direzione valida");
                    direzione = scanner.nextLine();
                }
            }
            return fromString(direzione);
        }
    }
   
  
 


