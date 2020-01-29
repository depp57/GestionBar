package items;

public class AchatProduit {

    private int quantite;

    private double prixUnitaire;

    public AchatProduit(int quantite, double prixUnitaire) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}