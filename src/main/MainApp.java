package main;

import controleurs.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataBase;

import java.nio.file.Paths;

public class MainApp extends Application {

    private double x, y;

    private static Scene menu, nouveauCompte, compte, stocks, dialogStocks, dialogVenteCompte, modifConsos;
    private static ControleurMenu controleurMenu;
    private static ControleurCompte controleurCompte;
    private static ControleurStocks controleurStocks;
    private static ControleurDialogStocks controleurDialogStocks;
    private static ControleurDialogVenteCompte controleurDialogVenteCompte;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Connecte à la base de donnée
        DataBase.openConnection();

        //charge les fichiers
        FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/Menu.fxml").toUri().toURL());
        Parent parent = fxmlLoader.load();
        controleurMenu = fxmlLoader.getController();
        menu = new Scene(parent);

        parent = FXMLLoader.load(Paths.get("resources/fxml/NouveauCompte.fxml").toUri().toURL());
        nouveauCompte = new Scene(parent);

        fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/Compte.fxml").toUri().toURL());
        parent = fxmlLoader.load();
        controleurCompte = fxmlLoader.getController();
        compte = new Scene(parent);

        fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/Stocks.fxml").toUri().toURL());
        parent = fxmlLoader.load();
        controleurStocks = fxmlLoader.getController();
        stocks = new Scene(parent);

        fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/DialogStocks.fxml").toUri().toURL());
        parent = fxmlLoader.load();
        controleurDialogStocks = fxmlLoader.getController();
        dialogStocks = new Scene(parent);

        fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/DialogVenteCompte.fxml").toUri().toURL());
        parent = fxmlLoader.load();
        controleurDialogVenteCompte = fxmlLoader.getController();
        dialogVenteCompte = new Scene(parent);

        fxmlLoader = new FXMLLoader(Paths.get("resources/fxml/ModifConsos.fxml").toUri().toURL());
        parent = fxmlLoader.load();
        modifConsos = new Scene(parent);

        ajouterDragScene(menu, primaryStage);
        ajouterDragScene(nouveauCompte, primaryStage);
        ajouterDragScene(compte, primaryStage);
        ajouterDragScene(stocks, primaryStage);
        ajouterDragScene(dialogVenteCompte, primaryStage);

        primaryStage.setScene(menu);

        primaryStage.show();
    }

    public static Scene getMenu() { return menu; }

    public static Scene getNouveauCompte() {
        return nouveauCompte;
    }

    public static Scene getCompte() {
        return compte;
    }

    public static Scene getStocks() {
        return stocks;
    }

    public static Scene getDialogStocks() { return dialogStocks; }

    public static Scene getDialogVenteCompte() { return dialogVenteCompte; }

    public static Scene getModifConsos() { return modifConsos; }

    public static ControleurMenu getControleurMenu() { return controleurMenu; }

    public static ControleurCompte getControleurCompte() { return controleurCompte; }

    public static ControleurStocks getControleurStocks() { return controleurStocks; }

    public static ControleurDialogStocks getControleurDialogStocks() {
        return controleurDialogStocks;
    }

    public static ControleurDialogVenteCompte getControleurDialogVenteCompte() {
        return controleurDialogVenteCompte;
    }


    private void ajouterDragScene(Scene scene, Stage stage) {
        //drag it here
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

        });
    }
}