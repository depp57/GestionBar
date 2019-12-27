package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import items.Consommable;
import main.MainApp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

    private boolean modeModification;

    void demanderVenteUtilisateur(Stage stage, Consommable consommable, String date) {
        modeModification = false;
        this.consommable = consommable;
        gratuit.setSelected(false);
        datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        //Car le mode modification désactive ces 2 composants
        gratuit.setDisable(false);
        datePicker.setDisable(false);

        labelQuestion.setText("Combien de " + consommable.getNom() + " voulez-vous vendre ?");
        initTextField();
        switchScene(stage);
    }

    void modifierVenteUtilisateur(Stage stage, Consommable consommable, int quantite, String date) {
        modeModification = true;
        this.consommable = consommable;
        gratuit.setDisable(true);

        textField.setText(String.valueOf(quantite));
        datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        datePicker.setDisable(true);

        labelQuestion.setText("Modifier la quantité de " + consommable.getNom() + " vendue le " + date);
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
            if (modeModification) {
                MainApp.controleurCompte.modifierAchat(consommable, datePicker.getValue(), Integer.parseInt(textField.getText()));
            }
            else
                MainApp.controleurCompte.acheter(consommable, datePicker.getValue(), Integer.parseInt(textField.getText()), gratuit.isSelected());
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