package application;

import application.core.behaviourcontroller.BasicAntCore;
import application.core.behaviourcontroller.Controller;
import application.core.behaviourcontroller.CustomBehaviourCore;
import application.core.entity.Ant;
import application.core.entity.Plane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class Main extends Application {
    public static int planeSize;
    public static int refreshDelay;
    public static List<Ant> antList;
    public static Map<Integer, Color> colorMap;
    public static Canvas canvas;
    public static GraphicsContext graphicsContext;
    public static Controller controller;

    private void displayConfigDialog() throws IOException {
        Stage configStage = new Stage(StageStyle.UTILITY);
        Parent configRoot = FXMLLoader.load(getClass().getResource("gui/configurationGui.fxml"));
        configStage.setTitle("Langton's Ant");
        configStage.setScene(new Scene(configRoot));
        configStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        configStage.setResizable(false);
        configStage.showAndWait();
    }

    public void setupAnimationWindow(Stage primaryStage) {
        if(canvas == null)
            return;

        primaryStage.setTitle("Langton's Ant");

        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
//        Group animationRoot = new Group();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        Scene scene = new Scene(borderPane, canvas.getWidth() + 100, canvas.getWidth() + 100);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);

        scrollPane.setContent(canvas);
        borderPane.setCenter(scrollPane);
//        animationRoot.getChildren().addAll(canvas);

        graphicsContext.strokeLine(0+5, 0+5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(0+5, 0+5, 0+5, canvas.getHeight()-5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, 0+5, canvas.getHeight()-5);

        TimerTask antCore = null;
        if(controller == Controller.BASIC) {
            antCore = new BasicAntCore(new Plane(planeSize), antList, graphicsContext, 5);
        } else {
            antCore = new CustomBehaviourCore(new Plane(planeSize), antList.get(0), graphicsContext, 5);
        }

        final TimerTask finalAntCore = antCore;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(refreshDelay),
                        event -> finalAntCore.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayConfigDialog();
        setupAnimationWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
