package Lab2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by borna on 4/2/15.
 */
public class MinDka {

    public static List<Prijelaz> pocetniPrijelazi = new ArrayList<Prijelaz>();
    public static List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
    public static List<String> novaDohvatljivaStanja;
    public static List<String> pocetnaPrihvatljivaStanja = new ArrayList<String>();
    public static List<String> novaPrihvatljivaStanja = new ArrayList<String>();
    public static List<String> pocetnaStanja = new ArrayList<String>();
    public static String[] ulSimboli;
    public static String printString = "";
    public static String pocetnoStanje;
    public static Map<String, List<String>> mapaIstovjetnosti = new HashMap<String, List<String>>();
    public static List<String> listaIstovjetnosti = new ArrayList<String>();


    public static void main(String[] args) throws IOException {

        inicijalizirajAutomat();
        obrisiNedohvatljivaStanja();
        brisiIstovjetnaStanja();
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
            pocetnaStanja.add(st);
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
            pocetnaPrihvatljivaStanja.add(stanje);
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
            pocetniPrijelazi.add(new Prijelaz(buffer));
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

        for (int i = 0 ; i < pocetniPrijelazi.size(); i++) {
            for (Prijelaz prijelaz1 : pocetniPrijelazi) {
                if (setDohvatljivaStanja.contains(prijelaz1.getUlaznoStanje())) {
                        setDohvatljivaStanja.add(prijelaz1.getNovoStanje());
                }
            }
        }

        novaDohvatljivaStanja = new ArrayList<String>(setDohvatljivaStanja);
        Collections.sort(novaDohvatljivaStanja);

        /**
         * brisanje prijelaza kojima je pocetno stanje nedohvatiljivo, tj. izrada nove liste sa novim dobrim prijelazima
         */
        for (Prijelaz prijelaz : pocetniPrijelazi) {
            if (novaDohvatljivaStanja.contains(prijelaz.getUlaznoStanje())) {
                noviPrijelazi.add(prijelaz);
            }
        }
        /**
         * brisanje prihvatljivih stanja koja su nedohvatljiva, tj. nova lista s prihvatljivim stanjima
         */

        for (String prihvatljivoStanje : pocetnaPrihvatljivaStanja) {
            if (novaDohvatljivaStanja.contains(prihvatljivoStanje)){
                novaPrihvatljivaStanja.add(prihvatljivoStanje);
            }
        }
    }

    private static void brisiIstovjetnaStanja() {
        Map<String, String> mapaStanja = new HashMap<String, String>();
        pocetniPrijelazi = new ArrayList<Prijelaz>(noviPrijelazi);
        pocetnaPrihvatljivaStanja = new ArrayList<String>(novaPrihvatljivaStanja);
        pocetnaStanja = new ArrayList<String>(novaDohvatljivaStanja);


//        noviPrijelazi = new ArrayList<Prijelaz>();
//        novaPrihvatljivaStanja = new ArrayList<String>();
//        novaDohvatljivaStanja = new ArrayList<String>();

        // upis svih mogucih parova stanja u hash tablicu
        String[] poljePocetnihStanja = pocetnaStanja.toArray(new String[pocetnaStanja.size()]);
        for (int i=0; i < poljePocetnihStanja.length; i++) {
            for (int j = i+1 ; j < poljePocetnihStanja.length; j++) {
                if(!poljePocetnihStanja[i].equals(poljePocetnihStanja[j])) {
                    mapaStanja.put(poljePocetnihStanja[i]+","+poljePocetnihStanja[j], null);
                }
            }
        }
        // X nisu istovjetna, 1 istovjetna su i brisu se!
        // prihvatljivo i neprihvatljivo stanje sigurno NECE biti istovjetno
        for (int i=0; i < poljePocetnihStanja.length; i++) {
            for (int j = i+1 ; j < poljePocetnihStanja.length; j++) {
                String stanje1 = poljePocetnihStanja[i];
                String stanje2 = poljePocetnihStanja[j];
                if ( stanje1.equals(stanje2) ) {
                    mapaStanja.replace(stanje1+","+stanje2, "X");
                }
                else if ((!pocetnaPrihvatljivaStanja.contains(stanje1) && pocetnaPrihvatljivaStanja.contains(stanje2)) || (pocetnaPrihvatljivaStanja.contains(stanje1) && !novaPrihvatljivaStanja.contains(stanje2))) {
                    mapaStanja.replace(stanje1+","+stanje2, "X");
                }
                    /**
                     * Po cijeloj hash tablici valja proci i ispitati jesu li stanja istovjetna na nacin da se pregledava
                     * jesu li za svaku pobudu konacna stanja jednaka. Ako nisu odmah se stavlja X
                     */
                else {
                        if (istovjetna(stanje1, stanje2)){
                            mapaStanja.replace(stanje1+","+stanje2, "1");
                        } else {
                            mapaStanja.replace(stanje1+","+stanje2, "X");
                        }
                }

            }
            Collections.sort(novaDohvatljivaStanja);
            Collections.sort(novaPrihvatljivaStanja);
        }

        /**
         * tablica sa istovjetnosti postoji! Sada treba izbrisati leksikografski drugo stanje. Dakle s1 zadrzati a s2 izbrisati
         * gdje god se u prijelazima pojavljuje drugo stanje kao novo stanje zamijeniti ga prvim. a gdje se pojavljuje kao prvo stanje
         * treba taj prijelaz obrisati
         */
        String ostavi;
        String zaBrisati;

        for (int i=0; i < poljePocetnihStanja.length; i++) {
            for (int j = i+1 ; j < poljePocetnihStanja.length; j++) {
                String stanje1 = poljePocetnihStanja[i];
                String stanje2 = poljePocetnihStanja[j];
                if (mapaStanja.get((String) stanje1+","+stanje2) != null) {
                    if (mapaStanja.get((String) stanje1 + "," + stanje2).equals("1")) {
                        if (stanje1.compareTo(stanje2) < 0) {
                            ostavi = stanje1;
                            zaBrisati = stanje2;
                        } else {
                            ostavi = stanje2;
                            zaBrisati = stanje1;
                        }


                        //ako je manje od nule string1 je veci, brisi string2
                        //izbrisati stanje2 iz prihvatljivih i dohvatljivih ako postoji!
                        if (pocetnaStanja.contains(zaBrisati)) {
                            novaDohvatljivaStanja.remove(zaBrisati);
                        }
                        if (pocetnaPrihvatljivaStanja.contains(zaBrisati)) {
                            novaPrihvatljivaStanja.remove(zaBrisati);
                        }
                        //for po prijelazima i mjenjaj getnovostanje umjesto stanje2 u stanje1
                        for (Prijelaz prijelaz : pocetniPrijelazi) {
                            if (prijelaz.getNovoStanje().equals(zaBrisati)) {
                                prijelaz.setNovoStanje(ostavi);
                            }
                            // ako je ulazno stanje2 brisi ga!
                            if (prijelaz.getUlaznoStanje().equals(zaBrisati)) {
                                noviPrijelazi.remove(prijelaz);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean istovjetna(String stanje1, String stanje2) {
        String novoStanje1 = new String("");
        String novoStanje2 = new String("");

        if (stanje1.equals(stanje2)){
            return true;
        }
        for (String simbol : ulSimboli) {
            for (Prijelaz prijelaz : noviPrijelazi) {
                if (prijelaz.getUlaznoStanje().equals(stanje1) && prijelaz.getPobuda().equals(simbol)) {
                    novoStanje1 = prijelaz.getNovoStanje();
                }
                if (prijelaz.getUlaznoStanje().equals(stanje2) && prijelaz.getPobuda().equals(simbol)) {
                    novoStanje2 = prijelaz.getNovoStanje();
                }
            }
        }

        if ( !(novoStanje1.equals("") || novoStanje2.equals("")) ) {
            mapaIstovjetnosti.put(novoStanje1+","+novoStanje2, );
        }
        else {
            return false;
        }
    }


    private static void updatePrintingString() {

        // 1. red sva stanja
        String[] svaStanja = novaDohvatljivaStanja.toArray(new String[novaDohvatljivaStanja.size()]);

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