package application;

import application.gui.animation.AnimationController;
import application.gui.config.ConfigurationController;
import application.langtonsant.entity.ConfigurationSetup;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private boolean quit;
    private ConfigurationSetup configurationSetup;

    private void displayConfigDialog() throws IOException {

        Stage configStage = new Stage(StageStyle.UNIFIED);
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass()
                .getResource("gui/config/configurationGui.fxml"));
        Parent configRoot = fxmlLoader.load();

        ConfigurationController configurationController = fxmlLoader.getController();

        configStage.setTitle("Langton's Ant");
        configStage.setScene(new Scene(configRoot));
        configStage.setOnCloseRequest(event -> onStageCloseRequest());
        configStage.setResizable(false);
        configStage.showAndWait();

        configurationSetup = configurationController.getConfigurationSetup();
    }

    private void setupAnimationWindowNEW() throws IOException {
        if (configurationSetup == null)
            return;
        if (!configurationSetup.isComplete())
            return;

        Stage animationStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass()
                .getResource("gui/animation/animationGUI.fxml"));
        Parent animationRoot = fxmlLoader.load();

        AnimationController animationController = fxmlLoader.getController();
        animationController.setup(configurationSetup);
        animationStage.setTitle("Langton's Ant");

        int minimalSize = (configurationSetup.getPlane().getPlaneSize() * 5) + 100;
        int sceneSize = (configurationSetup.getPlane().getPlaneSize() * configurationSetup.getAntSize()) + 100;

        if(sceneSize < minimalSize)
            sceneSize = minimalSize;

        animationStage.setScene(new Scene(animationRoot, sceneSize, sceneSize));
        animationStage.setOnCloseRequest(event -> onStageCloseRequest());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double availableHeight = screenBounds.getHeight() * 3 / 4;
        if (availableHeight < sceneSize) {
            animationStage.setMaximized(false);
            animationStage.setMaximized(true);
            animationStage.toFront();
        }

        animationStage.showAndWait();
    }

    private void startApp() throws IOException {
        while (!quit) {
            displayConfigDialog();
            setupAnimationWindowNEW();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        quit = false;
        startApp();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onStageCloseRequest() {
        quit = true;
        Platform.exit();
        System.exit(0);
    }
}
