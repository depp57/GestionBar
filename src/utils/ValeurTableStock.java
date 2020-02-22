package utils;

public final class ValeurTableStock {

    private final String nomProduit;

    private final int quantite;

    public ValeurTableStock(String nomProduit, int quantite) {
        this.quantite = quantite;
        this.nomProduit = nomProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getNomProduit() {
        return nomProduit;
    }
}