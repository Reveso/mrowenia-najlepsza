package application;

import application.langtonsant.behaviourcore.BasicAntCore;
import application.langtonsant.Controller;
import application.langtonsant.behaviourcore.CustomAntCore;
import application.langtonsant.behaviourcore.SavableAntCore;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    public static int refreshDelay;
    public static Long stepsLimit;
    public static Long currentSteps;
    public static List<Ant> antList;
    public static Map<Integer, SavableColor> colorMap;
    public static Canvas canvas;
    public static GraphicsContext graphicsContext;
    public static Controller controller;
    public static Plane plane;

    private Timeline timeline;
    private SavableAntCore currentAntCore;

    private void displayConfigDialog() throws IOException {
        Stage configStage = new Stage(StageStyle.UNIFIED);
        Parent configRoot = FXMLLoader.load(getClass().getResource("configgui/configurationGui.fxml"));
        configStage.setTitle("Langton's Ant");
        configStage.setScene(new Scene(configRoot));
        configStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        configStage.setResizable(false);
        configStage.showAndWait();
    }

    private void setupAnimationWindow(Stage primaryStage) {
        if(canvas == null)
            return;

        primaryStage.setTitle("Langton's Ant");

        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
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
        Button backToSettingsButton = new Button("Back to Settings");

        TextField stepCountTextField = new TextField("Steps: " + currentSteps);


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

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose save location");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Locations data", "*.dat"));

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                saveCurrentAntCore(currentAntCore, selectedFile);
            }

            pauseButton.setText("Play");
        });

        backToSettingsButton.setOnMouseClicked(event -> resetApplication());

        lowerHbox.getChildren().addAll(pauseButton, saveButton, backToSettingsButton, stepCountTextField);
        scrollPane.setContent(canvas);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(lowerHbox);

        if(controller.equals(Controller.BASIC)) {
            currentAntCore = new BasicAntCore(plane, antList, colorMap, graphicsContext, 5);
        } else if (controller.equals(Controller.CUSTOM)){
            currentAntCore = new CustomAntCore(plane, antList.get(0), colorMap, graphicsContext, 5);
        }

        final SavableAntCore finalAntCore = currentAntCore;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(refreshDelay),
                        event -> {
                            if(finalAntCore.run()) {
                                currentSteps++;
                                if(stepsLimit.equals(currentSteps)) {
                                    timeline.pause();
                                    pauseButton.setText("Play");
                                }
                                stepCountTextField.setText("Steps: " + currentSteps);
                            } else {
                                timeline.pause();
                                pauseButton.setText("Play");
                            }
                        })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void resetApplication() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayConfigDialog();
        setupAnimationWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void saveCurrentAntCore(SavableAntCore savableAntCore, File file) {

        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
            locFile.writeObject(controller);
            locFile.writeObject(savableAntCore.getPlane());
            locFile.writeObject(savableAntCore.getAntList());
            locFile.writeObject(savableAntCore.getColorList());
            locFile.writeObject(currentSteps);
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Saving Error");
            alert.setHeaderText("Cannot save file in that location");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Information");
        alert.setHeaderText("Save has been completed successfully!");
        alert.showAndWait();
    }
}
