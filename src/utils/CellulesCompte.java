package utils;

import java.util.HashMap;
import java.util.TreeMap;

public final class CellulesCompte {

    private TreeMap<String, HashMap<String, Integer>> hashMap;

    public CellulesCompte() {
        hashMap = new TreeMap<>((d1, d2) -> {
            final String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
            return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
        });
    }

    public final int getCellule(String date, String produit) {
        if (hashMap.containsKey(date))
            return hashMap.get(date).getOrDefault(produit, 0);
        else
            return 0;
    }

    public final void addLigne(String date, String produit, int quantite) {
        final HashMap<String, Integer> cellules;
        if (!hashMap.containsKey(date))
            cellules = new HashMap<>();
        else
            cellules = hashMap.get(date);

        cellules.put(produit, quantite);
        hashMap.put(date, cellules);
    }

    public final void updateLigne(String date, String produit, int quantite) {
        if (!hashMap.containsKey(date) || !hashMap.get(date).containsKey(produit))
            addLigne(date, produit, quantite);
        else
            hashMap.get(date).put(produit, quantite);
    }
}