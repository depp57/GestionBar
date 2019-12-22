import items.Consommable;
import items.TypeInformation;
import org.junit.*;
import utils.DataBase;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static utils.DataBase.*;

public class DataBaseTest {

    private final String NOM = "Thommet", PRENOM = "Sacha";

    @BeforeClass
    public static void setUp() {
        openConnection();
    }

    @AfterClass
    public static void after() {
        closeConnection();
    }

    @Test
    public void testCompte_creer() throws SQLException {
        //Test
        compte_creer(NOM, PRENOM);
        int idCompte = getIdCompte(NOM, PRENOM);
        assertEquals("Devrait être 1", 1, getComptes().size());
        assertEquals("Devrait être 0", 0, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.PLUS), 0);
        assertEquals("Devrait être 0", 0, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.MOINS), 0);
        assertEquals("Devrait être 0", 0, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.RESTE), 0);

        //Nettoie la ddb
        compte_supprimer(idCompte);
    }

    @Test (expected = SQLException.class)
    public void testCompte_creerDouble() throws SQLException {
        int idCompte = getIdCompte(NOM, PRENOM);
        //Test
        compte_creer(NOM, PRENOM);
        try {
            compte_creer(NOM, PRENOM);
        }
        catch (SQLException e) {
            //Nettoie la ddb
            compte_supprimer(idCompte);
            throw e;
        }
    }

    @Test
    public void testCompte_supprimer() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = getIdCompte(NOM, PRENOM);

        //Test
        compte_supprimer(idCompte);
        assertEquals("Devrait être 0", 0, getComptes().size());
    }

    @Test
    public void testCompte_acheter() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = DataBase.getIdCompte(NOM, PRENOM);
        produit_inserer("Bière", 0.2, 0.3, Consommable.Categorie.BOISSON, null);
        stock_acheter("Bière", 10, 0);

        //Test
        compte_acheter(idCompte, "Bière", 2, LocalDate.now(), 1);

        assertEquals("Devrait être 0.60", 0.60, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.MOINS), 0.05);
        assertEquals("Devrait être 2", 2, getAchatCompte(idCompte, LocalDate.now(), "Bière"));

        //Nettoie la ddb
        compte_supprimer(idCompte);
        produit_supprimer("Bière");
    }

    @Test
    public void testCompte_acheterGratuit() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = DataBase.getIdCompte(NOM, PRENOM);
        produit_inserer("Bière", 0.2, 0.3, Consommable.Categorie.BOISSON, null);
        stock_acheter("Bière", 10, 0);

        //Test
        compte_acheter(idCompte, "Bière", 2, LocalDate.now(), 0);

        assertEquals("Devrait être 0", 0, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.MOINS), 0.05);
        assertEquals("Devrait être 2", 2, getAchatCompte(idCompte, LocalDate.now(), "Bière"));

        //Nettoie la ddb
        compte_supprimer(idCompte);
        produit_supprimer("Bière");
    }

    @Test
    public void testCompte_crediter() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = getIdCompte(NOM, PRENOM);

        //Test
        compte_crediter(idCompte, 20.5, LocalDate.now());

        assertEquals("Devrait être 20.5", 20.5, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.PLUS), 0.05);

        //Nettoie la ddb
        compte_supprimer(idCompte);
    }

    @Test
    public void testProduit_inserer() throws SQLException {
        //Test
        produit_inserer("Bière", 0.67, 0.83, Consommable.Categorie.BOISSON, null);
        assertEquals("Devrait être 1", 1, getProduits().size());

        //Nettoie la ddb
        produit_supprimer("Bière");
    }

    @Test
    public void testProduit_modifier() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        produit_inserer("Bière", 0.67, 0.83, Consommable.Categorie.BOISSON, null);
        int idCompte = getIdCompte(NOM, PRENOM);

        //Test
        produit_modifier("Bière", "Bière", 1, 2, Consommable.Categorie.APERITIF, null);
        stock_acheter("Bière", 10, 1);
        compte_acheter(idCompte, "Bière", 2, LocalDate.now(), 1);

        assertEquals("Devrait être 10", 10, getTotalAchatStock(), 0);
        assertEquals("Devrait être 4", 4, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.MOINS), 0);

        //Nettoie la ddb
        compte_supprimer(idCompte);
        produit_supprimer("Bière");
    }


    //TODO ON NE RESET PAS LES TOTAUX STOCK CEST POUR CA YA ENCORE LES 10 DAVANT..
    @Test
    public void testStock_acheter() throws SQLException {
        double before1 = getTotalAchatStock(), before2 = getTotalVenteStock();
        //Setup
        produit_inserer("Bière", 1.50, 2.20, Consommable.Categorie.BOISSON, null);

        //Test
        stock_acheter("Bière", 10, 1);

        assertEquals("Devrait être 15", 15, getTotalAchatStock()-before1, 0);
        assertEquals("Devrait être 0", 0, getTotalVenteStock()-before2, 0);
        assertEquals("Devrait être 10", 10, getQuantiteStock("Bière"), 0);

        //Nettoie la ddb
        produit_supprimer("Bière");
    }

    @Test
    public void testStock_vendre() throws SQLException {
        double before1 = getTotalAchatStock(), before2 = getTotalVenteStock();
        //Setup
        produit_inserer("Bière", 1.50, 2.20, Consommable.Categorie.BOISSON, null);

        //Tests
        stock_acheter("Bière", 10, 1);
        stock_vendre("Bière", 10, 1);

        assertEquals("Devrait être 15", 15, getTotalAchatStock()-before1, 0);
        assertEquals("Devrait être 22", 22, getTotalVenteStock()-before2, 0);
        assertEquals("Devrait être 0", 0, getQuantiteStock("Bière"), 0);

        //Nettoie la ddb
        produit_supprimer("Bière");
    }

    @Test
    public void testCompte_setInfo() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = getIdCompte(NOM, PRENOM);

        //Tests
        compte_setInfo(idCompte, LocalDate.now(), TypeInformation.PLUS, 10.5);

        assertEquals("Devrait être 10.5", 10.5, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.PLUS), 0.2);


        //Nettoie la ddb
        compte_supprimer(idCompte);
    }

    @Test
    public void testCompte_acheterIngredientAssez() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        int idCompte = DataBase.getIdCompte(NOM, PRENOM);
        produit_inserer("Bière", 0.2, 0.3, Consommable.Categorie.BOISSON, null);
        produit_inserer("Picon", 1, 2, Consommable.Categorie.BOISSON, "Bière");
        stock_acheter("Bière", 10, 0);
        stock_acheter("Picon", 10, 0);

        //Test
        compte_acheter(idCompte, "Picon", 2, LocalDate.now(), 1);

        assertEquals("Devrait être 4", 4, getInfoCompte(idCompte, LocalDate.now(), TypeInformation.MOINS), 0.05);
        assertEquals("Devrait être 2", 2, getAchatCompte(idCompte, LocalDate.now(), "Picon"));
        assertEquals("Devrait être 8", 8, getQuantiteStock("Bière"), 0);
        assertEquals("Devrait être 8", 8, getQuantiteStock("Picon"), 0);

        //Nettoie la ddb
        compte_supprimer(idCompte);
        produit_supprimer("Picon");
        produit_supprimer("Bière");
    }

    @Test (expected = SQLException.class)
    public void testCompte_acheterIngredientPasAssez() throws SQLException {
        //Setup
        compte_creer(NOM, PRENOM);
        produit_inserer("Bière", 0.2, 0.3, Consommable.Categorie.BOISSON, null);
        produit_inserer("Picon", 1, 2, Consommable.Categorie.BOISSON, "Bière");
        stock_acheter("Bière", 1, 0);
        stock_acheter("Picon", 10, 0);
        int idCompte = getIdCompte(NOM, PRENOM);

        //Test
        try {
            compte_acheter(idCompte, "Picon", 2, LocalDate.now(), 1);
        }
        catch (SQLException e) {
            //Nettoie la ddb
            compte_supprimer(idCompte);
            produit_supprimer("Picon");
            produit_supprimer("Bière");
            throw e;
        }
    }
}