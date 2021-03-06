package utils;

import main.MainApp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public final class CellulesCompteB {

    private final TreeMap<String, CelluleCompteB> hashMap;

    public CellulesCompteB() {
        hashMap = new TreeMap<>((d1, d2) -> {
            final String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
            return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
        });
    }

    public final TreeMap<String, CelluleCompteB> getHashMap() {
        return hashMap;
    }

    public final double getCellule(String date, String typeInfo) {
        CelluleCompteB valeur = hashMap.get(date);
        if (valeur != null) {
            switch (typeInfo) {
                case "Reste" : return valeur.reste;
                case "Moins" : return valeur.moins;
                case "Plus" : return valeur.plus;
                case "Total" : return Math.round((valeur.reste + valeur.plus - valeur.moins) * Math.pow(10, 2)) / Math.pow(10, 2);
            }
        }
        return 0F;
    }

    public final void addLigne(String date, double reste, double moins, double plus) {
        hashMap.put(date, new CelluleCompteB(reste, moins, plus));
    }

    public final boolean updateLigne(int idCompte, String date, double moins, double plus) {
        final boolean containsKey = hashMap.containsKey(date);
        if (!containsKey)
            addLigne(date, DataBase.getReste(idCompte, date), moins, plus);
        else {
            CelluleCompteB cell = hashMap.get(date);
            cell.moins += moins;
            cell.plus += plus;
        }
        return containsKey;
    }

    public void addFirstColIfEmpty(int anneeDonnees) {
        if (hashMap.isEmpty()) {
            LocalDate today = LocalDate.now();
            if (today.getYear() != anneeDonnees) //Si c'est pas l'année courante qu'on veut afficher, affiche le 01/01/X
                today = LocalDate.ofYearDay(anneeDonnees, 1);
            hashMap.put(today.format(DateTimeFormatter.ofPattern(MainApp.datePattern)), new CelluleCompteB(0, 0, 0));
        }
    }

    public static class CelluleCompteB {
        private double reste, moins, plus;
        private CelluleCompteB(double reste, double moins, double plus) {
            this.reste = reste;
            this.moins = moins;
            this.plus = plus;
        }
    }
}
