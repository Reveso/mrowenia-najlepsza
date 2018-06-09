package application.gui.animation;

import application.langtonsant.Controller;
import application.langtonsant.behaviourcore.BasicAntCore;
import application.langtonsant.behaviourcore.CustomAntCore;
import application.langtonsant.behaviourcore.SavableAntCore;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import application.langtonsant.entity.SetupConfiguration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.List;
import java.util.Map;

public class AnimationController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button pauseButton;
    @FXML
    private TextField stepCountTextField;
    @FXML
    private Canvas animationCanvas;

    private Long stepsLimit;
    private Long currentSteps;
    private Controller controller;

    private Timeline timeline;
    private SavableAntCore currentAntCore;

    public void setup(SetupConfiguration setupConfiguration) {

        int refreshDelay = setupConfiguration.getRefreshDelay();
        List<Ant> antList = setupConfiguration.getAntList();
        Map<Integer, SavableColor> colorMap = setupConfiguration.getColorMap();
        Plane plane = setupConfiguration.getPlane();
        stepsLimit = setupConfiguration.getStepsLimit();
        currentSteps = setupConfiguration.getCurrentSteps();
        controller = setupConfiguration.getController();

        animationCanvas.setHeight(plane.getPlaneSize()*setupConfiguration.getAntSize());
        animationCanvas.setWidth(plane.getPlaneSize()*setupConfiguration.getAntSize());

        if (controller.equals(Controller.BASIC)) {
            currentAntCore = new BasicAntCore(plane, antList, colorMap, animationCanvas.getGraphicsContext2D(), setupConfiguration.getAntSize());
        } else if (controller.equals(Controller.CUSTOM)) {
            currentAntCore = new CustomAntCore(plane, antList.get(0), colorMap, animationCanvas.getGraphicsContext2D(), setupConfiguration.getAntSize());
        }

        final SavableAntCore finalAntCore = currentAntCore;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(refreshDelay),
                        event -> {
                            if (finalAntCore.run()) {
                                currentSteps++;
                                if (stepsLimit.equals(currentSteps)) {
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
        stepCountTextField.setText("Steps: " + currentSteps);
    }

    @FXML
    private void onPauseButtonAction() {
        if (pauseButton.getText().toLowerCase().equals("pause")) {
            timeline.pause();
            pauseButton.setText("Play");
        } else {
            timeline.play();
            pauseButton.setText("Pause");
        }
    }

    @FXML
    private void onSaveButtonMouseClicked() {
        timeline.pause();

        FileChooser fileChooser = new FileChooser();
        fileChooser.initialDirectoryProperty().setValue(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Choose save location");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Locations data", "*.dat"));

        Stage stage = (Stage) borderPane.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            saveCurrentAntCore(currentAntCore, selectedFile);
        }

        pauseButton.setText("Play");
    }

    @FXML
    private void onBackButtonMouseClicked() {
        timeline.stop();
        timeline = null;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    private void saveCurrentAntCore(SavableAntCore savableAntCore, File file) {

        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
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
