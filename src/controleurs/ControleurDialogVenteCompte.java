package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import items.Consommable;
import main.MainApp;


public class ControleurDialogVenteCompte {

    @FXML
    private TextField textField;

    @FXML
    private Label labelQuestion;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox gratuit;

    private Consommable consommable;

    void demanderVenteUtilisateur(Stage stage, Consommable consommable) {
        this.consommable = consommable;
        gratuit.setSelected(false);

        switchScene(stage);
        initLabel();
        initTextField();
    }

    private void initLabel() {
        labelQuestion.setText("Combien de " + consommable.getNom() + " Voulez-vous vendre ?");
    }

    private void initTextField() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                textField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    private void switchScene(Stage stage) {
        stage.setScene(MainApp.getDialogVenteCompte());
    }

    @FXML
    private void annuler() {
        textField.setText("");

        Stage stage = (Stage) textField.getScene().getWindow();

        //Switch simplement de scene
        stage.setScene(MainApp.getCompte());
    }

    @FXML
    private void valider() {
        try {
            MainApp.getControleurCompte().acheter(consommable, datePicker.getValue(), Integer.parseInt(textField.getText()), gratuit.isSelected());
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