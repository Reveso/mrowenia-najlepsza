package application.gui.animation;

import application.langtonsant.CoreType;
import application.langtonsant.behaviourcore.BasicAntCore;
import application.langtonsant.behaviourcore.CustomAntCore;
import application.langtonsant.behaviourcore.AbstractAntCore;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.ConfigurationSetup;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
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

    /**
     * Bieżąca ilość kroków animacji.
     */
    private Long currentSteps;
    /**
     * Typ jądra animacji.
     */
    private CoreType coreType;
    /**
     * Bieżące jądro animacji.
     */
    private AbstractAntCore currentAntCore;
    /**
     * Timeline na którym odbywa się animacja.
     */
    private Timeline timeline;

    /**
     * Ustawia animację zgodnie z danymi w instancji podanej jako parametr.
     * @param configurationSetup Konfiguracja animacji.
     */
    public void setup(ConfigurationSetup configurationSetup) {

        int refreshDelay = configurationSetup.getRefreshDelay();
        List<Ant> antList = configurationSetup.getAntList();
        Map<Integer, SavableColor> colorMap = configurationSetup.getColorMap();
        Plane plane = configurationSetup.getPlane();
        Long stepsLimit = configurationSetup.getStepsLimit();
        currentSteps = configurationSetup.getCurrentSteps();
        coreType = configurationSetup.getCoreType();

        animationCanvas.setHeight(plane.getPlaneSize()*configurationSetup.getAntSize());
        animationCanvas.setWidth(plane.getPlaneSize()*configurationSetup.getAntSize());

        if (coreType.equals(CoreType.BASIC)) {
            currentAntCore = new BasicAntCore(plane, antList, colorMap,
                    animationCanvas.getGraphicsContext2D(), configurationSetup.getAntSize());
        } else if (coreType.equals(CoreType.CUSTOM)) {
            currentAntCore = new CustomAntCore(plane, antList.get(0), colorMap,
                    animationCanvas.getGraphicsContext2D(), configurationSetup.getAntSize());
        }

        final AbstractAntCore finalAntCore = currentAntCore;
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

    /**
     * Handler dla akcji onAction przycisku pauseButton.
     * Pauzuje/startuje animację.
     */
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

    /**
     * Handler dla akcji onAction przycisku saveButton.
     * Wyświetla FileChooser, gdzie użytkownik specyfikuje lokalizację zapisu.
     * Nastepnie wywołuje saveCurrentAntCore(AbstractAntCore, File) dla właściwości
     * klasy abstractAntCore, oraz wybranej lokacji.
     * Jeśli animacja nie była zapauzowana, pauzuję animację.
     */
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

    /**
     * Handler dla akcji onMouseClicked przycisku backButton.
     * Stopuje timeline i zamyka stage.
     */
    @FXML
    private void onBackButtonMouseClicked() {
        timeline.stop();
        timeline = null;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Zapisuje jądro abstractAntCore w lokacji file.
     * @param abstractAntCore Jądro do zapisu.
     * @param file Lokalizacja zapisu.
     */
    private void saveCurrentAntCore(AbstractAntCore abstractAntCore, File file) {

        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(file)))) {
            locFile.writeObject(coreType);
            locFile.writeObject(abstractAntCore.getPlane());
            locFile.writeObject(abstractAntCore.getAntList());
            locFile.writeObject(abstractAntCore.getColorList());
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
