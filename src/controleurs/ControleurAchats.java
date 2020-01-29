package controleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainApp;
import utils.DataBase;
import utils.ValeurTableAchats;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControleurAchats {

    @FXML
    private Button retour;

    @FXML
    private TableView<ValeurTableAchats> dataTable;

    @FXML
    private Spinner<Integer> periodeAffichage;


    private void majDonnees() {
        //On ajoute la liste des produits
        ObservableList<ValeurTableAchats> donnees = getAchats();
        dataTable.setItems(donnees);
    }

    private ObservableList<ValeurTableAchats> getAchats() {
        try {
            ArrayList<String> produits = DataBase.getProduits();
            ObservableList<ValeurTableAchats> liste = FXCollections.observableArrayList();

            for (String produitCourant : produits)
                liste.add(new ValeurTableAchats(produitCourant, null));

            return liste;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private TableColumn<ValeurTableAchats, String>[] getDataColumn(int dateAafficher) {
        String[] dates = DataBase.achats_getAllDates(dateAafficher);
        TableColumn<ValeurTableAchats, String>[] tableColumns = new TableColumn[dates.length];

        for(int i = 0; i < dates.length; i++) {
            tableColumns[i] = new TableColumn<>(dates[i]);
            tableColumns[i].setMinWidth(100);
            //myCellFacto(tableColumns[i], i);
        }

        return tableColumns;
    }

    private TableColumn<ValeurTableAchats,?> getFirstColumn() {
        TableColumn<ValeurTableAchats, String> colonne = new TableColumn<>("Produit");
        colonne.setMinWidth(274);

        colonne.setCellFactory(param -> new TableCell<ValeurTableAchats, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTableAchats valeurTableAchats = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    if (DataBase.getTypeProduit(valeurTableAchats.getNomProduit()).equals("Boisson")) {
                        setStyle("-fx-background-color: rgba(0,255,255,0.5)");
                    }
                    else {
                        setStyle("-fx-background-color: rgba(222,184,135,0.51)");
                    }
                    setText(valeurTableAchats.getNomProduit());
                }
            }
        });
        return colonne;
    }

    private void majDatesEtDonnees(int annee) {
        dataTable.getColumns().clear();

        //On ajoute les colonnes
        dataTable.getColumns().add(getFirstColumn());
        //affiche seulement l'année choisie
        dataTable.getColumns().addAll(getDataColumn(annee));

        //Et on ajoute les données
        majDonnees();
    }

    ////////////////////////////////////////// FXML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * Initialise une seule fois la dataTable.
     */
    @FXML
    private void initialize() {
        majDatesEtDonnees(2020);
        periodeAffichage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, 2025, 2020));
    }

    @FXML
    private void retourArriere() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);
    }

    @FXML
    private void raccourciDoubleClic() {
        //TODO
    }

    @FXML
    private void modifierPeriodeAffichage() {
        //affiche seulement l'année choisie
        Integer annee = periodeAffichage.getValue();
        majDatesEtDonnees(annee);
    }
}