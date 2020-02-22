package controleurs;

import utils.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.MainApp;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class ControleurCompte{

    private int idCompte;

    @FXML
    private Button retour;

    @FXML
    private Label labelProprietaireCompte;

    @FXML
    private TableView<String> dataTable;

    @FXML
    private TableView<String> dataTableBottom;

    @FXML
    private Spinner<Integer> periodeAffichage;

    private Stack<ActionCompte> pileActions;
    private CellulesCompte data;
    private CellulesCompteB dataB;

    /**
     * Méthode principale appelée pour passer un compte au controleur.
     * @param nom nom du propriétaire.
     * @param prenom prenom du propriétaire.
     */
    void setCompte(String nom, String prenom) {
        try {
            this.idCompte = DataBase.getIdCompte(nom, prenom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.pileActions = new Stack<>();
        labelProprietaireCompte.setText("Compte de " + prenom + " " + nom);

        ajoutRaccourciClavier();
        ajoutFusionScrollBars();

        //Pour pouvoir sélectionner une seule cellule
        dataTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dataTable.getSelectionModel().setCellSelectionEnabled(true);

        periodeAffichage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, 2025, 2020));
        enleverHeader(dataTableBottom);
        chargerDonnees();
    }

    private void ajoutFusionScrollBars() {
        Set<Node> scrollBars = dataTableBottom.lookupAll(".scroll-bar:horizontal");
        Set<Node> scrollBars2 = dataTable.lookupAll(".scroll-bar:horizontal");
        Iterator<Node> iterator = scrollBars2.iterator();
        iterator.next();
        ScrollBar other = (ScrollBar) iterator.next();
        for (Node node: scrollBars) {
            ScrollBar scrollBar = (ScrollBar)node;
            if ( scrollBar.getOrientation().toString().equals("HORIZONTAL"))
                scrollBar.valueProperty().addListener((observable, oldValue, newValue)
                        -> other.setValue(newValue.doubleValue()));
        }
    }

    /**
     * Affiche un message dans la fenêtre.
     * @param type Type de l'alerte.
     * @param titre Titre.
     * @param texte Corps du message.
     */
    private void showAlerte(Alert.AlertType type, String titre, String texte) {
        Alert alerte = new Alert(type);
        alerte.setTitle(titre);
        alerte.setContentText(texte);
        alerte.setHeaderText(null);
        alerte.show();
    }

    /**
     * Ajoute les raccourcis clavier.
     */
    private void ajoutRaccourciClavier() {
        //CONTROLE Z
        retour.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.Z,
                    KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    try {
                        reverse(pileActions.pop());
                    }
                    catch (Exception e) {
                        showAlerte(Alert.AlertType.INFORMATION, "Information", "Il n'y a plus d'actions à annuler !");
                    }
                }
                ke.consume();
            }
        });
    }

    private void reverse(ActionCompte actionCompte) {
        switch (actionCompte.getAction()) {
            case CREDIT: {
                try {
                    double nouvelleValeur = DataBase.getInfoCompte(idCompte, actionCompte.getDate().toString(), TypeInformation.PLUS) - actionCompte.getMontant();
                    DataBase.compte_setInfo(idCompte, actionCompte.getDate(), TypeInformation.PLUS, nouvelleValeur);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case VENTE: {
                try {
                    int quantite = actionCompte.getQuantite();
                    String produit = actionCompte.getProduit();

                    DataBase.compte_acheter(idCompte, produit, -quantite, actionCompte.getDate(), actionCompte.isGratuit() ? 0 : 1);

                    MainApp.controleurStocks.majDonnees();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        chargerDonnees();
    }


    ////////////////////////////////////////// CHARGEMENT DES DONNÉES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @FXML
    private void chargerDonnees() {
        getDatas(periodeAffichage.getValue());
    }

    public final void addData(String date, String produit, int quantite, double moins, double plus) {
        boolean dateExistante = dataB.updateLigne(date, moins, plus);
        if (produit != null)
            data.updateLigne(date, produit, quantite);
        //Si la date n'existait pas avant il faut ajouter la colonne
        if (!dateExistante) {
            dataTableBottom.getColumns().add(creerColonneB(date));
            dataTable.getColumns().add(creerColonne(date));

            dataTable.getColumns().sort((c1, c2) -> {
                final String d1 = c1.getText(), d2 = c2.getText();
                if (d1.equals("Produit")) return -1;
                else if (d2.equals("Produit")) return 1;
                final String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
                return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
            });
            dataTableBottom.getColumns().sort((c1, c2) -> {
                final String d1 = c1.getText(), d2 = c2.getText();
                if (d1.equals("Produit")) return -1;
                else if (d2.equals("Produit")) return 1;
                final String m1 = d1.substring(3, 5), m2 = d2.substring(3, 5);
                return !m1.equals(m2) ? m1.compareTo(m2) : d1.compareTo(d2);
            });
        }

        dataTable.refresh();
        dataTableBottom.refresh();
    }

    private void getDatas(int dateAafficher) {
        //Chargement depuis la BDD
        data = DataBase.getAchatsCompte(idCompte, dateAafficher);
        dataB = DataBase.getInfosCompte(idCompte, dateAafficher);

        dataTable.getColumns().clear();
        dataTableBottom.getColumns().clear();
        try {
            ArrayList<String> produits = DataBase.getProduits();

            //La première colonne (produits)
            TableColumn<String, String> firstColonne = new TableColumn<>("Produit");
            firstColonne.setMinWidth(150);
            firstColonne.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()));
            firstColonne.setCellFactory(param -> new TableCell<String, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        if (DataBase.getTypeProduit(item).equals("Boisson"))
                            setStyle("-fx-background-color: rgba(0,255,255,0.5)");
                        else
                            setStyle("-fx-background-color: rgba(222,184,135,0.51)");
                        setText(item);
                    }
                }
            });
            dataTable.getColumns().add(firstColonne);

            TableColumn<String, String> firstColonneB = new TableColumn<>("Produit");
            firstColonneB.setMinWidth(150);
            firstColonneB.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()));
            dataTableBottom.getColumns().add(firstColonneB);


            //Les colonnes suivantes (dates)
            ArrayList<TableColumn<String, String>> colonnes = new ArrayList<>();
            dataB.getHashMap().forEach((date, useless) -> colonnes.add(creerColonne(date)));
            dataTable.getColumns().addAll(colonnes);

            ArrayList<TableColumn<String, Number>> colonnesB = new ArrayList<>();
            dataB.getHashMap().forEach((date, useless) -> colonnesB.add(creerColonneB(date)));
            dataTableBottom.getColumns().addAll(colonnesB);


            //Et finalement les données..
            final ObservableList<String> donnees = FXCollections.observableArrayList(produits);
            dataTable.setItems(donnees);

            final ObservableList<String> donneesB = FXCollections.observableArrayList("Reste", "Plus", "Moins", "Total");
            dataTableBottom.setItems(donneesB);
        }
        catch (SQLException ignored) {ignored.printStackTrace();}
    }

    private TableColumn<String, String> creerColonne(final String date) {
        TableColumn<String, String> colonne = new TableColumn<>(date);
        colonne.setMinWidth(100);
        colonne.setCellValueFactory(value -> new SimpleStringProperty(Integer.toString(data.getCellule(date, value.getValue()))));
        colonne.setCellFactory(column -> new TableCell<String, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setTooltip(new Tooltip(getTableView().getItems().get(getIndex())));
                    setText(item);
                }
            }
        });
        return colonne;
    }

    private TableColumn<String, Number> creerColonneB(final String date) {
        TableColumn<String, Number> colonne = new TableColumn<>(date);
        colonne.setMinWidth(100);
        colonne.setCellValueFactory(value -> new SimpleDoubleProperty(dataB.getCellule(date, value.getValue())));
        colonne.setCellFactory(column -> new TableCell<String, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setTooltip(new Tooltip(getTableView().getItems().get(getIndex())));
                    setText(item.toString());
                    if(item.doubleValue() < 0)
                        setStyle("-fx-background-color:lightcoral");
                }
            }
        });
        return colonne;
    }

    private void enleverHeader(TableView<String> tableView) {
        Pane header = (Pane) tableView.lookup("TableHeaderRow");
        header.setMaxHeight(0);
        header.setMinHeight(0);
        header.setPrefHeight(0);
        header.setVisible(false);
    }

    private int valCelluleSelectionnee() {
        TablePosition<String, String> pos = dataTable.getSelectionModel().getSelectedCells().get(0);
        String item = dataTable.getItems().get(pos.getRow());
        return Integer.parseInt(pos.getTableColumn().getCellObservableValue(item).getValue());
    }

    ////////////////////////////////////////// MODIFICATION DES VALEURS (UTILISATEUR) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private void modifierValeur(String produit) {
        final ObservableList<TablePosition> selectedCells = dataTable.getSelectionModel().getSelectedCells();
        final int idColonne = selectedCells.get(0).getColumn();

        if (idColonne > 0 ) {
            final int quantite = valCelluleSelectionnee();
            String date = dataTable.getColumns().get(idColonne).getText();

            if (quantite != 0)
                MainApp.controleurDialogVenteCompte.modifierVenteUtilisateur(MainApp.stage, produit, quantite, date);
            else
                MainApp.controleurDialogVenteCompte.demanderVenteUtilisateur(MainApp.stage, produit, date);
        }
        else
            MainApp.controleurDialogVenteCompte.demanderVenteUtilisateur(MainApp.stage, produit, LocalDate.now().format(DateTimeFormatter.ofPattern(MainApp.datePattern)));
    }

    void acheter(String produit, LocalDate date, int quantite, boolean gratuit) {
        try {
            DataBase.compte_acheter(idCompte, produit, quantite, date, gratuit ? 0 : 1);

            pileActions.add(new ActionCompte(ActionCompte.Actions.VENTE, produit, date, quantite, gratuit));

            double moins = gratuit ? 0 : DataBase.getPrixProduit(produit)*quantite;
            addData(date.format(DateTimeFormatter.ofPattern(MainApp.datePattern)), produit, quantite, moins, 0);
            MainApp.controleurStocks.majDonnees();
        }
        catch (SQLException exceptionStock) {
            showAlerte(Alert.AlertType.WARNING, "Plus assez de stock", exceptionStock.getMessage().split("\n")[0].split("20001:")[1]);
        }
    }

    void modifierAchat(String produit, LocalDate date, int quantite) {
        DataBase.compte_modifierAchat(idCompte, produit, date, quantite);
        final String dateS = date.format(DateTimeFormatter.ofPattern(MainApp.datePattern));
        final int oldQuantite = data.getCellule(dateS, produit);
        final double moins = DataBase.getPrixProduit(produit) * (quantite - oldQuantite);
        addData(dateS, produit, quantite, moins, 0);
        MainApp.controleurStocks.majDonnees();
    }

    private Results demanderCreditUtilisateur() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Créditer le compte");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label label = new Label("   Montant : ");
        TextField textField = new TextField();
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getText().matches("[0-9]|,") || textField.getText().length() > 4) change.setText("");
            return change;
        }));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        dialogPane.setContent(new VBox(8, label, textField, datePicker));
        Platform.runLater(textField::requestFocus);

        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                try {
                    double montant = format.parse(textField.getText()).doubleValue();

                    return new Results(montant,  datePicker.getValue());
                }
                catch (ParseException e) {
                    return null;
                }
            }
            return null;
        });
        Optional<Results> optionalResults = dialog.showAndWait();
        return optionalResults.orElse(null);
    }

    private void supprimerAchat() {
        //Récupère la date et le nom du produit
        final ObservableList<TablePosition> selectedCells = dataTable.getSelectionModel().getSelectedCells();
        final int idColonne = selectedCells.get(0).getColumn();
        final String date = dataTable.getColumns().get(idColonne).getText();
        final String produit = dataTable.getSelectionModel().getSelectedItem();

        final Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
        alerte.setTitle("Supprimer");
        alerte.setContentText("Etes vous sûr de vouloir supprimer l'achat de : " + produit +
                " le " + date + " ?");
        alerte.setHeaderText(null);
        final Optional<ButtonType> option = alerte.showAndWait();

        if(option.isPresent() && option.get() == ButtonType.OK)
            DataBase.compte_modifierAchat(idCompte, produit, LocalDate.parse(date, DateTimeFormatter.ofPattern(MainApp.datePattern)), 0);

        final int oldQuantite = data.getCellule(date, produit);
        final double moins = DataBase.getPrixProduit(produit) * (-oldQuantite);
        addData(date, produit, 0, moins, 0);
        MainApp.controleurStocks.majDonnees();
    }

    ////////////////////////////////////////// FXML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @FXML
    private void retourArriere() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);
    }

    @FXML
    private void raccourciClavierListePresse(KeyEvent event) {
        if (dataTable.getSelectionModel().getSelectedItem() != null) {
            if (event.getCode().equals(KeyCode.ENTER))
                modifierValeur(dataTable.getSelectionModel().getSelectedItems().get(0));
            else if (event.getCode().equals(KeyCode.BACK_SPACE))
                supprimerAchat();
        }
    }

    @FXML
    private void raccourciDoubleClic(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2
                && dataTable.getSelectionModel().getSelectedItem() != null)
            modifierValeur(dataTable.getSelectionModel().getSelectedItems().get(0));
    }

    //Done
    @FXML
    private void crediterCompte() {
        Results entrees = demanderCreditUtilisateur();

        if(entrees == null)
            return;

        try {
            DataBase.compte_crediter(idCompte, entrees.valeur, entrees.date);

            pileActions.add(new ActionCompte(ActionCompte.Actions.CREDIT, entrees.date, entrees.valeur));

            addData(entrees.date.format(DateTimeFormatter.ofPattern(MainApp.datePattern)), null, 0, 0, entrees.valeur);
        }
        catch (SQLException e) {
            showAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer une date valide");
        }
    }

}

final class Results {

    double valeur;
    LocalDate date;

    Results(double valeur, LocalDate date) {
        this.valeur = valeur;
        this.date = date;
    }
}