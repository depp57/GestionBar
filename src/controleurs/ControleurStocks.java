package controleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import excelUtilities.ValeurTableStock;
import main.MainApp;

import utils.DataBase;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControleurStocks {

    @FXML
    private TableView<ValeurTableStock> tableStocks;

    @FXML
    private Label labelAchat;

    @FXML
    private Label labelVente;

    @FXML
    private Label labelBenefices;

    @FXML
    private MenuButton acheter, vendre;

    @FXML
    public void initialize() {
        chargerDonnees();
    }

    @FXML
    private void gestionDesComptes() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);
    }

    private void chargerDonnees() {
        //On vide la table
        tableStocks.getColumns().clear();

        //On ajoute les colonnes
        tableStocks.getColumns().add(getFirstColumn());
        tableStocks.getColumns().add(getDataColumn());

        //On ajoute les items dans la liste des boutons
        addItemsMenuButtons();

        majDonnees();
    }

    private void addItemsMenuButtons() {
        ObservableList<MenuItem> listeAcheter = acheter.getItems();
        ObservableList<MenuItem> listeVendre = vendre.getItems();
        listeAcheter.clear();
        listeVendre.clear();

        ArrayList<String> produits = new ArrayList<>();

        try {
            produits = DataBase.getProduits();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        for(String produitCourant : produits) {
            MenuItem itemAcheter = new MenuItem(produitCourant), itemVendre = new MenuItem(produitCourant);

            itemAcheter.setOnAction(event -> handleAcheter(produitCourant));
            itemVendre.setOnAction(event -> handleVendre(produitCourant));

            listeAcheter.add(itemAcheter);
            listeVendre.add(itemVendre);
        }
    }

    void majDonnees() {
        //On ajoute la liste des produits
        ObservableList<ValeurTableStock> donnees = getProduits();
        tableStocks.setItems(donnees);

        //Finalement on met à jour les labels
        majLabels();
        //Et les boutons
        addItemsMenuButtons();
    }

    private void majLabels() {
        try {
            double totalAchat = DataBase.getTotalAchatStock();
            double totalVente = DataBase.getTotalVenteStock();

            labelAchat.setText("Total achat : " + totalAchat + '€');
            labelVente.setText("Total vente : " + totalVente + '€');
            labelBenefices.setText("Total bénéfices : " + Math.round((totalVente - totalAchat ) * 100) / 100.0 + '€');
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<ValeurTableStock> getProduits() {
        try {
            ArrayList<String> produits = DataBase.getProduits();
            ObservableList<ValeurTableStock> liste = FXCollections.observableArrayList();

            for (String produitCourant : produits)
                liste.add(new ValeurTableStock(produitCourant, DataBase.getQuantiteStock(produitCourant)));

            return liste;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private TableColumn<ValeurTableStock, Integer> getDataColumn() {
        TableColumn<ValeurTableStock, Integer> colonne = new TableColumn<>("Quantité");
        colonne.setMinWidth(274);

        colonne.setCellFactory(param -> new TableCell<ValeurTableStock, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTableStock valeurTableStock = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    int quantite = valeurTableStock.getQuantite();
                    if (quantite < 5) {
                        setStyle("-fx-background-color: rgba(255,0,14,0.56);");
                    }
                    else if(quantite < 10){
                        setStyle("-fx-background-color: rgba(180,169,11,0.59);");
                    }
                    else
                        setStyle("-fx-background-color: rgba(62,239,71,0.51);");

                    setStyle(getStyle() + "\n" +
                            "-fx-alignment : CENTER;");

                    setText(String.valueOf(quantite));
                }
            }
        });
        return colonne;
    }

    private TableColumn<ValeurTableStock, String> getFirstColumn() {
        TableColumn<ValeurTableStock, String> colonne = new TableColumn<>("Produit");
        colonne.setMinWidth(274);

        colonne.setCellFactory(param -> new TableCell<ValeurTableStock, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTableStock valeurTableStock = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    if (DataBase.getTypeProduit(valeurTableStock.getNomProduit()).equals("Boisson")) {
                        setStyle("-fx-background-color: rgba(0,255,255,0.5)");
                    }
                    else {
                        setStyle("-fx-background-color: rgba(222,184,135,0.51)");
                    }
                    setText(valeurTableStock.getNomProduit());
                }
            }
        });
        return colonne;
    }

    @FXML
    private void handleAcheter(String produit) {
        ControleurDialogStocks controleur = MainApp.controleurDialogStocks;

        controleur.demanderNombreUtilisateur(MainApp.stage, produit, true);
    }

    @FXML
    private void handleVendre(String produit) {
        ControleurDialogStocks controleur = MainApp.controleurDialogStocks;

        controleur.demanderNombreUtilisateur(MainApp.stage, produit, false);
    }

    /**
     * TODO CHECK LES SQL EXCEPTIONS !
     */
    void acheter(String nomProduit, int quantite, boolean gratuit) {
        int grat = (gratuit) ? 0 : 1;
        try {
            DataBase.stock_acheter(nomProduit, quantite, grat);
            majDonnees();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO SAME
    void vendre(String nomProduit, int quantite, boolean gratuit) throws ExceptionStock{
        int grat = (gratuit) ? 0 : 1;
        try {
            DataBase.stock_vendre(nomProduit, quantite, grat);
            majDonnees();
        }
        catch (SQLException e) {
            throw new ExceptionStock(DataBase.afficherException(e));
        }
    }

    @FXML
    private void raccourciDoubleClic(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2){
            handleAcheter(tableStocks.getSelectionModel().getSelectedItem().getNomProduit());
        }
        else if(event.getButton().equals(MouseButton.SECONDARY)
                && event.getClickCount() == 2){
            handleVendre(tableStocks.getSelectionModel().getSelectedItem().getNomProduit());
        }
    }

    static class ExceptionStock extends Throwable {
        ExceptionStock(String message) {
            super(message);
        }
    }
}