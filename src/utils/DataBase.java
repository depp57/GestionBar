package utils;

import items.Consommable;
import items.TypeInformation;

import java.sql.*;
import java.time.LocalDate;
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
        CallableStatement cStmt = connection.prepareCall("{call compte_creer(?, ?)}");
        cStmt.setString(1, nom);
        cStmt.setString(2, prenom);

        cStmt.execute();
        cStmt.close();
    }

    public static void produit_inserer(String intituleProduit, double prixAchat,
                                       double prixVente, Consommable.Categorie categorie, String ingredient) throws SQLException {
        CallableStatement cStmt = connection.prepareCall("{call produit_inserer(?, ?, ?, ?, ?)}");
        cStmt.setString(1, intituleProduit);
        cStmt.setDouble(2, prixAchat);
        cStmt.setDouble(3, prixVente);

        String categ = (categorie == Consommable.Categorie.BOISSON) ? "Boisson" : "Aperitif";
        cStmt.setString(4, categ);
        cStmt.setString(5, ingredient);

        cStmt.execute();
        cStmt.close();
    }

    public static void produit_modifier(String intituleProduitAvant, String intituleProduitApres,
                                        double prixAchat, double prixVente, Consommable.Categorie categorie, String ingredient) throws SQLException {
        CallableStatement cStmt = connection.prepareCall("{call produit_modifier(?, ?, ?, ?, ?, ?)}");
        cStmt.setString(1, intituleProduitAvant);
        cStmt.setString(2, intituleProduitApres);
        cStmt.setDouble(3, prixAchat);
        cStmt.setDouble(4, prixVente);

        String categ = (categorie == Consommable.Categorie.BOISSON) ? "Boisson" : "Aperitif";
        cStmt.setString(5, categ);
        cStmt.setString(6, ingredient);

        cStmt.execute();
        cStmt.close();
    }

    public static void compte_crediter(int idCompte, double valeur, LocalDate date) throws SQLException {
        Date d = Date.valueOf(date);

        CallableStatement cStmt = connection.prepareCall("{call compte_crediter(?, ?, ?)}");
        cStmt.setInt(1, idCompte);
        cStmt.setDouble(2, valeur);
        cStmt.setDate(3, d);

        cStmt.execute();
        cStmt.close();
    }

    public static void compte_supprimer(int idCompte) throws SQLException {
        CallableStatement cStmt = connection.prepareCall("{call compte_supprimer(?)}");
        cStmt.setInt(1, idCompte);

        cStmt.execute();
        cStmt.close();
    }

    /**
     * 0 pour gratuit, 1 pour non gratuit
     */
    public static void stock_acheter(String intituleProduit, int quantite, int gratuit) throws SQLException {
        CallableStatement cStmt = connection.prepareCall("{call stock_acheter(?, ?, ?)}");
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
        CallableStatement cStmt = connection.prepareCall("{? = call stock_vendre(?, ?, ?)}");

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
        Date d = Date.valueOf(date);

        CallableStatement cStmt = connection.prepareCall("{call compte_acheter(?, ?, ?, ?, ?)}");
        cStmt.setInt(1, idCompte);
        cStmt.setString(2, intituleProduit);
        cStmt.setInt(3, quantite);
        cStmt.setDate(4, d);
        cStmt.setInt(5, gratuit);

        cStmt.execute();
        cStmt.close();
    }

    public static void compte_init(int idCompte) {
        try {
            CallableStatement cStmt = connection.prepareCall("{call compte_init(?)}");
            cStmt.setInt(1, idCompte);

            cStmt.execute();
            cStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearDatesInutilesCompte(int idCompte) {
        try {
            CallableStatement cStmt = connection.prepareCall("{call clearDatesInutilesCompte(?)}");
            cStmt.setInt(1, idCompte);

            cStmt.execute();
            cStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void produit_supprimer(String intituleProduit) throws SQLException {
        CallableStatement cStmt = connection.prepareCall("{call produit_supprimer(?)}");
        cStmt.setString(1, intituleProduit);

        cStmt.execute();
        cStmt.close();
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<String> getComptes() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nom, prenom FROM Compte");

        ArrayList<String> comptes = new ArrayList<>();

        while (rs.next()) {
            StringBuilder compte = new StringBuilder(rs.getString("prenom"));
            int nbSpace = 52 - compte.length();

            for (int i = 0; i < nbSpace; i++)
                compte.append(" ");

            compte.append(rs.getString("nom"));

            comptes.add(compte.toString());
        }

        rs.close();

        return comptes;
    }

    public static double getInfoCompte(int idCompte,
                                       LocalDate date, TypeInformation typeInfo) throws SQLException {
        Statement stmt = connection.createStatement();

        String query;

        if (typeInfo != TypeInformation.TOTAL)
            query = "SELECT " + typeInfo.getNom() + " FROM Compte_info WHERE idCompte = " + idCompte + " AND dateInfo = TO_DATE('" + Date.valueOf(date) + "', 'YYYY-MM-DD')";

        else
            query = "SELECT reste + plus - moins FROM Compte_info WHERE idCompte = " + idCompte + " AND dateInfo = TO_DATE('" + Date.valueOf(date) + "', 'YYYY-MM-DD')";

        ResultSet rs = stmt.executeQuery(query);

        rs.next();
        double info = rs.getDouble(1);
        rs.close();

        return info;
    }

    public static int getAchatCompte(int idCompte,
                                     LocalDate date, String intituleProduit) throws SQLException {
        Statement stmt = connection.createStatement();

        String query = "SELECT quantite FROM Compte_achats WHERE idCompte = " + idCompte + " AND intituleProduit = '" + intituleProduit + "' AND " +
                " dateAchat = TO_DATE('" + Date.valueOf(date) + "', 'YYYY-MM-DD')";
        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        int quantite;
        try {
            quantite = rs.getInt("quantite");
        } catch (SQLException e) {
            quantite = 0;
        }

        rs.close();

        return quantite;
    }

    public static ArrayList<String> getProduits() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT intituleProduit FROM Produit order by INTITULEPRODUIT");

        ArrayList<String> produits = new ArrayList<>();

        while (rs.next())
            produits.add(rs.getString("intituleProduit"));

        rs.close();
        return produits;
    }

    public static double getTotalAchatStock() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT totalAchat FROM Stock_info");

        rs.next();
        double totalAchat = rs.getDouble("totalAchat");
        rs.close();

        return totalAchat;
    }

    public static double getTotalVenteStock() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT totalVente FROM Stock_info");

        rs.next();
        double totalVente = rs.getDouble("totalVente");
        rs.close();

        return totalVente;
    }

    public static int getQuantiteStock(String intituleProduit) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT quantite FROM Stock WHERE intituleProduit = '" + intituleProduit + "'");

        rs.next();
        int quantite;
        try {
            quantite = rs.getInt("quantite");
        } catch (SQLException e) {
            quantite = 0;
        }
        rs.close();

        return quantite;
    }

    public static String getTypeProduit(String intituleProduit) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT typeProduit FROM Produit WHERE intituleProduit = '" + intituleProduit + "'");

            rs.next();
            String type = rs.getString("typeProduit");
            rs.close();

            return type;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Boisson";
    }

    //TODO RAJOUTER LA RECETTE
    public static Consommable getProduit(String produit) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT prixAchat, prixVente, typeProduit FROM Produit WHERE intituleProduit = '" + produit + "'");

        rs.next();
        double prixAchat = rs.getDouble("prixAchat");
        double prixVente = rs.getDouble("prixVente");
        String typeProduit = rs.getString("typeProduit");

        rs.close();

        rs = stmt.executeQuery("SELECT intituleProduitConsomme as recette FROM Ingredient WHERE intituleProduitPere = '" + produit + "'");
        String recette;
        if (rs.next())
            recette = rs.getString("recette");
        else
            recette = "Aucun";

        rs.close();
        stmt.close();

        return new Consommable(produit, prixAchat, prixVente, recette, typeProduit);
    }

    public static void compte_setInfo(int idCompte, LocalDate date, TypeInformation typeInfo, double nouvelleValeur) throws SQLException {
        Statement stmt = connection.createStatement();
        String type = typeInfo.getNom();

        stmt.executeUpdate("UPDATE compte_info SET " + type + " = " +
                nouvelleValeur + " WHERE idCompte = " + idCompte + " AND dateInfo = TO_DATE('" + Date.valueOf(date) + "', 'YYYY-MM-DD')");

        stmt.close();
    }

    public static int getIdCompte(String nom, String prenom) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT idCompte FROM Compte WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'");

        rs.next();
        int idCompte = rs.getInt("idCompte");
        stmt.close();
        rs.close();

        return idCompte;
    }

    public static String[] compte_getAllDates(int idCompte, int dateAfficher) {
        ArrayList<String> dates = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT TO_CHAR(dateInfo, 'dd/mm/yyyy') FROM compte_info " +
                    "WHERE idCompte = " + idCompte + " AND EXTRACT(year FROM dateInfo) = " + dateAfficher +
                    " ORDER BY dateInfo");

            while (rs.next())
                dates.add(rs.getString(1));

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates.toArray(new String[0]);
    }

    public static String afficherException(SQLException exception) {
        return exception.getMessage().split("\n")[0].split("20001:")[1];
    }

    public static boolean aAcheteProduit(int idCompte, String intituleProduit) {
        boolean achat = false;
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM compte_achats WHERE idCompte = " + idCompte + " AND intituleProduit = '" + intituleProduit + "'");

            rs.next();

            achat = rs.getInt(1) > 0;

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return achat;
    }

    public static void compte_modifierAchat(int idCompte, String intituleProduit, LocalDate date, int nouvelleQuantite) {
        double prixUnite = 0.0;
        int oldQuantite = 0;

        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT prixAchatUnite, quantite FROM Compte_Achats WHERE idCompte = ? AND dateAchat = ? AND intituleProduit = ?");

            pstmt.setInt(1, idCompte);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setString(3, intituleProduit);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                prixUnite = rs.getDouble("prixAchatUnite");
                oldQuantite = rs.getInt("quantite");
            }
            rs.close();


            pstmt = connection.prepareStatement("UPDATE Compte_achats SET quantite = ? WHERE idCompte = ? AND dateAchat = ? AND intituleProduit = ?");
            pstmt.setDouble(1, nouvelleQuantite);
            pstmt.setInt(2, idCompte);
            pstmt.setDate(3, Date.valueOf(date));
            pstmt.setString(4, intituleProduit);
            pstmt.executeUpdate();


            pstmt = connection.prepareStatement("UPDATE Compte_info SET moins = moins - ? WHERE idCompte = ? AND dateInfo = ?");
            pstmt.setDouble(1, (oldQuantite - nouvelleQuantite) * prixUnite);
            pstmt.setInt(2, idCompte);
            pstmt.setDate(3, Date.valueOf(date));
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
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}