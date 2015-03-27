import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by borna on 3/18/15.
 */
public class SimEnka {

    public static List<String> prihvatljivaStanja = new ArrayList<String>();
    public static List<Prijelaz> prijelazi = new ArrayList<Prijelaz>();

    public static void main(String[] args) throws IOException {

//        BufferedReader br = new BufferedReader (new FileReader(new File("/Users/borna/Desktop/borna.txt")) );
//
//        String buffer = br.readLine();
//        while (buffer != (null)) {
//            System.out.println(buffer+"\n");
//            buffer = br.readLine();
//        }


        Automat automat = new Automat();
        inicijalizirajAutomat();


        automat.obaviPosao();
        automat.print();



    }

    public static void inicijalizirajAutomat() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<String> ulazniNizovi = new LinkedList();
        List<String> ulazniSimboli = new LinkedList();

        String buffer;

        buffer = reader.readLine();
        String[] ulaz =  buffer.split("|");          //odvajanje ulaznih nizova znakom |

        for (String ul : ulaz) {
            ulazniNizovi.add(ul);
        }

        int i = 0;
        for (String ulazniNiz : ulazniNizovi) {            //odvajanje simbola unutar nizova
            String[] simboli = ulazniNiz.split(",");
            for (String ul : simboli) {
                ulazniSimboli.add(ul);
            }
        }


        // citanje preostalih prijelaza do kraja datoteke
        buffer = reader.readLine();
        while (buffer != null) {
            prijelazi.add(new Prijelaz(buffer));

            buffer = reader.readLine();
        }
    }
}
