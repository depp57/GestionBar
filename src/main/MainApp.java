package main;

import controleurs.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DataBase;

public final class MainApp extends Application {

    public static Scene menu, nouveauCompte, compte, stocks, dialogStocks, dialogVenteCompte, modifConsos, achats, dialogAchats;
    public static ControleurMenu controleurMenu;
    public static ControleurCompte controleurCompte;
    public static ControleurStocks controleurStocks;
    public static ControleurDialogStocks controleurDialogStocks;
    public static ControleurDialogVenteCompte controleurDialogVenteCompte;
    public static ControleurDialogAchats controleurDialogAchats;
    public static ControleurAchats controleurAchats;
    public static Stage stage;

    //Constante pour les dates
    public static final String datePattern = "dd/MM/yyyy";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        //Connecte à la base de donnée
        DataBase.openConnection();

        //charge les fichiers
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        menu = new Scene(fxmlLoader.load());
        controleurMenu = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NouveauCompte.fxml"));
        nouveauCompte = new Scene(fxmlLoader.load());

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Compte.fxml"));
        compte = new Scene(fxmlLoader.load());
        controleurCompte = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Stocks.fxml"));
        stocks = new Scene(fxmlLoader.load());
        controleurStocks = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DialogStocks.fxml"));
        dialogStocks = new Scene(fxmlLoader.load());
        controleurDialogStocks = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DialogVenteCompte.fxml"));
        dialogVenteCompte = new Scene(fxmlLoader.load());
        controleurDialogVenteCompte = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifConsos.fxml"));
        modifConsos = new Scene(fxmlLoader.load());

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Achats.fxml"));
        achats = new Scene(fxmlLoader.load());
        controleurAchats = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DialogAchats.fxml"));
        dialogAchats = new Scene(fxmlLoader.load());
        controleurDialogAchats = fxmlLoader.getController();

        stage.setScene(menu);
        stage.show();
        stage.setMaximized(true);
    }
}