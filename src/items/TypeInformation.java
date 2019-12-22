package items;

public enum TypeInformation {

    RESTE("Reste"),
    PLUS("Plus"),
    MOINS("Moins"),
    TOTAL("Total");

    private final String nom;

    TypeInformation(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}