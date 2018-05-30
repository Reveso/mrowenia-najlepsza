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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    Timeline timeline;
    TimerTask antCore;
    BasicAntCore basicAntCore;
    CustomBehaviourCore customBehaviourCore;
//    basicAntCore = new BasicAntCore(new Plane(planeSize), antList, graphicsContext, 5);
    public Plane plane;
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

        HBox lowerHbox = new HBox(10);
        BorderPane.setAlignment(lowerHbox, Pos.CENTER_LEFT);

        Button pauseButton = new Button("Pause");
        Button saveButton = new Button ( "Save");
        Button loadButton = new Button ( "LoadPrevious");
        lowerHbox.getChildren().add(pauseButton);
        lowerHbox.getChildren().add(saveButton);
        lowerHbox.getChildren().add(loadButton);
        pauseButton.setOnMouseClicked(event -> {
            if (pauseButton.getText().toLowerCase().equals("pause")) {
                timeline.pause();
                pauseButton.setText("Play");
            } else {
                timeline.play();
                pauseButton.setText("Pause");
            }
        });
        saveButton.setOnMouseClicked(event -> {
            save(basicAntCore);
            timeline.pause();
            pauseButton.setText("Play");
//
        });
        loadButton.setOnMouseClicked(event -> {
            timeline.pause();
            pauseButton.setText("Play");
            load();
            antCore = new BasicAntCore(plane, antList, graphicsContext, 5);

        });

        scrollPane.setContent(canvas);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(lowerHbox);
//        animationRoot.getChildren().addAll(canvas);

        graphicsContext.strokeLine(0+5, 0+5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(0+5, 0+5, 0+5, canvas.getHeight()-5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, 0+5, canvas.getHeight()-5);
        plane = new Plane(planeSize);
        if(controller == Controller.BASIC) {
            antCore = new BasicAntCore(plane, antList, graphicsContext, 5);
//            antCore = basicAntCore;
        } else {
            antCore = new CustomBehaviourCore(plane, antList.get(0), graphicsContext, 5);
//            antCore = customBehaviourCore;
        }

        final TimerTask finalAntCore = antCore;
        timeline = new Timeline(
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
    public void save(BasicAntCore basicAntCore){

//        public static List<Ant> antList;
//        plane
        String string = "locations.dat";
        try {
            Files.deleteIfExists(Paths.get(string));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(string)))){

            try {
//                locFile.writeBoolean(true);
                locFile.writeObject(plane);
                for(Ant ant: antList)
                    locFile.writeObject(ant);
                locFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Information");
        alert.setHeaderText("Save has completed succesfully");
//        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
    }
    public void load(){
//        public static List<Ant> antList;
//        plane
        String string = "locations.dat";
        ObjectInputStream loccFile = null;
        try(ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(string)))) {
            loccFile=locFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            plane= (Plane) loccFile.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        antList.clear();
        boolean eof = false;
        while (!eof) {

            try {
                antList.add((Ant)loccFile.readObject());
            } catch (EOFException e) {eof = true;} catch (IOException e) {
                eof = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
