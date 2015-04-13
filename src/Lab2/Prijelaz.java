package Lab2;


/**
 * Created by borna on 3/26/15.
 */
public class Prijelaz {
    private String ulaznoStanje;
    private String pobuda;
    private String novoStanje;
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
            novoStanje = s;
        }
    }

    public String getUlaznoStanje() {
         return ulaznoStanje;
    }

    public String getPobuda() {
        return pobuda;
    }

    public String getNovoStanje() {
        return novoStanje;
    }

    public void setNovoStanje(String novoStanje) {
        this.novoStanje = novoStanje;
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
        return ulaznoStanje + "," + pobuda + "->" + novoStanje + "\n";
    }
}