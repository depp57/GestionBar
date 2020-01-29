package utils;

import items.AchatProduit;

public class ValeurTableAchats {

    private final String nomProduit;

    private AchatProduit[] valeurs;

    public ValeurTableAchats(String nomProduit, AchatProduit[] valeurs) {
        this.nomProduit = nomProduit;
        this.valeurs = valeurs;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public AchatProduit getValeur(int index) {
        return valeurs[index];
    }

    public void setValeurs(int index, int quantite, double prixUnitaire) {
        valeurs[index].setQuantite(quantite);
        valeurs[index].setPrixUnitaire(prixUnitaire);
    }
}