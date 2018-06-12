package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa uruchamiająca aplikację javaFX.
 */
public class Main extends Application {

    /**
     * Tworzy okno konfiguracji z pliku application/gui/config/configurationGUI.fxml.
     * @throws IOException
     */
    private void displayConfigDialog(Stage configStage) throws IOException {
        Parent configRoot = FXMLLoader.load(getClass()
                .getResource("gui/config/configurationGUI.fxml"));

        configStage.setTitle("Langton's Ant");
        configStage.setScene(new Scene(configRoot));
        configStage.setResizable(false);
        configStage.show();
    }

    /**
     * Wywołuje displayConfigDialog(Stage) parametrem primaryStage.
     * @param primaryStage Stage stworzony przez platformę.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        displayConfigDialog(primaryStage);
    }

    /**
     * Klasa główna. Uruchamia aplikację metodą launch(String) z klasy javafx.application.Application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
