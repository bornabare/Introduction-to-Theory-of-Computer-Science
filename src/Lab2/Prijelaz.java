package Lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by borna on 3/26/15.
 */
public class Prijelaz {
    private String ulaznoStanje;
    private String pobuda;
    private List<String> novaStanja = new ArrayList<String>();
    private String izraz;

    public Prijelaz(String izraz) {
        this.izraz = izraz;
        izdvojiParametre();
    }

    public void izdvojiParametre() {
        String[] prvo = izraz.split("->");
        String[] livaStrana = prvo[0].split(",");
        ulaznoStanje=livaStrana[0];
        pobuda=livaStrana[1];
        String[] stanja = prvo[1].split(",");
        for (String s : stanja) {
            novaStanja.add(s);
        }
    }

    public String getUlaznoStanje() {
         return ulaznoStanje;
    }

    public String getPobuda() {
        return pobuda;
    }

    public List<String> getNovaStanja() {
        return novaStanja;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prijelaz) {
            Prijelaz prijelaz = (Prijelaz)obj;
            return prijelaz.getUlaznoStanje().equals(getUlaznoStanje()) && prijelaz.getPobuda().equals(getPobuda());
        }
        return false;
    }

    @Override
    public String toString() {
        return ulaznoStanje + "," + pobuda + "->" + novaStanja.toArray()[0] + "\n";
    }
}