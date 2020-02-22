package utils;

import java.sql.Date;

public final class HistoriqueAchat {

    public final double prixUnite;
    public final Date dateAchat;

    public HistoriqueAchat(Date dateAchat, double prixUnite) {
        this.dateAchat = dateAchat;
        this.prixUnite = prixUnite;
    }
}