package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;

public final class ControleurDialogStocks {

    @FXML
    private CheckBox gratuit;
    
    @FXML
    private TextField textField;

    @FXML
    private Label labelQuestion;


    private String nomProduit;
    private boolean achat;


    final void demanderNombreUtilisateur(Stage stage, String nomProduit, boolean achat) {
        this.nomProduit = nomProduit;
        this.achat = achat;

        gratuit.setSelected(true);

        if(!achat)
            gratuit.setText("Vendre gratuitement ?");
        else
            gratuit.setText("Acheter gratuitement ?");

        switchScene(stage);
        initTextField();
        initLabel();
    }

    private void initLabel() {
        if (achat)
            labelQuestion.setText("Combien de " + nomProduit + " Voulez-vous acheter ?");
        else
            labelQuestion.setText("Combien de " + nomProduit + " Voulez-vous vendre ?");
    }

    private void initTextField() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            if (newValue.length() > 3)
                textField.setText(newValue.substring(0,3));
        });

        textField.requestFocus();
    }

    private void switchScene(Stage stage) {
        //Switch simplement de scene
        stage.setScene(MainApp.dialogStocks);
    }

    @FXML
    private void annuler() {
        textField.setText("");

        //Switch simplement de scene
        MainApp.stage.setScene(MainApp.stocks);
    }

    @FXML
    private void valider() {
        if (!textField.getText().equals("")) {
            if (achat)
                MainApp.controleurStocks.acheter(nomProduit, Integer.parseInt(textField.getText()), gratuit.isSelected());
            else {
                try {
                    MainApp.controleurStocks.vendre(nomProduit, Integer.parseInt(textField.getText()), gratuit.isSelected());
                }
                catch (ControleurStocks.ExceptionStock exceptionStock) {
                    Alert alerte = new Alert(Alert.AlertType.WARNING);
                    alerte.setTitle("Plus assez de stock");
                    alerte.setContentText(exceptionStock.getMessage());
                    alerte.setHeaderText(null);
                    alerte.show();
                }
            }
        }
        annuler();
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