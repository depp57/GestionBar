package controleurs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.MainApp;

import utils.DataBase;

import java.sql.SQLException;
import java.util.*;

public class ControleurMenu {

    @FXML
    private Button flecheNom, flechePrenom;

    @FXML
    private ListView<String> listeComptes;

    public void initialize() {
        initialiserTri();
        Platform.runLater(()->listeComptes.requestFocus());
    }

    private enum Tri {
        NOM_CROISSANT,
        NOM_DECROISSANT,
        PRENOM_CROISSANT,
        PRENOM_DECROISSANT
    }

    private void initialiserTri() {
        flecheNom.setRotate(90);
        flechePrenom.setRotate(90);

        tri(Tri.NOM_CROISSANT);
    }

    @FXML
    private void creerCompte() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.nouveauCompte);
    }

    @FXML
    private void boutonFlecheNom() {
        if(flecheNom.getRotate() == 90.0) {
            tri(Tri.NOM_DECROISSANT);
            flecheNom.setRotate(-90);
        }
        else {
            tri(Tri.NOM_CROISSANT);
            flecheNom.setRotate(90);
        }
    }

    @FXML
    private void boutonFlechePrenom() {
        if(flechePrenom.getRotate() == 90.0) {
            tri(Tri.PRENOM_DECROISSANT);
            flechePrenom.setRotate(-90);
        }
        else {
            tri(Tri.PRENOM_CROISSANT);
            flechePrenom.setRotate(90);
        }
    }

    private void tri(Tri sensTri) {
        List<String> comptes = getIdentitesComptes();
        Comparator<? super String> comparateur = null;

        switch (sensTri) {
            case NOM_CROISSANT :  comparateur = (Comparator<String>) String::compareTo; break;
            case NOM_DECROISSANT : comparateur = Comparator.reverseOrder(); break;
            case PRENOM_CROISSANT : comparateur = (Comparator<String>) (chaine1, chaine2) -> {
                //On récupère que le nom de la chaine
                chaine1 = chaine1.substring(30);
                chaine2 = chaine2.substring(30);

                return chaine1.compareTo(chaine2);
            }; break;
            case PRENOM_DECROISSANT : comparateur = (Comparator<String>) (chaine1, chaine2) -> {
                //On récupère que le nom de la chaine
                chaine1 = chaine1.substring(30);
                chaine2 = chaine2.substring(30);

                return chaine2.compareTo(chaine1);
            };
        }

        comptes.sort(comparateur);
        afficherComptes(FXCollections.observableArrayList(comptes));
    }

    void afficherComptes(ObservableList<String> comptes) {
        listeComptes.setItems(comptes);
        listeComptes.refresh();
    }

    /**
     * Retourne une liste contenant le nom et prénom de tous les comptes.
     * @return ArrayList contenant le nom et prénom de tous les comptes.
     */
    ArrayList<String> getIdentitesComptes() {
        ArrayList<String> comptes = new ArrayList<>();

        try {
            comptes = DataBase.getComptes();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return comptes;
    }

    @FXML
    private void quitterApplication() {
        Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
        alerte.setTitle("Quitter");
        alerte.setContentText("Etes vous sûr de quitter ?");
        alerte.setHeaderText(null);
        Optional<ButtonType> option = alerte.showAndWait();

        if(option.isPresent() && option.get() == ButtonType.OK)
            Platform.exit();
    }

    @FXML
    private void raccourciClavierListe(KeyEvent event) {
        ObservableList<String> liste = listeComptes.getItems();

        Iterator<String> iterator = liste.iterator();
        String compteCourant = "";
        String key = event.getCharacter();

        while (iterator.hasNext() && !compteCourant.startsWith(key)) {
            compteCourant = iterator.next();
        }

        if (compteCourant.startsWith(key)) {
            listeComptes.getSelectionModel().select(compteCourant);
            listeComptes.scrollTo(listeComptes.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void raccourciClavierListePresse(KeyEvent event) {
        //Entrer
        if(event.getCode().equals(KeyCode.ENTER) && listeComptes.getSelectionModel().getSelectedItem() != null ) {
            String compte = listeComptes.getSelectionModel().getSelectedItem();
            String[] str = compte.split(" ");
            String prenom = str[0];
            String nom = str[str.length-1];

            ouvrirCompte(nom, prenom);

            return;
        }
        //Supprimer
        if(event.getCode().equals(KeyCode.BACK_SPACE) && listeComptes.getSelectionModel().getSelectedItem() != null ) {
            String compte = listeComptes.getSelectionModel().getSelectedItem();
            String[] str = compte.split(" ");
            String prenom = str[0];
            String nom = str[str.length-1];

            supprimerCompte(nom, prenom);
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
    private void modifierConsos() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.modifConsos);
    }

    /**
     * Supprime un compte.
     * @param prenom Prenom associé au compte.
     * @param nom Nom associé au compte.
     */
    private void supprimerCompte(String nom, String prenom) {
        Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
        alerte.setTitle("Attention");
        alerte.setContentText("Êtes vous sûr de vouloir supprimer le compte de " + nom + " " + prenom + " ?");
        alerte.setHeaderText(null);
        Optional<ButtonType> option = alerte.showAndWait();

        if(option.isPresent() && option.get() != ButtonType.OK)
            return;

        try {
            DataBase.compte_supprimer(DataBase.getIdCompte(nom, prenom));
        }
        catch (SQLException e) {
            Alert alerte2 = new Alert(Alert.AlertType.WARNING);
            alerte2.setTitle("Erreur");
            alerte2.setContentText(DataBase.afficherException(e));
            alerte2.setHeaderText(null);
            alerte2.show();
        }

        afficherComptes(FXCollections.observableArrayList(getIdentitesComptes()));
    }

    private void ouvrirCompte(String nom, String prenom) {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.compte);

        //Passe le compte au controleur
        MainApp.controleurCompte.setCompte(nom, prenom);
    }

    @FXML
    private void gererStocks() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.stocks);
    }
}