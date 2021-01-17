package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public final class ControleurDialogVenteCompte {

    @FXML
    private TextField textField;

    @FXML
    private Label labelQuestion;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox gratuit;

    @FXML
    private Button valider;

    private String produit;

    private boolean modeModification;

    final void demanderVenteUtilisateur(Stage stage, String produit, String date) {
        modeModification = false;
        this.produit = produit;
        gratuit.setSelected(false);
        datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern(MainApp.datePattern)));
        textField.requestFocus();

        //Car le mode modification désactive ces 2 composants
        gratuit.setDisable(false);
        datePicker.setDisable(false);

        labelQuestion.setText("Combien de " + produit + " voulez-vous vendre ?");
        initTextField();

        valider.setDefaultButton(true);
        switchScene(stage);
    }

    final void modifierVenteUtilisateur(Stage stage, String produit, int quantite, String date) {
        modeModification = true;
        this.produit = produit;
        gratuit.setDisable(true);

        textField.setText(String.valueOf(quantite));
        datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern(MainApp.datePattern)));
        datePicker.setDisable(true);

        labelQuestion.setText("Modifier la quantité de " + produit + " vendue le " + date);
        initTextField();
        switchScene(stage);
    }

    private void initTextField() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                textField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    private void switchScene(Stage stage) {
        stage.setScene(MainApp.dialogVenteCompte);
    }

    @FXML
    private void annuler() {
        textField.setText("");

        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.compte);
    }

    @FXML
    private void valider() {
        try {
            if (modeModification)
                MainApp.controleurCompte.modifierAchat(produit, datePicker.getValue(), Integer.parseInt(textField.getText()));
            else
                MainApp.controleurCompte.acheter(produit, datePicker.getValue(), Integer.parseInt(textField.getText()), gratuit.isSelected());
        }
        catch (Exception e) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Erreur");
            alerte.setContentText("Veuillez entrer un nombre ou une date valide");
            alerte.setHeaderText(null);
            alerte.show();
        }
        finally {
            annuler();
        }
    }


    //---------------------------- Numeric Keypad ----------------------------//

    private void handleButtonClick(int button) {
        textField.setText(textField.getText()+button);
    }

    @FXML
    private void handle_nk_suppr() {
        String beforeRemove = textField.getText();
        if (beforeRemove.length() > 0)
            textField.setText(textField.getText(0, beforeRemove.length()-1));
    }

    @FXML
    private void handle_nk_0() {
        handleButtonClick(0);
    }

    @FXML
    private void handle_nk_1() {
        handleButtonClick(1);
    }

    @FXML
    private void handle_nk_2() {
        handleButtonClick(2);
    }

    @FXML
    private void handle_nk_3() {
        handleButtonClick(3);
    }

    @FXML
    private void handle_nk_4() {
        handleButtonClick(4);
    }

    @FXML
    private void handle_nk_5() {
        handleButtonClick(5);
    }

    @FXML
    private void handle_nk_6() {
        handleButtonClick(6);
    }

    @FXML
    private void handle_nk_7() {
        handleButtonClick(7);
    }

    @FXML
    private void handle_nk_8() {
        handleButtonClick(8);
    }

    @FXML
    private void handle_nk_9() {
        handleButtonClick(9);
    }
}
