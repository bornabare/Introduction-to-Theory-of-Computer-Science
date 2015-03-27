import java.util.*;


/**
 * Created by borna on 3/26/15.
 */
public class Automat extends SimEnka{

    public List<String> pobude = new ArrayList<String>();
    private List<String> trenutnaStanja = new ArrayList<String>();
    private List<String> listaNovihStanja = new ArrayList<String>();
    private Automat noviAutomat;
    private String printAutomat = new String();


    public Automat(List<String> pobude) {
        this.pobude = pobude;
        printAutomat.concat(trenutnaStanja.toString());
    }

    public void obaviPosao() {
        stringZaPrint(trenutnaStanja);
        generirajNovaStanja();

        if (noviAutomat.pobude.size() > 0) {
            noviAutomat.obaviPosao();
        }

    }

    private String getPobuda() {
        return pobude.remove(0);
    }

    private void generirajNovaStanja() {
        String pobuda = getPobuda();

        listaNovihStanja.clear();
        for (Prijelaz prijelaz : prijelazi) {
            for (String nekoTrenutnoStanje : trenutnaStanja) {
                if (prijelaz.getUlaznoStanje().equals(nekoTrenutnoStanje) && prijelaz.getPobuda().equals(pobuda)){
                    for (String jednoNovoStanje : prijelaz.getNovaStanja()){
                        listaNovihStanja.add(jednoNovoStanje);
                    }

                }
            }
        }
        if (listaNovihStanja.isEmpty()){
            listaNovihStanja.add("#");
        }
        traziEpsilone(listaNovihStanja);

    }

    private void traziEpsilone(List<String> stanja) {
        for (String nekoTrenutnoStanje : stanja) {
            for (Prijelaz prijelaz : prijelazi) {
                if (prijelaz.getUlaznoStanje().equals(nekoTrenutnoStanje) && prijelaz.getPobuda().equals("$")){
                    for (String st : prijelaz.getNovaStanja()){
                        listaNovihStanja.add(st);
                    }
                }
            }
        }
    }

    public void stringZaPrint(List<String> trenutnaStanja){
        Collections.sort(trenutnaStanja);

        Iterator<String> iterator = trenutnaStanja.iterator();
        String zadnje = trenutnaStanja.remove(trenutnaStanja.size());
        trenutnaStanja.add(zadnje);

        printAutomat.concat("|");
        for (String stanje : trenutnaStanja){
            printAutomat.concat(stanje);
        }
    }

    public void print() {
        System.out.println(printAutomat);
    }
}
