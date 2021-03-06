package Lab1;

import java.io.*;
import java.util.*;

/**
 * Created by borna on 3/18/15.
 */
public class SimEnka {

    public static List<String> prihvatljivaStanja = new ArrayList<String>();
    public static List<Prijelaz> prijelazi = new ArrayList<Prijelaz>();
    public static List<String> ulazniNizovi = new ArrayList<String>();
    public static String printString = "";
    private static int trenutniBrojacAutomata = 1;
    private static int ukupniBrojAutomata;


    public static void main(String[] args) throws IOException {

        inicijalizirajAutomat();
        System.out.println(printString);

    }

    public static void inicijalizirajAutomat() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String buffer;


        // 1. red - citanje ulaznih nizova
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        String[] ulNizovi =  buffer.split("\\|");          //odvajanje ulaznih nizova znakom |
        ukupniBrojAutomata = ulNizovi.length;

        // 2. red - citanje skupova stanja
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        String[] skupStanja = buffer.split(",");

        // 3. red - citanje skupa simbola
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        String[] skupSimbola = buffer.split(",");

        // 4. red  - citanje prihvatljivih stanja
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        String[] prihvatljivaStanja = buffer.split(",");

        // 5. red - citanje pocetnih stanja
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        String[] pocetnaStanja = buffer.split(",");

        // citanje preostalih prijelaza do kraja datoteke
        do {
            buffer = reader.readLine();
        } while (buffer==null);
        while (buffer != null) {
            prijelazi.add (new Prijelaz(buffer));
            buffer = reader.readLine();
        }



        // Iteriranje po ulaznim nizovima odijeljenim po | i odvajanje u skupove simbola za svaki automat.
        // Nakon toga slijedi inicijalizacija svakog automata (tocno onoliko koliko ima ulaznih nizova) s inputom liste ulaznih simbola
        //

        List<String> ulazniSimboli = new ArrayList<String>();

        for (String ul : ulNizovi) {
            String[] ulSimboli = ul.split(",");

            ulazniSimboli.clear();

            for (String simbol : ulSimboli) {
                ulazniSimboli.add(simbol);
            }

            Automat automat = new Automat(ulazniSimboli, new ArrayList<String>(Arrays.asList(pocetnaStanja)));
            automat.obaviPosao();
        }
    }
}