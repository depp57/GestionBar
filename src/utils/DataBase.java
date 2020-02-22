package utils;

import javafx.scene.control.Alert;
import main.MainApp;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class DataBase {

    private static Connection connection;

    /**
     * A CHANGER SUIVANT LA DDB
     */
    public static void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Depp");
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void compte_creer(String nom, String prenom) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call compte_creer(?, ?)}");
        cStmt.setString(1, nom);
        cStmt.setString(2, prenom);

        cStmt.execute();
        cStmt.close();
    }

    public static void produit_inserer(String intituleProduit, double prixAchat,
                                       double prixVente, Consommable.Categorie categorie, String ingredient) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call produit_inserer(?, ?, ?, ?, ?)}");
        cStmt.setString(1, intituleProduit);
        cStmt.setDouble(2, prixAchat);
        cStmt.setDouble(3, prixVente);

        final String categ = (categorie == Consommable.Categorie.BOISSON) ? "Boisson" : "Aperitif";
        cStmt.setString(4, categ);
        cStmt.setString(5, ingredient);

        cStmt.execute();
        cStmt.close();
    }

    public static void produit_modifier(String intituleProduitAvant, String intituleProduitApres,
                                        double prixAchat, double prixVente, Consommable.Categorie categorie, String ingredient) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call produit_modifier(?, ?, ?, ?, ?, ?)}");
        cStmt.setString(1, intituleProduitAvant);
        cStmt.setString(2, intituleProduitApres);
        cStmt.setDouble(3, prixAchat);
        cStmt.setDouble(4, prixVente);

        final String categ = (categorie == Consommable.Categorie.BOISSON) ? "Boisson" : "Aperitif";
        cStmt.setString(5, categ);
        cStmt.setString(6, ingredient);

        cStmt.execute();
        cStmt.close();
    }

    public static void compte_crediter(int idCompte, double valeur, LocalDate date) throws SQLException {
        if (valeur < 0) {
            final Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur crédit compte");
            alerte.setContentText("Veuillez rentrer un montant positif");
            alerte.setHeaderText(null);
            alerte.show();
            return;
        }

        final Date d = Date.valueOf(date);

        final CallableStatement cStmt = connection.prepareCall("{call compte_crediter(?, ?, ?)}");
        cStmt.setInt(1, idCompte);
        cStmt.setDouble(2, valeur);
        cStmt.setDate(3, d);

        cStmt.execute();
        cStmt.close();
    }

    public static void compte_supprimer(int idCompte) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call compte_supprimer(?)}");
        cStmt.setInt(1, idCompte);

        cStmt.execute();
        cStmt.close();
    }

    /**
     * 0 pour gratuit, 1 pour non gratuit
     */
    public static void stock_acheter(String intituleProduit, int quantite, int gratuit) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call stock_acheter(?, ?, ?)}");
        cStmt.setString(1, intituleProduit);
        cStmt.setInt(2, quantite);
        cStmt.setInt(3, gratuit);

        cStmt.execute();
        cStmt.close();
    }

    /**
     * 0 pour gratuit, 1 pour non gratuit
     */
    public static void stock_vendre(String intituleProduit, int quantite, int gratuit) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{? = call stock_vendre(?, ?, ?)}");

        cStmt.registerOutParameter(1, Types.DOUBLE);
        cStmt.setString(2, intituleProduit);
        cStmt.setInt(3, quantite);
        cStmt.setInt(4, gratuit);

        cStmt.execute();
        cStmt.close();
    }

    /**
     * 0 pour gratuit, 1 pour non gratuit
     */
    public static void compte_acheter(int idCompte, String intituleProduit,
                                      int quantite, LocalDate date, int gratuit) throws SQLException {
        final Date d = Date.valueOf(date);

        final CallableStatement cStmt = connection.prepareCall("{call compte_acheter(?, ?, ?, ?, ?)}");
        cStmt.setInt(1, idCompte);
        cStmt.setString(2, intituleProduit);
        cStmt.setInt(3, quantite);
        cStmt.setDate(4, d);
        cStmt.setInt(5, gratuit);

        cStmt.execute();
        cStmt.close();
    }

    public static void enregistrer_achat(String produit, Date date, int quantite, float prixUnit) {
        try {
            final CallableStatement cStmt = connection.prepareCall("{call ENREGISTERACHAT(?, ?, ?, ?)}");
            cStmt.setString(1, produit);
            cStmt.setDate(2, date);
            cStmt.setInt(3, quantite);
            cStmt.setDouble(4, prixUnit);

            cStmt.execute();
            cStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void produit_supprimer(String intituleProduit) throws SQLException {
        final CallableStatement cStmt = connection.prepareCall("{call produit_supprimer(?)}");
        cStmt.setString(1, intituleProduit);

        cStmt.execute();
        cStmt.close();
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<String> getComptes() throws SQLException {
        final Statement stmt = connection.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT nom, prenom FROM Compte");

        final ArrayList<String> comptes = new ArrayList<>();

        while (rs.next()) {
            final StringBuilder compte = new StringBuilder(rs.getString("prenom"));
            final int nbSpace = 52 - compte.length();

            for (int i = 0; i < nbSpace; i++)
                compte.append(" ");

            compte.append(rs.getString("nom"));

            comptes.add(compte.toString());
        }

        rs.close();
        stmt.close();

        return comptes;
    }

    public static double getInfoCompte(int idCompte,String date, TypeInformation typeInfo) throws SQLException {
        final PreparedStatement pstmt = connection.prepareStatement("SELECT ? FROM Compte_info WHERE IdCompte = ? AND TO_CHAR(dateInfo, 'dd/mm/yyyy') = ?");

        if (typeInfo != TypeInformation.TOTAL)
            pstmt.setString(1, typeInfo.getNom());

        else
            pstmt.setString(1, "reste + plus - moins");

        pstmt.setInt(2, idCompte);
        pstmt.setString(3, date);

        final ResultSet rs = pstmt.executeQuery();

        double info = 0.0;
        if (rs.next())
            info = rs.getDouble(1);
        rs.close();
        pstmt.close();

        return info;
    }

    public static ArrayList<String> getProduits() throws SQLException {
        final Statement stmt = connection.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT intituleProduit FROM Produit order by INTITULEPRODUIT");

        final ArrayList<String> produits = new ArrayList<>();

        while (rs.next())
            produits.add(rs.getString("intituleProduit"));

        rs.close();
        stmt.close();
        return produits;
    }

    public static double[] getTotauxStock() throws SQLException {
        final Statement stmt = connection.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT totalAchat, totalVente FROM Stock_info");

        rs.next();
        final double totalAchat = rs.getDouble(1);
        final double totalVente = rs.getDouble(2);
        rs.close();
        stmt.close();

        return new double[]{totalAchat, totalVente};
    }

    public static int getQuantiteStock(String intituleProduit) throws SQLException {
        final Statement stmt = connection.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT quantite FROM Stock WHERE intituleProduit = '" + intituleProduit + "'");

        rs.next();
        int quantite;
        try {
            quantite = rs.getInt("quantite");
        } catch (SQLException e) {
            quantite = 0;
        }
        stmt.close();
        rs.close();

        return quantite;
    }

    public static String getTypeProduit(String intituleProduit) {
        try {
            final Statement stmt = connection.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT typeProduit FROM Produit WHERE intituleProduit = '" + intituleProduit + "'");

            rs.next();
            final String type = rs.getString("typeProduit");
            stmt.close();
            rs.close();

            return type;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Boisson";
    }

    public static Consommable getProduit(String produit) throws SQLException {
        final Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT prixAchat, prixVente, typeProduit FROM Produit WHERE intituleProduit = '" + produit + "'");

        rs.next();
        final double prixAchat = rs.getDouble("prixAchat");
        final double prixVente = rs.getDouble("prixVente");
        String typeProduit = rs.getString("typeProduit");

        rs.close();

        rs = stmt.executeQuery("SELECT intituleProduitConsomme as recette FROM Ingredient WHERE intituleProduitPere = '" + produit + "'");
        final String recette;
        if (rs.next())
            recette = rs.getString("recette");
        else
            recette = "Aucun";

        rs.close();
        stmt.close();

        return new Consommable(produit, prixAchat, prixVente, recette, typeProduit);
    }

    public static void compte_setInfo(int idCompte, LocalDate date, TypeInformation typeInfo, double nouvelleValeur) throws SQLException {
        final Statement stmt = connection.createStatement();
        final String type = typeInfo.getNom();

        stmt.executeUpdate("UPDATE compte_info SET " + type + " = " +
                nouvelleValeur + " WHERE idCompte = " + idCompte + " AND dateInfo = TO_DATE('" + Date.valueOf(date) + "', 'YYYY-MM-DD')");

        stmt.close();
    }

    public static int getIdCompte(String nom, String prenom) throws SQLException {
        final Statement stmt = connection.createStatement();
        final ResultSet rs = stmt.executeQuery("SELECT idCompte FROM Compte WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'");

        rs.next();
        final int idCompte = rs.getInt("idCompte");
        stmt.close();
        rs.close();

        return idCompte;
    }

    private static int getNbLignes(ResultSet rs) {
        try {
            rs.last();
            final int rows = rs.getRow();
            rs.beforeFirst();
            return rows;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String afficherException(SQLException exception) {
        return exception.getMessage().split("\n")[0].split("20001:")[1];
    }

    public static void compte_modifierAchat(int idCompte, String intituleProduit, LocalDate date, int nouvelleQuantite) {
        double prixUnite = 0.0;
        int oldQuantite = 0;
        final Date dateSQL = Date.valueOf(date);

        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT prixAchatUnite, quantite FROM Compte_Achats WHERE idCompte = ? AND dateAchat = ? AND intituleProduit = ?");

            pstmt.setInt(1, idCompte);
            pstmt.setDate(2, dateSQL);
            pstmt.setString(3, intituleProduit);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                prixUnite = rs.getDouble("prixAchatUnite");
                oldQuantite = rs.getInt("quantite");
            }

            if (nouvelleQuantite <= 0) {
                pstmt = connection.prepareStatement("DELETE FROM Compte_achats WHERE idCompte = ? AND dateAchat = ? AND intituleProduit = ?");
                pstmt.setInt(1, idCompte);
                pstmt.setDate(2, dateSQL);
                pstmt.setString(3, intituleProduit);
            }
            else {
                pstmt = connection.prepareStatement("UPDATE Compte_achats SET quantite = ? WHERE idCompte = ? AND dateAchat = ? AND intituleProduit = ?");
                pstmt.setDouble(1, nouvelleQuantite);
                pstmt.setInt(2, idCompte);
                pstmt.setDate(3, dateSQL);
                pstmt.setString(4, intituleProduit);
            }
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement("SELECT plus, moins FROM Compte_info WHERE idCompte = ? AND dateInfo = ?");
            pstmt.setInt(1, idCompte);
            pstmt.setDate(2, dateSQL);
            rs = pstmt.executeQuery();
            boolean delete = false;
            if (rs.next())
                delete = rs.getDouble(1) == 0 && ((rs.getDouble(2) - (oldQuantite - nouvelleQuantite) * prixUnite)) == 0;

            if (delete) {
                pstmt = connection.prepareStatement("DELETE FROM Compte_info WHERE idCompte = ? AND dateInfo = ?");
                pstmt.setInt(1, idCompte);
                pstmt.setDate(2, dateSQL);
            }
            else {
                pstmt = connection.prepareStatement("UPDATE Compte_info SET moins = moins - ? WHERE idCompte = ? AND dateInfo = ?");
                pstmt.setDouble(1, (oldQuantite - nouvelleQuantite) * prixUnite);
                pstmt.setInt(2, idCompte);
                pstmt.setDate(3, dateSQL);
            }
            pstmt.executeUpdate();

            // ------------------ STOCK ------------------ \\
            pstmt = connection.prepareStatement("UPDATE Stock SET quantite = quantite + ? WHERE intituleProduit = ?");
            pstmt.setInt(1, oldQuantite - nouvelleQuantite);
            pstmt.setString(2, intituleProduit);
            pstmt.executeUpdate();


            pstmt = connection.prepareStatement("UPDATE Stock_info SET totalVente = totalVente - ?");
            pstmt.setDouble(1, (oldQuantite - nouvelleQuantite) * prixUnite);
            pstmt.executeUpdate();
            // ------------------ STOCK ------------------ \\


            // ------------------ CAS RECETTE (PICON..) -----------------\\
            pstmt = connection.prepareStatement("SELECT intituleProduitConsomme FROM ingredient WHERE intituleProduitPere = ?");
            pstmt.setString(1, intituleProduit);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                final String recette = rs.getString(1);
                pstmt = connection.prepareStatement("UPDATE STOCK SET quantite = quantite - ? WHERE intituleProduit = ?");
                pstmt.setInt(1, nouvelleQuantite - oldQuantite);
                pstmt.setString(2, recette);
                pstmt.executeUpdate();
            }

            rs.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HistoriqueAchat[] getDonneesGraphique(String intituleProduit, int dateAafficher) {
        try {
            final PreparedStatement pstmt = connection.prepareStatement("SELECT dateAchat, prixUnite" +
                    " FROM stock_achats WHERE INTITULEPRODUIT = ? AND EXTRACT(year FROM dateAchat) = ? ORDER BY dateAchat", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            pstmt.setString(1, intituleProduit);
            pstmt.setInt(2, dateAafficher);
            final ResultSet rs = pstmt.executeQuery();
            final HistoriqueAchat[] valeurs = new HistoriqueAchat[getNbLignes(rs)];
            int i = 0;
            while(rs.next())
                valeurs[i++] = new HistoriqueAchat(rs.getDate("dateAchat"), rs.getDouble("prixUnite"));

            rs.close();
            pstmt.close();
            return valeurs;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CellulesAchat getAchats(int dateAafficher) {
        final CellulesAchat valeurs = new CellulesAchat(dateAafficher);
        try {
            final Statement stmt = connection.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT TO_CHAR(dateAchat, 'dd/MM/yyyy'), intituleProduit, quantite, prixUnite FROM Stock_achats " +
                    "WHERE EXTRACT(year FROM dateAchat) = " + dateAafficher);

            while(rs.next())
                valeurs.addLigne(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getFloat(4));

            rs.close();
            stmt.close();

            return valeurs;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return valeurs;
    }


    public static CellulesCompte getAchatsCompte(int idCompte, int dateAafficher) {
        final CellulesCompte valeurs = new CellulesCompte();
        try {
            final PreparedStatement pstmt = connection.prepareStatement("SELECT TO_CHAR(dateAchat, 'dd/MM/yyyy'), intituleProduit, quantite FROM Compte_achats" +
                    " WHERE idCompte = ? AND EXTRACT(year FROM dateAchat) = ?");

            pstmt.setInt(1, idCompte);
            pstmt.setInt(2, dateAafficher);

            final ResultSet rs = pstmt.executeQuery();

            while(rs.next())
                valeurs.addLigne(rs.getString(1), rs.getString(2), rs.getInt(3));

            rs.close();
            pstmt.close();

            return valeurs;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return valeurs;
    }

    public static CellulesCompteB getInfosCompte(int idCompte, int dateAafficher) {
        final CellulesCompteB valeurs = new CellulesCompteB(dateAafficher);
        try {
            final PreparedStatement pstmt = connection.prepareStatement("SELECT TO_CHAR(dateInfo, 'dd/MM/yyyy'), moins, plus  FROM COMPTE_INFO" +
                    " WHERE idCompte = ? AND EXTRACT(year FROM dateInfo) = ? ORDER BY dateInfo");

            pstmt.setInt(1, idCompte);
            pstmt.setInt(2, dateAafficher);

            final ResultSet rs = pstmt.executeQuery();

            while(rs.next())
                valeurs.addLigne(rs.getString(1), rs.getDouble(2), rs.getDouble(3));

            rs.close();
            pstmt.close();

            return valeurs;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return valeurs;
    }

    public static double getPrixProduit(String produit) {
        double prix = 0;
        try {
            final PreparedStatement pstmt = connection.prepareStatement("SELECT prixVente FROM Produit WHERE intituleProduit = ?");
            pstmt.setString(1, produit);
            final ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                prix =  rs.getDouble(1);

            rs.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return prix;
    }

    public static void supprimerAchat(String produit, String date) {
        try {
            final PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Stock_Achats" +
                    " WHERE intituleProduit = ? AND dateAchat = ?");
            pstmt.setString(1, produit);
            pstmt.setDate(2, Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern(MainApp.datePattern))));

            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}