package controleurs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import items.Consommable;
import main.MainApp;
import utils.DataBase;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

public class ControleurModifConsos {

    @FXML
    private ListView<String> listeConsos;

    @FXML
    private void retour() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);
    }

    @FXML
    public void initialize() {
        initialiserListeConsos();
    }

    private void initialiserListeConsos() {
        try {
            ArrayList<String> produits = DataBase.getProduits();

            listeConsos.setItems(observableArrayList(produits));
            listeConsos.refresh();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void raccourciClavierListePresse(KeyEvent event) {
        //Entrer
        if(event.getCode().equals(KeyCode.ENTER) && listeConsos.getSelectionModel().getSelectedItem() != null ) {
            String produit = listeConsos.getSelectionModel().getSelectedItem();
            modifierConso(produit);
            return;
        }

        //Supprimer
        if(event.getCode().equals(KeyCode.BACK_SPACE) && listeConsos.getSelectionModel().getSelectedItem() != null ) {
            String produit = listeConsos.getSelectionModel().getSelectedItem();

            Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
            alerte.setTitle("Supprimer");
            alerte.setContentText("Etes vous sûr de vouloir supprimer : " + produit + " ?" + "\n" +
                    "Attention, il ne sera plus possible de voir l'historique des achats d'un client pour cette consommation !");
            alerte.setHeaderText(null);
            Optional<ButtonType> option = alerte.showAndWait();

            if(option.isPresent() && option.get() == ButtonType.OK) {
                try {
                    DataBase.produit_supprimer(produit);
                    initialiserListeConsos();
                    MainApp.controleurStocks.majDonnees();
                }
                catch (SQLException e) {
                    Alert alerte2 = new Alert(Alert.AlertType.WARNING);
                    alerte2.setTitle("Attention");
                    alerte2.setContentText(DataBase.afficherException(e));
                    alerte2.setHeaderText(null);
                    alerte2.show();
                }
            }
        }
    }

    private void modifierConso(String produit) {
        Consommable conso;
        try {
            conso = DataBase.getProduit(produit);

            Dialog<Consommable> dialog = new Dialog<>();
            dialog.setTitle("Modification");
            dialog.setHeaderText("Modification de : " + produit);

            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Label labelNomProduit = new Label("   Nom du produit : ");
            TextField textNomProduit = new TextField(produit);
            ControleurNouveauCompte.addFormatter(textNomProduit);

            Label labelPrixAchat = new Label("   Prix d'achat : ");
            TextField textPrixAchat = new TextField(String.valueOf(conso.getPrixAchat()));

            Label labelPrixVente = new Label("   Prix de vente : ");
            TextField textPrixVente = new TextField(String.valueOf(conso.getPrixVente()));

            Label labelTypeProduit = new Label("   Type du produit : ");
            ChoiceBox<String> choiceBoxTypeProduit = new ChoiceBox<>();
            choiceBoxTypeProduit.getItems().add("Apéritif");
            choiceBoxTypeProduit.getItems().add("Boisson");

            if(conso.getCategorie().equals(Consommable.Categorie.BOISSON))
                choiceBoxTypeProduit.setValue("Boisson");
            else
                choiceBoxTypeProduit.setValue("Apéritif");

            Label labelingredient = new Label("   Produit consommé (picon etc)");
            ChoiceBox<String> choiceBoxIngredients = new ChoiceBox<>();
            choiceBoxIngredients.getItems().add("Aucun");
            try {
                choiceBoxIngredients.getItems().addAll(DataBase.getProduits());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String recette = conso.getNomRecette();
            choiceBoxIngredients.setValue(recette != null ? recette : "Aucun");

            dialogPane.setContent(new VBox(10, labelNomProduit, textNomProduit, labelPrixAchat, textPrixAchat,
                    labelPrixVente, textPrixVente, labelTypeProduit, choiceBoxTypeProduit, labelingredient, choiceBoxIngredients));
            Platform.runLater(textNomProduit::requestFocus);

            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    try {
                        String categorie = choiceBoxTypeProduit.getValue();
                        String ingredient = !choiceBoxIngredients.getValue().equals("Aucun") ? choiceBoxIngredients.getValue() : null;

                        textPrixAchat.setText(textPrixAchat.getText().replace('.', ','));
                        textPrixVente.setText(textPrixVente.getText().replace('.', ','));

                        //TODO RECETTE NULL !
                        return new Consommable(textNomProduit.getText(), format.parse(textPrixAchat.getText()).doubleValue(),
                                format.parse(textPrixVente.getText()).doubleValue(), ingredient, categorie);

                    }
                    catch (ParseException e) {
                        return null;
                    }
                }
                return null;
            });

            Optional<Consommable> optionalResults = dialog.showAndWait();
            if(optionalResults.isPresent()) {
                Consommable newConso = optionalResults.get();

                DataBase.produit_modifier(produit, newConso.getNom(), newConso.getPrixAchat(), newConso.getPrixVente(), newConso.getCategorie(), newConso.getNomRecette());

                initialiserListeConsos();
                MainApp.controleurStocks.majDonnees();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void raccourciDoubleClic(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2){
            raccourciClavierListePresse(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, true, true, true, true));
        }
    }

    @FXML
    private void creerConso() {

        Dialog<Consommable> dialog = new Dialog<>();
        dialog.setTitle("Ajout d'un produit");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Label labelNomProduit = new Label("   Nom du produit : ");
        TextField textNomProduit = new TextField();
        ControleurNouveauCompte.addFormatter(textNomProduit);

        Label labelPrixAchat = new Label("   Prix d'achat : ");
        TextField textPrixAchat = new TextField();

        Label labelPrixVente = new Label("   Prix de vente : ");
        TextField textPrixVente = new TextField();

        Label labelTypeProduit = new Label("   Type du produit : ");
        ChoiceBox<String> choiceBoxTypeProduit = new ChoiceBox<>();
        choiceBoxTypeProduit.getItems().add("Apéritif");
        choiceBoxTypeProduit.getItems().add("Boisson");

        Label labelingredient = new Label("   Produit consommé (picon etc)");
        ChoiceBox<String> choiceBoxIngredients = new ChoiceBox<>();
        choiceBoxIngredients.getItems().add("Aucun");
        try {
            choiceBoxIngredients.getItems().addAll(DataBase.getProduits());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dialogPane.setContent(new VBox(10, labelNomProduit, textNomProduit, labelPrixAchat, textPrixAchat,
                labelPrixVente, textPrixVente, labelTypeProduit, choiceBoxTypeProduit, labelingredient, choiceBoxIngredients));
        Platform.runLater(textNomProduit::requestFocus);

        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                try {
                    String categorie = choiceBoxTypeProduit.getValue();
                    String ingredient = !choiceBoxIngredients.getValue().equals("Aucun") ? choiceBoxIngredients.getValue() : null;

                    return new Consommable(textNomProduit.getText(), format.parse(textPrixAchat.getText()).doubleValue(),
                            format.parse(textPrixVente.getText()).doubleValue(),ingredient, categorie);

                }
                catch (ParseException e) {
                    return null;
                }
            }
            return null;
        });
        Optional<Consommable> optionalResults = dialog.showAndWait();
        if(optionalResults.isPresent()) {
            Consommable newConso = optionalResults.get();
            try {
                DataBase.produit_inserer(newConso.getNom(), newConso.getPrixAchat(), newConso.getPrixVente(), newConso.getCategorie(), newConso.getNomRecette());
                initialiserListeConsos();
                MainApp.controleurStocks.majDonnees();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}