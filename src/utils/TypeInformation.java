package utils;

public enum TypeInformation {

    PLUS("Plus"),
    TOTAL("Total");

    private final String nom;

    TypeInformation(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}