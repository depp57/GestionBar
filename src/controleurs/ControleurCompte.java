package controleurs;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import utils.ActionCompte;
import excelUtilities.ValeurTableBottom;
import excelUtilities.ValeurTablePrincipale;
import items.Consommable;
import items.TypeInformation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.MainApp;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import utils.DataBase;

public class ControleurCompte{

    private int idCompte;

    @FXML
    private Button retour;

    @FXML
    private Label labelProprietaireCompte;

    @FXML
    private TableView<ValeurTablePrincipale> dataTable;

    @FXML
    private TableView<ValeurTableBottom> dataTableBottom;

    private Stack<ActionCompte> pileActions;


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

        DataBase.clearDatesInutilesCompte(idCompte);
        DataBase.compte_init(idCompte);

        chargerDonnees();

        ajoutRaccourciClavier();

        ajoutFusionScrollBars();


        //TODO pour séléctionner 1 seule cellule
        dataTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dataTable.getSelectionModel().setCellSelectionEnabled(true);
        //TODO
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
        retour.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.Z,
                    KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    try {
                        reverse(pileActions.pop());
                        Thread.sleep(300);
                    }
                    catch (Exception e) {
                        showAlerte(Alert.AlertType.INFORMATION, "Information", "Il n'y a plus d'actions à annuler !");
                    }
                }
            }
        });
    }

    //TODO
    private void reverse(ActionCompte actionCompte) {
        switch (actionCompte.getAction()) {
            case CREDIT: {
                try {
                    double nouvelleValeur = DataBase.getInfoCompte(idCompte, actionCompte.getDate(), TypeInformation.PLUS) - actionCompte.getMontant();
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
                    Consommable consommable = actionCompte.getConsommable();

                    //TODO CHECK SI JUSTE
                    DataBase.compte_acheter(idCompte, consommable.getNom(), -quantite, actionCompte.getDate(), actionCompte.isGratuit() ? 0 : 1);


                    //Cas spécial recette Picon.. TODO
                    MainApp.getControleurStocks().majDonnees();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        majDataTable();
    }


    ////////////////////////////////////////// CHARGEMENT DES DONNÉES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private void chargerDonnees() {
        chargerDonneesCentral();
        chargerDonneesBottom();
    }

    private void chargerDonneesCentral() {
        //On vide la table
        dataTable.getColumns().clear();

        //On ajoute les colonnes
        dataTable.getColumns().add(getFirstColumn());
        dataTable.getColumns().addAll(getDataColumns());

        //On ajoute la liste des produits
        ObservableList<ValeurTablePrincipale> donnees = getProduits();
        getDonnees(donnees);
        dataTable.setItems(donnees);
    }

    private void chargerDonneesBottom() {
        enleverHeader(dataTableBottom);

        //On vide la table
        dataTableBottom.getColumns().clear();

        //On ajoute les colonnes
        dataTableBottom.getColumns().add(getFirstColumnBottom());
        dataTableBottom.getColumns().addAll(getDataColumnsBottom());


        //On ajoute les données
        ObservableList<ValeurTableBottom> donnees = getInformationsBottom();
        getDonneesBottom(donnees);
        dataTableBottom.setItems(donnees);
    }

    //TODO
    private void getDonneesBottom(ObservableList<ValeurTableBottom> donnees) {
        try {
            int nbColonnes = dataTable.getColumns().size();

            for (int i = 1; i < nbColonnes; i++) {

                for (ValeurTableBottom valeurCourante : donnees) {
                    TypeInformation typeInformation = valeurCourante.getTypeInformation();

                    valeurCourante.add(DataBase.getInfoCompte(idCompte, getDateColonne(i), typeInformation));

                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO MAYBE FAUT AJOUTER UN DATEFORMATTER https://www.mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
    private LocalDate getDateColonne(int index) {
        ObservableList<TableColumn<ValeurTablePrincipale, ?>> listeColonnes = dataTable.getColumns();

        TableColumn<ValeurTablePrincipale, ?> colonneCourante = listeColonnes.get(index);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        return LocalDate.parse(colonneCourante.getText(), dateTimeFormatter);
    }

    private ObservableList<ValeurTableBottom> getInformationsBottom() {
        ObservableList<ValeurTableBottom> liste = FXCollections.observableArrayList();

        for (TypeInformation typeInformation : TypeInformation.values())
            liste.add(new ValeurTableBottom(typeInformation, new ArrayList<>()));

        return liste;
    }

    private TableColumn<ValeurTableBottom, String>[] getDataColumnsBottom() {
        TableColumn<ValeurTableBottom, String>[] tableColumns = new TableColumn[dataTable.getColumns().size() - 1];
        int index = -1;

        for (TableColumn<ValeurTablePrincipale, ?> currentColumn : dataTable.getColumns()) {
            if (index == -1) {
                index++;
                continue;
            }

            String date = currentColumn.getText();
            tableColumns[index] = new TableColumn<>();

            if(date.equals("AUJOURD'HUI"))
                tableColumns[index].getStyleClass().add("bold");

            tableColumns[index].setMinWidth(100);
            myCellFactoBottom(tableColumns[index], index);

            index++;
        }
        return tableColumns;
    }

    private void myCellFactoBottom(TableColumn<ValeurTableBottom, String> tableColumn, int index) {
        tableColumn.setCellFactory(param -> new TableCell<ValeurTableBottom, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTableBottom valeurTableBottom = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    if (valeurTableBottom.getTypeInformation() == TypeInformation.TOTAL &&
                            Double.parseDouble(valeurTableBottom.getMontant(index).split("€")[0]) < 0)
                        setStyle("-fx-background-color: red");

                    setText(valeurTableBottom.getMontant(index));
                }
            }
        });
    }

    private TableColumn<ValeurTableBottom, String> getFirstColumnBottom() {
        TableColumn<ValeurTableBottom, String> colonne = new TableColumn<>("");
        colonne.setMinWidth(150);
        colonne.setCellValueFactory(new PropertyValueFactory<>("texte"));
        return colonne;
    }

    private void enleverHeader(TableView tableView) {
        Pane header = (Pane) tableView.lookup("TableHeaderRow");
        header.setMaxHeight(0);
        header.setMinHeight(0);
        header.setPrefHeight(0);
        header.setVisible(false);
    }

    private TableColumn<ValeurTablePrincipale, String> getFirstColumn() {
        TableColumn<ValeurTablePrincipale, String> colonne = new TableColumn<>("");
        colonne.setMinWidth(150);

        colonne.setCellFactory(param -> new TableCell<ValeurTablePrincipale, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTablePrincipale valeurTablePrincipale = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    if (valeurTablePrincipale.getConsommable().getCategorie().equals(Consommable.Categorie.BOISSON)) {
                        setStyle("-fx-background-color: rgba(0,255,255,0.5)");
                    }
                    else {
                        setStyle("-fx-background-color: rgba(222,184,135,0.51)");
                    }
                    setText(valeurTablePrincipale.getNomConso());
                }
            }
        });
        return colonne;
    }

    private TableColumn<ValeurTablePrincipale, Integer>[] getDataColumns() {
        String[] dates = DataBase.compte_getAllDates(idCompte);
        TableColumn<ValeurTablePrincipale, Integer>[] tableColumns = new TableColumn[dates.length];


        for(int i = 0; i < dates.length; i++) {
            tableColumns[i] = new TableColumn<>(dates[i]);
            tableColumns[i].setMinWidth(100);
            myCellFacto(tableColumns[i], i);
        }

        return tableColumns;
    }

    private void myCellFacto(TableColumn<ValeurTablePrincipale, Integer> tableColumn, int index) {
        tableColumn.setCellFactory(param -> new TableCell<ValeurTablePrincipale, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    int currentIndex = indexProperty()
                            .getValue() < 0 ? 0
                            : indexProperty().getValue();
                    ValeurTablePrincipale valeurTablePrincipale = param
                            .getTableView().getItems()
                            .get(currentIndex);

                    setText(String.valueOf(valeurTablePrincipale.getQuantite(index)));
                }
            }
        });
    }

    //DONE
    private ObservableList<ValeurTablePrincipale> getProduits() {
        ObservableList<ValeurTablePrincipale> liste = FXCollections.observableArrayList();

        try {
            ArrayList<String> produits = DataBase.getProduits();

            for(String produitCourant : produits)
                liste.add(new ValeurTablePrincipale(DataBase.getProduit(produitCourant), null));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }


    //TODO CHANGER l'id
    private void getDonnees(ObservableList<ValeurTablePrincipale> listeProduits) {
        for (ValeurTablePrincipale listeProduit : listeProduits) {
            Consommable nomProduit = listeProduit.getConsommable();

            int nbColonnes = dataTable.getColumns().size();
            ArrayList<Integer> quantitesCourante = new ArrayList<>(nbColonnes);

            //Cas normaux où le client a acheté au moins 1 fois le produit
            if (DataBase.aAcheteProduit(idCompte, nomProduit.getNom())) {
                try {
                    for (int i = 1; i < nbColonnes; i++)
                        quantitesCourante.add(DataBase.getAchatCompte(idCompte, getDateColonne(i), nomProduit.getNom()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //Cas où le client n'a jamais acheté le produit
            else
                for (int i = 1; i < nbColonnes; i++)
                    quantitesCourante.add(0);

            listeProduit.setQuantite(quantitesCourante);
        }
    }



    ////////////////////////////////////////// MODIFICATION DES VALEURS (UTILISATEUR) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private void modifierValeur(Consommable consommable) {
        final ObservableList<TablePosition> selectedCells = dataTable.getSelectionModel().getSelectedCells();
        final int idColonne = selectedCells.get(0).getColumn();

        if (idColonne > 0 ) {
            final int quantite = dataTable.getSelectionModel().getSelectedItem().getQuantite(idColonne-1);
            System.out.println(quantite);
            if (quantite != 0) {
                System.out.println("TODO ligne 425 !");
            }

            //TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // Si quantite != 0 alors on modifie la quantite, sinon on fait bien le demanderVente...
        }

        MainApp.getControleurDialogVenteCompte().demanderVenteUtilisateur((Stage) retour.getScene().getWindow(), consommable);
    }

    //TODO DataBase.compte_acheter(idCompte, consommable.getNom(), quantite, date, 0); 0 A METTRE EN VARIABLE (gratuit)
    void acheter(Consommable consommable, LocalDate date, int quantite, boolean gratuit) {
        try {
            DataBase.compte_acheter(idCompte, consommable.getNom(), quantite, date, gratuit ? 0 : 1);

            pileActions.add(new ActionCompte(ActionCompte.Actions.VENTE, consommable, date, quantite, gratuit));

            majDataTable();
            MainApp.getControleurStocks().majDonnees();
        }
        catch (SQLException exceptionStock) {
            showAlerte(Alert.AlertType.WARNING, "Plus assez de stock", exceptionStock.getMessage().split("\n")[0].split("20001:")[1]);
        }
    }

    private Results demanderCreditUtilisateur() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Créditer le compte");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label label = new Label("   Montant : ");
        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*.?(\\d)*"))
                Platform.runLater(textField::clear);
        });
        DatePicker datePicker = new DatePicker();

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

    private void majDataTable() {
        chargerDonnees();

        //Table principale
        ObservableList<ValeurTablePrincipale> donnees = dataTable.getItems();
        getDonnees(donnees);
        dataTable.setItems(donnees);
        dataTable.refresh();

        //Table bottom
        ObservableList<ValeurTableBottom> donneesBottom = dataTableBottom.getItems();
        getDonneesBottom(donneesBottom);
        dataTableBottom.setItems(donneesBottom);
        dataTableBottom.refresh();
    }



    ////////////////////////////////////////// FXML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @FXML
    private void retourArriere() {
        //Switch simplement de scene
        Stage stage = (Stage) retour.getScene().getWindow();

        stage.setScene(MainApp.getMenu());
    }

    @FXML
    private void raccourciClavierListePresse(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            modifierValeur(dataTable.getSelectionModel().getSelectedItem().getConsommable());
    }

    @FXML
    private void raccourciDoubleClic(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2 && dataTable.getSelectionModel().getSelectedItem() != null){
            modifierValeur(dataTable.getSelectionModel().getSelectedItem().getConsommable());
        }
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

            majDataTable();
        }
        catch (SQLException e) {
            showAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer une date valide");
        }
    }
}


class Results {

    double valeur;
    LocalDate date;

    Results(double valeur, LocalDate date) {
        this.valeur = valeur;
        this.date = date;
    }
}