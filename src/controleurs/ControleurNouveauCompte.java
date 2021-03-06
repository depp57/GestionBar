package controleurs;

import javafx.scene.control.TextFormatter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.MainApp;
import utils.DataBase;

import java.sql.SQLException;

public final class ControleurNouveauCompte {

    @FXML
    private Button annuler, valider;

    @FXML
    private TextField texteNom, textePrenom;

    @FXML
    private void initialize() {
        annuler.setFocusTraversable(false);
        valider.setFocusTraversable(false);

        addFormatter(texteNom, false);
        addFormatter(textePrenom, false);
    }

    static void addFormatter(TextField textField, boolean espace) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String txt = change.getText();
            if (txt.length() != 0 ) {
                //1 ere lettre en maj
                if (textField.getText().length() == 0) {
                    StringBuilder sb = new StringBuilder(txt);
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    change.setText(sb.toString());
                }

                if (!txt.matches("[a-zA-Z0-9-_.,à-é]"))
                    change.setText("");
            }
            return change;
        }));
    }

    @FXML
    public final void retourArriere() {
        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.menu);

        texteNom.setText("");
        textePrenom.setText("");

        textePrenom.requestFocus();
    }

    @FXML
    private void valider() {
        String nom = texteNom.getText(), prenom = textePrenom.getText();

        if(nom.equals("") || prenom.equals("")) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur création de compte");
            alerte.setContentText("Veuillez remplir le nom et le prénom");
            alerte.setHeaderText(null);
            alerte.show();
        }
        else {
            try {
                creerCompte(nom, prenom);

                //Rafraichit la liste des comptes du menu
                ControleurMenu controleurMenu = MainApp.controleurMenu;
                controleurMenu.afficherComptes(FXCollections.observableArrayList(controleurMenu.getIdentitesComptes()));


                //Switch simplement de scene
                MainApp.stage.setScene(MainApp.compte);
                //Passe le compte au controleur
                MainApp.controleurCompte.setCompte(nom, prenom);
            }
            catch (SQLException e) {
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Information");
                alerte.setContentText(DataBase.afficherException(e));
                alerte.setHeaderText(null);
                alerte.show();
            }
        }

        textePrenom.requestFocus();
    }

    private void creerCompte(String nom, String prenom) throws SQLException {
        DataBase.compte_creer(nom, prenom);
        texteNom.setText("");
        textePrenom.setText("");

        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Information");
        alerte.setContentText("Le compte a bien été créé");
        alerte.setHeaderText(null);
        alerte.show();
    }
}