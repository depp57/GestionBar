package utils;

import main.MainApp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TreeMap;

public final class CellulesAchat {

    private TreeMap<String, HashMap<String, Cellule>> hashMap;

    public CellulesAchat(int dateAafficher) {
        hashMap = new TreeMap<>((d1, d2) -> {
            String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
            return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
        });

        if (LocalDate.now().getYear() == dateAafficher)
            hashMap.put(LocalDate.now().format(DateTimeFormatter.ofPattern(MainApp.datePattern)), new HashMap<>());
    }

    public final void addLigne(String date, String produit, int quantite, float prixUnite) {
        Cellule cell = new Cellule(quantite, prixUnite);
        HashMap<String, Cellule> cellules;
        if (!hashMap.containsKey(date))
            cellules = new HashMap<>();
        else
            cellules = hashMap.get(date);

        cellules.put(produit, cell);
        hashMap.put(date, cellules);
    }

    public final TreeMap<String, HashMap<String, Cellule>> getHashMap() {
        return hashMap;
    }

    public final Integer getQuantite(String date, String produit) {
        Cellule valeur = hashMap.get(date).get(produit);
        return (valeur != null) ? valeur.quantite : 0;
    }

    public final Float getPrixUnite(String date, String produit) {
        Cellule valeur = hashMap.get(date).get(produit);
        return (valeur != null) ? valeur.prixUnite : 0;
    }

    public final String getQuantiteEtPrix(String date, String produit) {
        Cellule valeur = hashMap.get(date).get(produit);
        return (valeur != null) ? valeur.toString() : "";
    }

    public final boolean updateLigne(String date, String produit, int quantite, float prixUnit) {
        boolean containsKey = hashMap.containsKey(date);
        if (!containsKey || !hashMap.get(date).containsKey(produit))
            addLigne(date, produit, quantite, prixUnit);
        else {
            Cellule cell = hashMap.get(date).get(produit);
            cell.prixUnite = (float) (Math.round((cell.prixUnite * cell.quantite + quantite * prixUnit) /
                    (cell.quantite + quantite) * 100) /100.0);
            cell.quantite += quantite;
        }
        return containsKey;
    }

    public final void clearLigne(String date, String produit) {
        if (hashMap.containsKey(date))
            hashMap.get(date).remove(produit);
    }

    public final static class Cellule {

        private int quantite;
        private float prixUnite;

        private Cellule(int quantite, float prixUnite) {
            this.quantite = quantite;
            this.prixUnite = prixUnite;
        }

        public String toString() {
            return "Q : " + quantite + "   |    P : " + prixUnite + '€';
        }
    }
}