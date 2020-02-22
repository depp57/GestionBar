package utils;

import java.time.LocalDate;

public final class ActionCompte {

    private Actions action;
    private String produit;
    private LocalDate date;
    private int quantite;
    private double montant;
    private boolean gratuit;

    public enum Actions {
        VENTE,
        CREDIT
    }

    public ActionCompte(Actions action, String produit, LocalDate date, int quantite, boolean gratuit) {
        this.action = action;
        this.produit = produit;
        this.date = date;
        this.quantite = quantite;
        this.gratuit = gratuit;
    }

    public ActionCompte(Actions action, LocalDate date, double montant) {
        this.action = action;
        this.date = date;
        this.montant = montant;
    }

    public Actions getAction() {
        return action;
    }

    public String getProduit() {
        return produit;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getMontant() {
        return montant;
    }

    public boolean isGratuit() { return gratuit; }
}