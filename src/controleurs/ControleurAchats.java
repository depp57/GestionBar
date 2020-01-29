package controleurs;

import items.AchatProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import main.MainApp;
import utils.DataBase;
import utils.ValeurTableAchats;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControleurAchats {

    @FXML
    private TableView<ValeurTableAchats> dataTable;

    @FXML
    private Spinner<Integer> periodeAffichage;

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private Tab buttonGraph;

    @FXML
    private ListView<String> listeProduits;


    private TableColumn<ValeurTableAchats, String>[] getDataColumn(int dateAafficher) {
        try {
            ArrayList<String> produits = DataBase.getProduits();

            ObservableList<ValeurTableAchats> donnees = FXCollections.observableArrayList();

            String[] dates = DataBase.achats_getAllDates(dateAafficher);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            TableColumn<ValeurTableAchats, String>[] tableColumns = new TableColumn[dates.length];


            for (String produitCourant : produits) {
                AchatProduit[] valeurs = new AchatProduit[dates.length];
                for (int i = 0; i < dates.length; i++) {
                    tableColumns[i] = new TableColumn<>(dates[i]);
                    tableColumns[i].setMinWidth(100);
                    myCellFacto(tableColumns[i], i);
                    valeurs[i] = DataBase.stock_getAchat(produitCourant, LocalDate.parse(dates[i], dateTimeFormatter));
                }
                donnees.add(new ValeurTableAchats(produitCourant, valeurs));
            }

            dataTable.setItems(donnees);
            return tableColumns;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void myCellFacto(TableColumn<ValeurTableAchats, String> tableColumn, int index) {
        tableColumn.setCellFactory(param -> new TableCell<ValeurTableAchats, String>() {
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

                    setText(String.valueOf(valeurTableAchats.getValeur(index) != null ? valeurTableAchats.getValeur(index) : 0));
                }
            }
        });
    }

    private TableColumn<ValeurTableAchats, ?> getFirstColumn() {
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
                    } else {
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

    @FXML
    private void majLinechart() {
        if(!buttonGraph.isSelected())
            return;

        try {
            listeProduits.setItems(FXCollections.observableArrayList(DataBase.getProduits()));

            XYChart.Series<String, Double> values = new XYChart.Series<>();
            values.setName("Test");

            values.getData().add(new XYChart.Data<>("Janvier", 1.0));
            values.getData().add(new XYChart.Data<>("Février", 2.0));
            values.getData().add(new XYChart.Data<>("Mars", 3.0));
            values.getData().add(new XYChart.Data<>("Avril", 4.0));
            values.getData().add(new XYChart.Data<>("Mai", 5.0));
            values.getData().add(new XYChart.Data<>("Juin", 6.0));
            values.getData().add(new XYChart.Data<>("Juillet", 7.0));
            values.getData().add(new XYChart.Data<>("Août", 8.0));
            values.getData().add(new XYChart.Data<>("Septembre", 9.0));
            values.getData().add(new XYChart.Data<>("Octobre", 10.0));
            values.getData().add(new XYChart.Data<>("Novembre", 11.0));
            values.getData().add(new XYChart.Data<>("Décembre", 12.0));


            lineChart.getData().add(values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}