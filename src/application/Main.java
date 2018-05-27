package application;

import application.core.miscs.Plane;
import application.core.behaviourcontroller.CustomBehaviourController;
import javafx.application.Application;
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

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

/*
        System.out.println("podaj dugosc boku plaszczyzny");
        // ustawka planeSize
        planeSize = 10;
        System.out.println("podaj max il mrowki");
        maxSteps = 5;
        // ustawka maxSteps
        System.out.println("podaj algorytm zachowania");
        // ustawka behaviourString
        behaviourString = "RL";


        if (coreMiscs.checkBehaviourString(behaviourString, "RL") == false) {
            System.err.println("return -1");
            return;
        }
        if (behaviourString.equals("RL")) {
            //int antCount;
            System.out.println("Dawaj ilosc mrowek");
            antCount = 1;
            //scanf
            new basicAntController(planeSize, maxSteps, antCount,new Plane(planeSize));
        } else {
            //int startX, startY;
            System.out.println("Podaj wspolrzedne poczatkowe mrowki");
            startX = 4;
            startY = 4;
            //scanf
            new customBehaviourController(planeSize, maxSteps, behaviourString, startX, startY);
        }
        System.out.println();


*/

//        Parent root = FXMLLoader.load(getClass().getResource("gui/sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        Group root = new Group();
        primaryStage.show();

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(800, 800);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().addAll(canvas);

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(100, 100, 20, 20);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(101, 101, 5, 5);

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(0, 0, 10, 10);

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(10, 0, 10, 10);

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(20, 0, 10, 10);


        int planesize = 100;
        int maxSteps = 20000;
        int antCount = 1;
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(0, Color.GREEN);
        colors.put(1, Color.BLUE);
        colors.put(2, Color.CYAN);
        colors.put(3, Color.RED);
        colors.put(4, Color.YELLOW);

//        new BasicAntController(planesize, maxSteps, antCount, new Plane(planesize), graphicsContext, 5);
        new CustomBehaviourController(planesize, maxSteps, "RLLR", 50, 50, graphicsContext,
                5, colors);

    }

//    public void stop(){
//        System.out.println("podaj dugosc boku plaszczyzny");
//        // ustawka planeSize
//        planeSize = 10;
//        System.out.println("podaj max il mrowki");
//        maxSteps = 10;
//        // ustawka maxSteps
//        System.out.println("podaj algorytm zachowania");
//        // ustawka behaviourString
//        behaviourString = "RL";
//
//
//        if (CoreMiscs.checkBehaviourString(behaviourString, "RL") == false) {
//            System.err.println("return -1");
//            return;
//        }
//        if (behaviourString.equals("RL")) {
//            //int antCount;
//            System.out.println("Dawaj ilosc mrowek");
//            antCount = 1;
//            //scanf
//            new BasicAntController(planeSize, maxSteps, antCount,new Plane(planeSize));
//        } else {
//            //int startX, startY;
//            System.out.println("Podaj wspolrzedne poczatkowe mrowki");
//            startX = 4;
//            startY = 4;
//            //scanf
//            new CustomBehaviourController(planeSize, maxSteps, behaviourString, startX, startY);
//        }
//        System.out.println();
//    }

    public static void main(String[] args) {
        launch(args);


    }
}
