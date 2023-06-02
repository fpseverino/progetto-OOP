//
//  Main.java
//  progetto-OOP
//
//  Created by Francesco Paolo Severino, Daniele Campisi and Roberto Giovanni Scolari on 31/05/23.
//

import java.util.Scanner;

public class Main {
    static public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Partita partita = new Partita(5, 3);
        partita.initNavi(scanner);
        Griglia griglia = partita.getGiocatore().getGriglia();
        griglia.stampa();
        griglia.posizionaNavi(partita.getNavi(), scanner);
        scanner.close();
    }
}
