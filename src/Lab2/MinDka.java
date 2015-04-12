package Lab2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by borna on 4/2/15.
 */
public class MinDka {

    public static List<Prijelaz> originalniprijelazi = new ArrayList<Prijelaz>();
    public static List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
    public static List<String> listaDohvatljivihStanja;
    public static List<String> skupPrihvatljivihStanja = new ArrayList<String>();
    public static List<String> novaPrihvatljivaStanja = new ArrayList<String>();
    public static List<String> totalStanja = new ArrayList<String>();
    public static String[] ulSimboli;
    public static String printString = "";
    public static String pocetnoStanje;


    public static void main(String[] args) throws IOException {

        inicijalizirajAutomat();
        obrisiNedohvatljivaStanja();
        updatePrintingString();
        System.out.println(printString);

    }


    public static void inicijalizirajAutomat() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String buffer;


        // 1. red - citanje ulaznih stanja
        do {
            buffer = reader.readLine();
        } while (buffer == null);
        String[] stanja = buffer.split(",");
        for (String st : stanja) {
            totalStanja.add(st);
        }


        // 2. red - citanje ulaznih simbola
        do {
            buffer = reader.readLine();
        } while (buffer == null);
        ulSimboli = buffer.split(",");

        // 3. red - citanje prihvatljivih stanja
        do {
            buffer = reader.readLine();
        } while (buffer == null);
        String[] poljePrihvatljivihStanja = buffer.split(",");

        for (String stanje : poljePrihvatljivihStanja) {
            skupPrihvatljivihStanja.add(stanje);
        }

        // 4. red - citanje pocetnih stanja
        do {
            buffer = reader.readLine();
        } while (buffer == null);
        pocetnoStanje = buffer;

        // citanje preostalih prijelaza do kraja datoteke
        do {
            buffer = reader.readLine();
        } while (buffer == null);
        while (!buffer.isEmpty()) {
            originalniprijelazi.add(new Prijelaz(buffer));
            buffer = reader.readLine();
        }
    }

    /** Metoda brise nedohvatljiva stanja stvarajuci
    *  listu prihvatljivih stanja, odnosno onih do kojih
    *  se moze doci iz pocetnog stanja, te na temelju
    *  usporedbe s originalnom listom stanja stvara novu
    *  listu nedohvatljivih stanja. Pomocu liste nedohvatljivih
    *  stanja brisu se unosi iz lista koje definiraju prijelaze
    *  automata (prthodnoStanje, procitaniZnak, sljedeceStanje)
    *  i na kraju mijenja originalnu listu stanja.
    */

    private static void obrisiNedohvatljivaStanja() {
        Set<String> setDohvatljivaStanja = new TreeSet<String>();
        setDohvatljivaStanja.add(pocetnoStanje);

        //stvori listu dohvatljivih stanja - onih do kojih se moze doci iz pocetnog

        for (int i = 0 ; i < originalniprijelazi.size(); i++) {
            for (Prijelaz prijelaz1 : originalniprijelazi) {
                if (setDohvatljivaStanja.contains(prijelaz1.getUlaznoStanje())) {
                    for(String s : prijelaz1.getNovaStanja()) {
                        setDohvatljivaStanja.add(s);
                    }
                }
            }
        }

        listaDohvatljivihStanja = new ArrayList<String>(setDohvatljivaStanja);
        Collections.sort(listaDohvatljivihStanja);

        /**
         * brisanje prijelaza kojima je pocetno stanje nedohvatiljivo
         */
        for (Prijelaz prijelaz : originalniprijelazi) {
            if (listaDohvatljivihStanja.contains(prijelaz.getUlaznoStanje())) {
                noviPrijelazi.add(prijelaz);
            }
        }
        /**
         * brisanje prihvatljivih stanja koja su nedohvatljiva
         */

        for (String prihvatljivoStanje : skupPrihvatljivihStanja) {
            if (listaDohvatljivihStanja.contains(prihvatljivoStanje)){
                novaPrihvatljivaStanja.add(prihvatljivoStanje);
            }
        }
    }


    private static void updatePrintingString() {

        // 1. red sva stanja
        String[] svaStanja = listaDohvatljivihStanja.toArray(new String[listaDohvatljivihStanja.size()]);

        for (int i = 0; i < svaStanja.length-1; i++) {
            printString = printString.concat(svaStanja[i]+",");
        }
        printString = printString.concat(svaStanja[svaStanja.length-1]+"\n");

        // 2. red iste pobude
        for (int i = 0; i < ulSimboli.length-1; i++) {
            printString = printString.concat(ulSimboli[i]+",");
        }
        printString = printString.concat(ulSimboli[ulSimboli.length-1]+"\n");

        // 3. red nova prihvatljiva stanja
        String[] prihvatljiva = novaPrihvatljivaStanja.toArray(new String[novaPrihvatljivaStanja.size()]);
        for (int i = 0; i < prihvatljiva.length-1; i++) {
            printString = printString.concat(prihvatljiva[i]+",");
        }
        printString = printString.concat(prihvatljiva[prihvatljiva.length-1]+"\n");

        // 4. red isto pocetno stanje
        printString = printString.concat(pocetnoStanje+"\n");

        // 5. red++ prijelazi

        for (Prijelaz prij : noviPrijelazi) {
            printString = printString.concat(prij.toString());
        }
    }
}