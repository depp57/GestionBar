package excelUtilities;

import items.Consommable;

import java.util.ArrayList;

public class ValeurTablePrincipale {

    private final Consommable consommable;

    private final String nomConso;

    private ArrayList<Integer> quantite;

    public ValeurTablePrincipale(Consommable consommable, ArrayList<Integer> quantite) {
        this.consommable = consommable;
        this.quantite = quantite;

        nomConso = consommable.getNom();
    }

    public Consommable getConsommable() {
        return consommable;
    }

    public int getQuantite(int index) {
        if(quantite != null)
            return quantite.get(index);

        return 0;
    }

    public String getNomConso() {
        return nomConso;
    }

    public void setQuantite(ArrayList<Integer> quantite) {
        this.quantite = quantite;
    }
}
