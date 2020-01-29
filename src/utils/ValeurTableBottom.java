package utils;

import items.TypeInformation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ValeurTableBottom {

    private final TypeInformation typeInformation;
    private final ArrayList<String> montant;

    public ValeurTableBottom(TypeInformation typeInformation, ArrayList<Integer> montants) {
        this.typeInformation = typeInformation;

        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.FRANCE);

        this.montant = new ArrayList<>();

        for (Integer currentMontant : montants) {
            this.montant.add(n.format(currentMontant));
        }
    }

    public TypeInformation getTypeInformation() { return typeInformation;}

    public String getMontant(int index) {
        if(montant.size() > index)
            return montant.get(index);
        return "0";
    }

    public String getTexte() {
        return typeInformation.getNom();
    }

    public void add(double infoCompte) {
        montant.add(infoCompte + "€");
    }
}
