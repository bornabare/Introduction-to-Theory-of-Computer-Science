import java.util.*;


/**
 * Created by borna on 3/26/15.
 */
public class Automat extends SimEnka{

    private List<String> ulazniSimboli = new ArrayList<String>();
    private List<String> trenutnaStanja = new ArrayList<String>();
    private List<String> listaNovihStanja = new ArrayList<String>();
    private Automat noviAutomat;

    public Automat(List <String> ulazniSimboli, List<String> trenutnaStanja) {
        this.ulazniSimboli = ulazniSimboli;
        this.trenutnaStanja = trenutnaStanja;
//        printAutomat.concat(trenutnaStanja.toString());
    }

    public void obaviPosao() {
        updatePrintingString (trenutnaStanja);
        generirajNovaStanja();

        if (ulazniSimboli.size() > 0) {
            obaviPosao();
        }

    }

    public String getSimbol() {
        return ulazniSimboli.remove(0);
    }

    public void generirajNovaStanja() {
        String pobuda = getSimbol();

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

    public void traziEpsilone(List<String> stanja) {
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

    public void updatePrintingString (List<String> trenutnaStanja2){
        //prebaci obicnu listu u array listu!
        ArrayList<String> trenutnaStanja = new ArrayList<String>(trenutnaStanja2);
        Collections.sort(trenutnaStanja);

        String[] poljeTrenutnihStanja = trenutnaStanja.toArray(new String[trenutnaStanja.size()]);
        int duljina = poljeTrenutnihStanja.length;

        int i = 0;
        for (; i < duljina-1; i++) {
            printString = printString.concat(poljeTrenutnihStanja[i] + ",");
        }
        if (i == duljina-1){
            printString = printString.concat(poljeTrenutnihStanja[duljina-1]);
        }

        if (ulazniSimboli.size() > 1) {
            printString = printString.concat("|");
        } else {
            printString.concat("\n");
        }

    }
}
