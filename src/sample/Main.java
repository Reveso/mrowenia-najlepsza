package sample;

import coreMiscs.Plane;
import customBehaviourCore.customBehaviourController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import coreMiscs.coreMiscs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import basicAntCore.basicAntController;

public class Main extends Application {

    public static int planeSize;
    public static int maxSteps;
    private static String behaviourString;
    public static int antCount;
    public static int startX;
    public static int startY;

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

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }

    public void stop(){
        System.out.println("podaj dugosc boku plaszczyzny");
        // ustawka planeSize
        planeSize = 10;
        System.out.println("podaj max il mrowki");
        maxSteps = 10;
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
            new basicAntController (planeSize, maxSteps, antCount,new Plane(planeSize));
        } else {
            //int startX, startY;
            System.out.println("Podaj wspolrzedne poczatkowe mrowki");
            startX = 4;
            startY = 4;
            //scanf
            new customBehaviourController(planeSize, maxSteps, behaviourString, startX, startY);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        launch(args);


    }
}
