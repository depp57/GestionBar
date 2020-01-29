package utils;

import items.Consommable;

import java.util.ArrayList;

public class ValeurTablePrincipale {

    private final Consommable consommable;

    private final String nomConso;

    private ArrayList<Integer> quantites;

    public ValeurTablePrincipale(Consommable consommable, ArrayList<Integer> quantites) {
        this.consommable = consommable;
        this.quantites = quantites;

        nomConso = consommable.getNom();
    }

    public Consommable getConsommable() {
        return consommable;
    }

    public int getQuantite(int index) {
        if(quantites != null)
            return quantites.get(index);

        return 0;
    }

    public String getNomConso() {
        return nomConso;
    }

    public void setQuantites(ArrayList<Integer> quantites) {
        this.quantites = quantites;
    }
}
