package application;

import application.core.behaviourcontroller.Controller;
import application.core.miscs.Ant;
import application.core.miscs.Plane;
import application.core.behaviourcontroller.CustomBehaviourController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import application.core.miscs.CoreMiscs;
import application.core.behaviourcontroller.BasicAntController;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

//        graphicsContext.setFill(Color.BLUE);
//        graphicsContext.fillRect(100, 100, 20, 20);
//
//        graphicsContext.setFill(Color.GOLD);
//        graphicsContext.fillRect(101, 101, 5, 5);
//
//        graphicsContext.setFill(Color.BLUE);
//        graphicsContext.fillRect(0, 0, 10, 10);
//
//        graphicsContext.setFill(Color.RED);
//        graphicsContext.fillRect(10, 0, 10, 10);
//
//        graphicsContext.setFill(Color.BLUE);
//        graphicsContext.fillRect(20, 0, 10, 10);

        if(controller == Controller.BASIC) {
            new BasicAntController(planeSize, new Plane(planeSize), antList, graphicsContext, 5);
        } else if (controller == Controller.CUSTOM) {
            new CustomBehaviourController(planeSize, behaviourString, antList.get(0), graphicsContext, 5);
        }

    }

    public static void main(String[] args) {
        launch(args);


    }
}
