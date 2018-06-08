package application;

import application.gui.animation.AnimationController;
import application.gui.config.ConfigurationController;
import application.langtonsant.Controller;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    public static int refreshDelay;
    public static Long stepsLimit;
    public static Long currentSteps;
    public static List<Ant> antList;
    public static Map<Integer, SavableColor> colorMap;
    public static Canvas canvas;
    public static Controller controller;
    public static Plane plane;

    private void displayConfigDialog() throws IOException {
        Stage configStage = new Stage(StageStyle.UNIFIED);
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("gui/config/configurationGui.fxml"));
        Parent configRoot = fxmlLoader.load();
        ConfigurationController configurationController = fxmlLoader.getController();

        configStage.setTitle("Langton's Ant");
        configStage.setScene(new Scene(configRoot));
        configStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        configStage.setResizable(false);
        configStage.showAndWait();


    }

    private void setupAnimationWindowNEW() throws IOException {
        if (canvas == null)
            return;

        Stage animationStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("gui/animation/animationGUI.fxml"));
        Parent animationRoot = fxmlLoader.load();
        AnimationController animationController = fxmlLoader.getController();
        animationController.setup(555);
        animationStage.setTitle("Langton's Ant");
        animationStage.setScene(new Scene(animationRoot, canvas.getWidth() + 100, canvas.getWidth() + 100));
        animationStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        animationStage.showAndWait();
    }

    private void startApp() throws IOException {
        while (true) {
            displayConfigDialog();


            setupAnimationWindowNEW();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startApp();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
