package utils;

public final class Consommable {

    public enum Categorie {
        BOISSON,
        APERITIF
    }

    private String nom;
    private double prixAchat;
    private double prixVente;
    private String recette;
    private Categorie categorie;

    public Consommable(String nom, double prixAchat, double prixVente, String recette, String categorie) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.recette = recette;

        if(categorie.equals("Boisson"))
            this.categorie = Categorie.BOISSON;
        else
            this.categorie = Categorie.APERITIF;
    }

    public final String getNom() {
        return nom;
    }

    public final void setNom(String nom) {
        this.nom = nom;
    }

    public final double getPrixAchat() {
        return prixAchat;
    }

    public final double getPrixVente() {
        return prixVente;
    }

    public final Categorie getCategorie() {
        return categorie;
    }

    public final String getNomRecette() { return recette; }
}