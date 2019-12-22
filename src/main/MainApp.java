package main;

import controleurs.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataBase;

import java.nio.file.Paths;

public class MainApp extends Application {


    public static Scene menu, nouveauCompte, compte, stocks, dialogStocks, dialogVenteCompte, modifConsos;
    public static ControleurMenu controleurMenu;
    public static ControleurCompte controleurCompte;
    public static ControleurStocks controleurStocks;
    public static ControleurDialogStocks controleurDialogStocks;
    public static ControleurDialogVenteCompte controleurDialogVenteCompte;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

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

        stage.setScene(menu);

        stage.show();

        stage.setMaximized(true);
    }
}