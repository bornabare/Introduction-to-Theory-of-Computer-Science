package Lab1;

import java.util.*;


/**
 * Created by borna on 3/26/15.
 */
public class Automat extends SimEnka{

    private List<String> ulazniSimboli = new ArrayList<String>();
    private List<String> trenutnaStanja = new ArrayList<String>();
    private List<String> listaNovihStanja = new ArrayList<String>();

    public Automat(List<String> ulazniSimboli, List<String> trenutnaStanja) {
        this.ulazniSimboli = ulazniSimboli;
        this.trenutnaStanja = trenutnaStanja;
    }

    public void obaviPosao() {
        traziPrveEpsilone(trenutnaStanja);
        updatePrintingString (trenutnaStanja);
        generirajNovaStanja();

        if (ulazniSimboli.size() == 0) {
            traziZadnjeEpsilone(listaNovihStanja);
            updatePrintingString (listaNovihStanja);
        }

        if (ulazniSimboli.size() > 0) {
            trenutnaStanja = new ArrayList<String>(listaNovihStanja);
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
                        if ((!listaNovihStanja.contains(jednoNovoStanje)) && (!jednoNovoStanje.equals("#"))) {
                            listaNovihStanja.add(jednoNovoStanje);
                        }
                    }

                }
            }
        }
        if (listaNovihStanja.isEmpty()){
            listaNovihStanja.add("#");
        }
    }

    public void traziPrveEpsilone(List<String> stanja) {
        int zastavica = 0;
        ArrayList<String> stanja2 = new ArrayList<String>(stanja);
        for (String nekoTrenutnoStanje : stanja2) {
            for (Prijelaz prijelaz : prijelazi) {
                if (prijelaz.getUlaznoStanje().equals(nekoTrenutnoStanje) && prijelaz.getPobuda().equals("$")){
                    for (String st : prijelaz.getNovaStanja()){
                        if ((!trenutnaStanja.contains(st)) && (!st.equals("#"))){
                            trenutnaStanja.add(st);
                            zastavica = 1;
                        }
                    }
                }
            }
        }
        if (zastavica == 1) {
            traziPrveEpsilone(trenutnaStanja);
        }
    }
    public void traziZadnjeEpsilone(List<String> stanja) {
        int zastavica = 0;
        ArrayList<String> stanja2 = new ArrayList<String>(stanja);
        for (String nekoTrenutnoStanje : stanja2) {
            for (Prijelaz prijelaz : prijelazi) {
                if (prijelaz.getUlaznoStanje().equals(nekoTrenutnoStanje) && prijelaz.getPobuda().equals("$")){
                    for (String st : prijelaz.getNovaStanja()){
                        if ((!listaNovihStanja.contains(st)) && (!st.equals("#"))){
                            listaNovihStanja.add(st);
                            zastavica = 1;
                        }
                    }
                }
            }
        }
        if (zastavica == 1) {
            traziZadnjeEpsilone(listaNovihStanja);
        }
    }

    public void updatePrintingString (List<String> trenutnaStanja2){
        //prebaci obicnu listu u array listu!
        ArrayList<String> printStanja = new ArrayList<String>(trenutnaStanja2);
        Collections.sort(printStanja);

        String[] poljeTrenutnihStanja = printStanja.toArray(new String[printStanja.size()]);
        int duljina = poljeTrenutnihStanja.length;

        int i = 0;
        for (; i < duljina-1; i++) {
            printString = printString.concat(poljeTrenutnihStanja[i] + ",");
        }
        if (i == duljina-1){
            printString = printString.concat(poljeTrenutnihStanja[duljina-1]);
        }

        if (ulazniSimboli.size() >= 1) {
            printString = printString.concat("|");
        } else {
            printString = printString.concat("\n");
        }
    }
}