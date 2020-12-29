package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;
import utils.DataBase;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ControleurDialogAchats {

    @FXML
    private Label labelQuestion;

    @FXML
    private TextField txtFieldQ;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtFieldPrix;

    private String produit;

    final void init(String produit, String date) {
        this.produit = produit;
        datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern(MainApp.datePattern)));
        txtFieldQ.requestFocus();
        labelQuestion.setText("Combien de " + produit + " voulez-vous acheter ?");

        switchScene(MainApp.stage);
        initTextField();
    }

    private void initTextField() {
        txtFieldQ.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                txtFieldQ.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    private void switchScene(Stage stage) {
        stage.setScene(MainApp.dialogAchats);
    }

    @FXML
    private void annuler() {
        txtFieldPrix.setText("");
        txtFieldQ.setText("");

        MainApp.stage.setScene(MainApp.achats);
    }

    @FXML
    private void valider() {
        try {
            int quantite = Integer.parseInt(txtFieldQ.getText());
            float prixUnit = Float.parseFloat(txtFieldPrix.getText());
            DataBase.enregistrer_achat(produit, Date.valueOf(datePicker.getValue()),
                   quantite , prixUnit);
            MainApp.controleurAchats.addData(datePicker.getValue().format(DateTimeFormatter.ofPattern(MainApp.datePattern)), produit, quantite, prixUnit);
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

    private void handleButtonClick(int button, char keyBoard) {
        if (keyBoard == 'Q')
            txtFieldQ.setText(txtFieldQ.getText() + button);
        else
            txtFieldPrix.setText(txtFieldPrix.getText() + button);
    }

    @FXML
    private void handle_nk_suppr_Q() {
        String beforeRemove = txtFieldQ.getText();
        if (beforeRemove.length() > 0)
            txtFieldQ.setText(txtFieldQ.getText(0, beforeRemove.length()-1));
    }

    @FXML
    private void handle_nk_suppr_P() {
        String beforeRemove = txtFieldPrix.getText();
        if (beforeRemove.length() > 0)
            txtFieldPrix.setText(txtFieldPrix.getText(0, beforeRemove.length()-1));
    }

    @FXML
    private void handle_nk_dot_P() {
        txtFieldPrix.setText(txtFieldPrix.getText() + '.');
    }

    @FXML
    private void handle_nk_0_Q() {handleButtonClick(0, 'Q');}

    @FXML
    private void handle_nk_0_P() {handleButtonClick(0, 'P');}

    @FXML
    private void handle_nk_1_Q() {
        handleButtonClick(1, 'Q');
    }

    @FXML
    private void handle_nk_1_P() {handleButtonClick(1, 'P');}

    @FXML
    private void handle_nk_2_Q() {
        handleButtonClick(2, 'Q');
    }

    @FXML
    private void handle_nk_2_P() {handleButtonClick(2, 'P');}

    @FXML
    private void handle_nk_3_Q() {
        handleButtonClick(3, 'Q');
    }

    @FXML
    private void handle_nk_3_P() {handleButtonClick(3, 'P');}

    @FXML
    private void handle_nk_4_Q() {
        handleButtonClick(4, 'Q');
    }

    @FXML
    private void handle_nk_4_P() {handleButtonClick(4, 'P');}

    @FXML
    private void handle_nk_5_Q() {
        handleButtonClick(5, 'Q');
    }

    @FXML
    private void handle_nk_5_P() {handleButtonClick(5, 'P');}

    @FXML
    private void handle_nk_6_Q() {
        handleButtonClick(6, 'Q');
    }

    @FXML
    private void handle_nk_6_P() {handleButtonClick(6, 'P');}

    @FXML
    private void handle_nk_7_Q() {
        handleButtonClick(7, 'Q');
    }

    @FXML
    private void handle_nk_7_P() {handleButtonClick(7, 'P');}

    @FXML
    private void handle_nk_8_Q() {
        handleButtonClick(8, 'Q');
    }

    @FXML
    private void handle_nk_8_P() {handleButtonClick(8, 'P');}

    @FXML
    private void handle_nk_9_Q() {
        handleButtonClick(9, 'Q');
    }

    @FXML
    private void handle_nk_9_P() {handleButtonClick(9, 'P');}
}
