package application;

import application.core.behaviourcontroller.Controller;
import application.core.miscs.Ant;
import application.core.miscs.Plane;
import application.core.behaviourcontroller.CustomBehaviourCore;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import application.core.behaviourcontroller.BasicAntCore;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    public static String behaviourString;
    public static int planeSize;
    public static int refreshDelay;
    public static List<Ant> antList;
    public static Map<Integer, Color> colorMap;
    public static Canvas canvas;
    public static GraphicsContext graphicsContext;
    public static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {

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

        if(canvas == null)
            return;

        Group animationRoot = new Group();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        Scene scene = new Scene(animationRoot, canvas.getWidth(), canvas.getWidth());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        animationRoot.getChildren().addAll(canvas);

        Runnable antCore = null;
        if(controller == Controller.BASIC) {
            antCore = new BasicAntCore(planeSize, new Plane(planeSize), antList, graphicsContext, 5);
        } else if (controller == Controller.CUSTOM) {
            antCore = new CustomBehaviourCore(planeSize, behaviourString, antList.get(0), graphicsContext, 5);
        }

        final Runnable finalAntCore = antCore;
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1),
                        event -> finalAntCore.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void main(String[] args) {
        launch(args);


    }
}
