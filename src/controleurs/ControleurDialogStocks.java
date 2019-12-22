package controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;

public class ControleurDialogStocks {

    @FXML
    private CheckBox gratuit;
    
    @FXML
    private TextField textField;

    @FXML
    private Label labelQuestion;


    private String nomProduit;
    private boolean achat;


    void demanderNombreUtilisateur(Stage stage, String nomProduit, boolean achat) {
        this.nomProduit = nomProduit;
        this.achat = achat;

        gratuit.setSelected(false);

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
        stage.setScene(MainApp.getDialogStocks());

        //Centre le stage
        stage.setX(stage.getX() + stage.getWidth()/2);
        stage.setY(stage.getY() + stage.getHeight()/2);
    }

    @FXML
    private void annuler() {
        textField.setText("");

        Stage stage = (Stage) gratuit.getScene().getWindow();

        //Décentre le stage
        stage.setX(stage.getX() - stage.getWidth()/2);
        stage.setY(stage.getY() - stage.getHeight()/2);

        //Switch simplement de scene
        stage.setScene(MainApp.getStocks());
    }

    @FXML
    private void valider() {
        if (!textField.getText().equals("")) {
            if (achat)
                MainApp.getControleurStocks().acheter(nomProduit, Integer.parseInt(textField.getText()), gratuit.isSelected());
            else {
                try {
                    MainApp.getControleurStocks().vendre(nomProduit, Integer.parseInt(textField.getText()), gratuit.isSelected());
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
}