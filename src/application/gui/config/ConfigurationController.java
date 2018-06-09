package application.gui.config;

import application.langtonsant.Controller;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import application.langtonsant.entity.SetupConfiguration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConfigurationController {


    @FXML
    private BorderPane borderPane;
    @FXML
    private GridPane gridPaneOne;
    @FXML
    private TextField behaviourStringTextField;
    @FXML
    private Button okayButton;
    @FXML
    private Button resetButton;
    @FXML
    private TextField delayTextField;
    @FXML
    private TextField planeSizeTextField;
    @FXML
    private TextField stepsLimitTextField;
    @FXML
    private TextField antSizeTextField;

    private int antCount;
    private Map<TextField, TextField> antTextFieldsMap;
    private Queue<SavableColor> colorQueue;

    private String behaviourString;
    private File locationsLoadFile;

    private SetupConfiguration setupConfiguration;

    @FXML
    private void initialize() {
        setupConfiguration = new SetupConfiguration();
        antCount = 0;
        setupColorCollections();
    }

    private void setupColorCollections() {
        colorQueue = new LinkedBlockingQueue<>();
        colorQueue.add(new SavableColor(Color.BLUE));
        colorQueue.add(new SavableColor(Color.PINK));
        colorQueue.add(new SavableColor(Color.CORAL));
        colorQueue.add(new SavableColor(Color.YELLOW));
        colorQueue.add(new SavableColor(Color.CHOCOLATE));
        colorQueue.add(new SavableColor(Color.CORNFLOWERBLUE));
        colorQueue.add(new SavableColor(Color.DARKTURQUOISE));
        colorQueue.add(new SavableColor(Color.VIOLET));
        colorQueue.add(new SavableColor(Color.SILVER));
        colorQueue.add(new SavableColor(Color.TURQUOISE));
        colorQueue.add(new SavableColor(Color.TAN));
        colorQueue.add(new SavableColor(Color.TEAL));
        colorQueue.add(new SavableColor(Color.THISTLE));
        colorQueue.add(new SavableColor(Color.WHEAT));
        colorQueue.add(new SavableColor(Color.SIENNA));
        colorQueue.add(new SavableColor(Color.SADDLEBROWN));
        colorQueue.add(new SavableColor(Color.SEASHELL));
        colorQueue.add(new SavableColor(Color.SALMON));
        colorQueue.add(new SavableColor(Color.PURPLE));
        colorQueue.add(new SavableColor(Color.PLUM));
        colorQueue.add(new SavableColor(Color.PERU));
        colorQueue.add(new SavableColor(Color.PALEGOLDENROD));
        colorQueue.add(new SavableColor(Color.ORANGE));
        colorQueue.add(new SavableColor(Color.LIME));
        colorQueue.add(new SavableColor(Color.LINEN));
        colorQueue.add(new SavableColor(Color.KHAKI));
        colorQueue.add(new SavableColor(Color.INDIGO));
        colorQueue.add(new SavableColor(Color.GOLD));
        colorQueue.add(new SavableColor(Color.DARKVIOLET));
        colorQueue.add(new SavableColor(Color.BLANCHEDALMOND));
        colorQueue.add(new SavableColor(Color.BROWN));
        colorQueue.add(new SavableColor(Color.BEIGE));
        colorQueue.add(new SavableColor(Color.BISQUE));
        colorQueue.add(new SavableColor(Color.DARKORANGE));
        colorQueue.add(new SavableColor(Color.DEEPPINK));
        colorQueue.add(new SavableColor(Color.FUCHSIA));
        colorQueue.add(new SavableColor(Color.RED));
        colorQueue.add(new SavableColor(Color.CYAN));
        colorQueue.add(new SavableColor(Color.CORAL));
        colorQueue.add(new SavableColor(Color.BLACK));
        shuffleQueue();
    }

    private void shuffleQueue() {
        List<SavableColor> colorList = new ArrayList<>(colorQueue);
        Collections.shuffle(colorList);
        colorQueue = new LinkedBlockingQueue<>();
        colorQueue.addAll(colorList);

        setupConfiguration.setColorMap(new LinkedHashMap<>());
        int i = 0;
        for (SavableColor color : colorList) {
            setupConfiguration.getColorMap().put(i, color);
            i++;
        }
    }

    private void addAntPosFields() {
        if (antCount + 1 > colorQueue.size()) {
            displayAlert("Too much Ants", "Cannot add more Ants");
            return;
        }

        HBox startingPositionsTextFieldsHBox = new HBox(10);
        startingPositionsTextFieldsHBox.setAlignment(Pos.CENTER);

        Label label = new Label("Ant " + (antCount + 1));
        TextField xPosTextField = new TextField();
        xPosTextField.setPromptText("x");
        xPosTextField.setPrefWidth(40);
        xPosTextField.setPrefHeight(25);

        TextField yPosTextField = new TextField();
        yPosTextField.setPromptText("y");
        yPosTextField.setPrefWidth(40);
        yPosTextField.setPrefHeight(25);

        antTextFieldsMap.put(xPosTextField, yPosTextField);

        startingPositionsTextFieldsHBox.getChildren().addAll(label, xPosTextField, yPosTextField);

        gridPaneOne.add(startingPositionsTextFieldsHBox, 0, antCount);

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (stage.getHeight() > (screenSize.height / 2)) {
            stage.setHeight(screenSize.height / 2);
        }
        antCount++;
    }

    private void setupCustomBehaviourConfig() {
        Label startingPositionsLabel = new Label("Ant's starting position");
        BorderPane.setAlignment(startingPositionsLabel, Pos.CENTER);
        borderPane.setLeft(startingPositionsLabel);

        resetBorderPaneRightChildren(false);
        addAntPosFields();
    }

    private void setupBasicBehaviourConfig() {
        VBox leftVBox = new VBox(10);
        Label startingPositionsLabel = new Label("Ant's starting position");

        Button addAntButton = new Button("Add ant");
        addAntButton.setOnMouseClicked(event -> addAntPosFields());

        BorderPane.setAlignment(leftVBox, Pos.CENTER);
        leftVBox.getChildren().addAll(startingPositionsLabel, addAntButton);
        borderPane.setLeft(leftVBox);

        resetBorderPaneRightChildren(false);
    }

    private void resetBorderPaneRightChildren(boolean textFieldDisabled) {
        stepsLimitTextField.setText("0");
        delayTextField.setText("1");
        planeSizeTextField.setText("100");
        planeSizeTextField.setDisable(textFieldDisabled);
    }

    @FXML
    private void onBehaviourStringButtonMouseClicked() {
        locationsLoadFile = null;
        setupConfiguration.setComplete(false);

        String tempBehaviourString = behaviourStringTextField.getText().trim();
        if(!checkBehaviourString(tempBehaviourString)) {
            System.out.println(behaviourStringTextField.getText());
            displayAlert("Wrong Behaviour String", "Behaviour String can only contain letters R and L");
            return;
        }

        if(tempBehaviourString.length() > colorQueue.size()) {
            displayAlert("Too long string", "Max length is: " + colorQueue.size());
            return;
        }

        gridPaneOne.getChildren().clear();
        antCount=0;
        antTextFieldsMap = new LinkedHashMap<>();
        setupConfiguration.setCurrentSteps(0L);

        if (tempBehaviourString.equals("RL")) {
            setupConfiguration.setController(Controller.BASIC);
            setupBasicBehaviourConfig();
        } else {
            setupConfiguration.setController(Controller.CUSTOM);
            setupCustomBehaviourConfig();
        }

        behaviourString = tempBehaviourString;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        okayButton.setDisable(false);
        resetButton.setDisable(false);
    }

    @FXML
    private void onLoadPositionFromFileButtonMouseClicked() {
        locationsLoadFile = null;
        setupConfiguration.setComplete(false);

        gridPaneOne.getChildren().clear();
        borderPane.setLeft(null);
        antCount=0;
        antTextFieldsMap = new LinkedHashMap<>();


        resetBorderPaneRightChildren(true);

        HBox loadFileHbox = new HBox(10);
        TextField selectFileLocationTextField = new TextField();
        selectFileLocationTextField.setDisable(true);
        Button selectFileLocationButton = new Button("Select File Location");

        Stage stage = (Stage) borderPane.getScene().getWindow();

        selectFileLocationButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.initialDirectoryProperty().setValue(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Open saved locations file");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Locations data", "*.dat"));

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                selectFileLocationTextField.setText(selectedFile.getAbsolutePath());
                locationsLoadFile = selectedFile;
            }

        });

        loadFileHbox.getChildren().addAll(selectFileLocationTextField, selectFileLocationButton);
        gridPaneOne.getChildren().add(loadFileHbox);

        stage.sizeToScene();
        okayButton.setDisable(false);
        resetButton.setDisable(false);
    }

    @FXML
    private void onLoadPositionFromFileButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onLoadPositionFromFileButtonMouseClicked();
    }

    @FXML
    private void onBehaviourStringButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    @FXML
    private void onBehaviourStringTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    @FXML
    private void onOkayKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onOkayClicked();
    }

    private boolean loadRightBorderPaneData() {
        try {
            int refreshDelay = Integer.parseInt(delayTextField.getText().trim());
            if (refreshDelay < 1) {
                return false;
            }
            setupConfiguration.setRefreshDelay(refreshDelay);

            int planeSize = Integer.parseInt(planeSizeTextField.getText().trim());
            if (planeSize < 1)
                return false;
            else {
                setupConfiguration.setPlane(new Plane(planeSize));
            }

            Long stepsLimit = Long.parseLong(stepsLimitTextField.getText().trim());
            if (stepsLimit < 1) {
                setupConfiguration.setStepsLimit(-1L);
            }
            setupConfiguration.setStepsLimit(stepsLimit);

            int antSize = Integer.parseInt(antSizeTextField.getText().trim());
            if(antSize < 1) {
                return false;
            }
            setupConfiguration.setAntSize(antSize);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean loadAntList() {
        setupConfiguration.setAntList(new LinkedList<>());
        int planeSize = setupConfiguration.getPlane().getPlaneSize();

        for(TextField xTextField : antTextFieldsMap.keySet()) {
            int x, y;
            TextField yTextField = antTextFieldsMap.get(xTextField);

            try {
                x = Integer.parseInt(xTextField.getText().trim());
                y = Integer.parseInt(yTextField.getText().trim());
            } catch (NumberFormatException e) {
                x = -1;
                y = -1;
                e.printStackTrace();
            }

            if (x > 0 && y > 0 && x < planeSize && y < planeSize) {
                Ant newAnt = new Ant(x, y);
                newAnt.interpretBehaviourString(behaviourString);
                newAnt.setStartDirection();
                setupConfiguration.getAntList().add(newAnt);
            }
        }

        if (setupConfiguration.getAntList().size() < 1) {
            return false;
        } else return true;

    }

    @FXML
    private void onOkayClicked() {
        if(locationsLoadFile != null) {
            loadSavedAntCore(locationsLoadFile);
            return;
        }

        if (!loadRightBorderPaneData()) {
            displayAlert("Wrong data", "Right Pane");
            return;
        }

        if (!loadAntList()) {
            displayAlert("Wrong data", "Ant List");
            return;
        }

        setupConfiguration.setComplete(true);
        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onResetClicked() {
        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../animation/configurationGui.fxml"));
            stage.setTitle("Langton's Ant");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExitClicked() {
        Platform.exit();
        System.exit(0);
    }

    private boolean checkBehaviourString(String behaviourString) {
        if (behaviourString == null) return false;
        if (behaviourString.length() < 2) return false;

        for (int i = 0; i < behaviourString.length(); i++) {
            if (behaviourString.charAt(i) != 'R' && behaviourString.charAt(i) != 'L') {
                return false;
            }
        }
        return true;
    }

    private void loadSavedAntCore(File file) {
        Controller controller;
        Plane plane;
        List<Ant> antList;
        Map<Integer, SavableColor> colors;
        Long currentSteps;

        try (ObjectInput locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            controller = (Controller) locFile.readObject();
            plane = (Plane) locFile.readObject();
            antList = (List<Ant>) locFile.readObject();
            colors = (Map<Integer, SavableColor>) locFile.readObject();
            currentSteps = (Long) locFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            displayAlert("File Incorrect", "Couldn't load a file");
            return;
        }

        if (!loadRightBorderPaneData()) {
            displayAlert("Wrong data", "Right Pane");
            return;
        }

        setupConfiguration.setController(controller);
        setupConfiguration.setPlane(plane);
        setupConfiguration.setAntList(antList);
        setupConfiguration.setColorMap(colors);
        setupConfiguration.setCurrentSteps(currentSteps);
        setupConfiguration.setStepsLimit(setupConfiguration.getStepsLimit() + currentSteps);

        setupConfiguration.setComplete(true);

        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
    }

    private void displayAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public SetupConfiguration getSetupConfiguration() {
        return setupConfiguration;
    }
}