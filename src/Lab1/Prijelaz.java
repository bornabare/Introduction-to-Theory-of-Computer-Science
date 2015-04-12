package Lab1;

import java.util.Arrays;
import java.util.List;

/**
 * Created by borna on 3/26/15.
 */
public class Prijelaz {
    private String ulaznoStanje;
    private String pobuda;
    private List<String> novaStanja;
    private String izraz;

    public Prijelaz(String izraz) {
        this.izraz = izraz;
    }

    public String getUlaznoStanje() {
        return lijevaStrana().split(",")[0];
    }

    public String getPobuda() {
        return lijevaStrana().split(",")[1];
    }

    public List<String> getNovaStanja() {
        return Arrays.asList(desnaStrana().split(","));
    }

    private String lijevaStrana() {
        return izraz.split("->")[0];
    }

    private String desnaStrana() {
        return izraz.split("->")[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prijelaz) {
            Prijelaz prijelaz = (Prijelaz)obj;
            return prijelaz.getUlaznoStanje().equals(getUlaznoStanje()) && prijelaz.getPobuda().equals(getPobuda());
        }
        return false;
    }
}