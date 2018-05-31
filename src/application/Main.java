package application;

import application.core.behaviourcontroller.BasicAntCore;
import application.core.behaviourcontroller.Controller;
import application.core.behaviourcontroller.CustomBehaviourCore;
import application.core.behaviourcontroller.SavableAntCore;
import application.core.entity.Ant;
import application.core.entity.Plane;
import application.core.entity.SavableColor;
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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class Main extends Application {
    public static int planeSize;
    public static int refreshDelay;
    public static List<Ant> antList;
    public static Map<Integer, SavableColor> colorMap;
    public static Canvas canvas;
    public static GraphicsContext graphicsContext;
    public static Controller controller;
    public static Plane plane;

    private Timeline timeline;
    private SavableAntCore currentAntCore;

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
        Button saveButton = new Button("Save");


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
            timeline.pause();
            saveCurrentAntCore(currentAntCore);
            pauseButton.setText("Play");
        });

        lowerHbox.getChildren().addAll(pauseButton, saveButton);
        scrollPane.setContent(canvas);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(lowerHbox);
//        animationRoot.getChildren().addAll(canvas);

        graphicsContext.strokeLine(0+5, 0+5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(0+5, 0+5, 0+5, canvas.getHeight()-5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, 0+5, canvas.getHeight()-5);

        if(controller.equals(Controller.BASIC)) {
            currentAntCore = new BasicAntCore(new Plane(planeSize), antList, graphicsContext, 5);
        } else if (controller.equals(Controller.CUSTOM)){
            currentAntCore = new CustomBehaviourCore(new Plane(planeSize), antList.get(0), graphicsContext, 5);
        } else if (controller.equals(Controller.LOADED_BASIC)) {
            currentAntCore = new BasicAntCore(plane, antList, graphicsContext, 5);
        } else if (controller.equals(Controller.LOADED_CUSTOM)) {
            currentAntCore = new CustomBehaviourCore(plane, antList.get(0), graphicsContext, 5);
            System.out.println("SFAASF");
        }

        final SavableAntCore finalAntCore = currentAntCore;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(refreshDelay),
                        event -> {
                            finalAntCore.run();
                            currentAntCore = finalAntCore;
                            System.out.println("tak");
                        })
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

    private void saveCurrentAntCore(SavableAntCore savableAntCore) {
        String string = "locations.dat";

        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(string)))){
            locFile.writeObject(savableAntCore.getPlane());
            locFile.writeObject(savableAntCore.getAntList());
            locFile.writeObject(savableAntCore.getColorList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Information");
        alert.setHeaderText("Save has been completed successfully!");
        alert.showAndWait();
    }
}
