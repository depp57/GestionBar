package controleurs;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.MainApp;
import utils.CellulesAchat;
import utils.DataBase;
import utils.HistoriqueAchat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public final class ControleurAchats {

    @FXML
    private TableView<String> dataTable;

    @FXML
    private Spinner<Integer> periodeAffichage;

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private Tab buttonGraph;

    @FXML
    private ListView<String> listeProduits;

    private CellulesAchat data;

    final void addData(String date, String produit, int quantite, float prixUnit) {
        boolean dateExistante = data.updateLigne(date, produit, quantite, prixUnit);
        //Si la date n'existait pas avant il faut ajouter la colonne
        if (!dateExistante) {
            dataTable.getColumns().add(creerColonne(date));
            dataTable.getColumns().sort((c1, c2) -> {
                final String d1 = c1.getText(), d2 = c2.getText();
                if (d1.equals("Produit")) return -1;
                else if (d2.equals("Produit")) return 1;
                final String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
                return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
            });
        }
        dataTable.refresh();
    }

    private void clearData(String date, String produit) {
        data.clearLigne(date, produit);
        dataTable.refresh();
    }

    private void getData(int dateAafficher) {
        data = DataBase.getAchats(dateAafficher);
        try {
            ArrayList<String> produits = DataBase.getProduits();

            //La première colonne (produits)
            TableColumn<String, String> firstColonne = new TableColumn<>("Produit");
            firstColonne.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()));
            dataTable.getColumns().add(firstColonne);
            firstColonne.setStyle("-fx-pref-width: 180px");

            //Les colonnes suivantes (dates)
            ArrayList<TableColumn<String, String>> colonnes = new ArrayList<>();
            data.getHashMap().forEach((date, useless) -> colonnes.add(creerColonne(date)));
            dataTable.getColumns().addAll(colonnes);

            //Et finalement les données..
            final ObservableList<String> donnees = FXCollections.observableArrayList(produits);
            dataTable.setItems(donnees);
        }
        catch (SQLException ignored) {}
    }

    private TableColumn<String, String> creerColonne(String date) {
        TableColumn<String, String> colonne = new TableColumn<>(date);
        colonne.setResizable(false);

        //Si il y'a bcp de données affiche de manière - belle mais + performante
        if (data.getHashMap().size() < 100) {
            TableColumn<String, Number> quantite = new TableColumn<>("Quantité");
            quantite.setCellValueFactory(value -> new SimpleIntegerProperty(data.getQuantite(date, value.getValue())));
            quantite.setResizable(false);
            afficherToolTip(quantite);

            TableColumn<String, Number> prix = new TableColumn<>("PrixUnité (€)");
            prix.setCellValueFactory(value -> new SimpleFloatProperty(data.getPrixUnite(date, value.getValue())));
            prix.setResizable(false);
            afficherToolTip(prix);

            colonne.getColumns().clear();
            colonne.getColumns().addAll(quantite, prix);
        }
        else
            colonne.setCellValueFactory(value -> new SimpleStringProperty(data.getQuantiteEtPrix(date, value.getValue())));

        return colonne;
    }

    private void afficherToolTip(TableColumn<String, Number> colonne) {
        colonne.setCellFactory(column -> new TableCell<String, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setTooltip(new Tooltip(getTableView().getItems().get(getIndex())));
                    setText(item.toString());
                }
            }
        });
    }

    public final void majDatesEtDonnees(int annee) {
        dataTable.getColumns().clear();
        getData(annee);
    }

    ////////////////////////////////////////// FXML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @FXML
    private void initialize() {
        periodeAffichage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, 2025, 2019));
    }

    @FXML
    private void retourArriere() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);
    }

    private void enregistrerAchat() {
        //Récupère la date et le nom du produit
        final ObservableList<TablePosition> selectedCells = dataTable.getSelectionModel().getSelectedCells();
        final int idColonne = selectedCells.get(0).getColumn() - 1;

        MainApp.controleurDialogAchats.init(dataTable.getSelectionModel().getSelectedItem(),
                dataTable.getColumns().get(idColonne/2+1).getText());
    }

    private void supprimerAchat() {
        //Récupère la date et le nom du produit
        final ObservableList<TablePosition> selectedCells = dataTable.getSelectionModel().getSelectedCells();
        final int idColonne = selectedCells.get(0).getColumn() - 1;
        String date = dataTable.getColumns().get(idColonne/2+1).getText();
        String produit = dataTable.getSelectionModel().getSelectedItem();

        Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
        alerte.setTitle("Supprimer");
        alerte.setContentText("Etes vous sûr de vouloir supprimer l'achat de : " + produit +
                " le " + date + " ?");
        alerte.setHeaderText(null);
        Optional<ButtonType> option = alerte.showAndWait();

        if(option.isPresent() && option.get() == ButtonType.OK) {
            clearData(date, produit);
            DataBase.supprimerAchat(produit, date);
        }
    }

    @FXML
    private void raccourciDoubleClic(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2
                && dataTable.getSelectionModel().getSelectedItem() != null)
            enregistrerAchat();
    }

    @FXML
    private void raccourciClavier(KeyEvent event) {
        if (dataTable.getSelectionModel().getSelectedItem() != null) {
            if (event.getCode().equals(KeyCode.ENTER))
                enregistrerAchat();
            else if (event.getCode().equals(KeyCode.BACK_SPACE))
                supprimerAchat();
        }
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
            lineChart.getData().clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void majGraphique() {
        lineChart.getData().clear();
        String selection = listeProduits.getSelectionModel().getSelectedItem();

        HistoriqueAchat[] valeurs = DataBase.getDonneesGraphique(selection, periodeAffichage.getValue());
        if (valeurs == null)
            return;

        ObservableList<XYChart.Data<String, Double>> donnees = FXCollections.observableArrayList();

        //Affiche seulement 10 points sur le graphique (en lissant les donnéees)
        int i = 0;
        int accuracy = valeurs.length/11;
        if (accuracy > 0) {
            Double[] buffer = new Double[accuracy];
            for (HistoriqueAchat v : valeurs) {
                if (i < accuracy)
                    buffer[i] = v.prixUnite;
                else {
                    double moyenne = 0;
                    for (Double c : buffer)
                        moyenne += c;

                    moyenne /= accuracy;

                    donnees.add(new XYChart.Data<>(v.dateAchat.toString(), moyenne));
                    i = -1;
                }

                i++;
            }
        }
        else
            for (HistoriqueAchat valeurCourante : valeurs)
                donnees.add(new XYChart.Data<>(valeurCourante.dateAchat.toString(), valeurCourante.prixUnite));

        XYChart.Series<String, Double> serie = new XYChart.Series<>(selection, donnees);

        lineChart.getData().add(serie);
    }
}