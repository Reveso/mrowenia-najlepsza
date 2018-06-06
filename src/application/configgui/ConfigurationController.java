package application.configgui;

import application.Main;
import application.langtonsant.Controller;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import java.util.concurrent.LinkedBlockingQueue;

public class ConfigurationController {

    @FXML
    BorderPane borderPane;
    @FXML
    GridPane gridPaneOne;
    @FXML
    TextField behaviourStringTextField;
    @FXML
    Button okayButton;
    @FXML
    Button resetButton;

    private int antCount;
    private Queue<SavableColor> colorQueue;

    private String behaviourString;
    private File locationsLoadFile;

    @FXML
    public void initialize() {
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

        Main.colorMap = new LinkedHashMap<>();
        int i = 0;
        for (SavableColor color : colorList) {
            Main.colorMap.put(i, color);
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
        xPosTextField.setId("xPosTextField");
        xPosTextField.setPromptText("x");
        xPosTextField.setPrefWidth(40);
        xPosTextField.setPrefHeight(25);

        TextField yPosTextField = new TextField();
        yPosTextField.setId("yPosTextField");
        yPosTextField.setPromptText("y");
        yPosTextField.setPrefWidth(40);
        yPosTextField.setPrefHeight(25);


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

        setupBorderPaneRightChildren(true);
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

        setupBorderPaneRightChildren(true);

    }

    private void setupBorderPaneRightChildren(boolean textFieldEnabled) {
        VBox VBox = new VBox();
        Label delayLabel = new Label("Delay per Refresh");
        TextField delayTextField = new TextField("1");
        delayTextField.setId("delayTextField");
        delayTextField.setPrefHeight(25);
        delayTextField.setPrefWidth(25);

        Label planeSizeLabel = new Label("Plane Size");
        TextField planeSizeTextField = new TextField("100");
        planeSizeTextField.setId("planeSizeTextField");
        planeSizeTextField.setPrefHeight(25);
        planeSizeTextField.setPrefWidth(25);
        planeSizeTextField.setDisable(!textFieldEnabled);

        Label stepsLimitLabel = new Label("Steps limit");
        TextField stepsLimitTextField = new TextField("0");
        stepsLimitTextField.setId("stepsLimitTextField");
        stepsLimitTextField.setPrefHeight(25);
        stepsLimitTextField.setPrefWidth(25);

        VBox.getChildren().addAll(delayLabel, delayTextField, planeSizeLabel, planeSizeTextField,
                stepsLimitLabel, stepsLimitTextField);
        borderPane.setRight(VBox);
    }

    @FXML
    public void onBehaviourStringButtonMouseClicked() {
        locationsLoadFile = null;

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
        Main.currentSteps = 0L;

        if (tempBehaviourString.equals("RL")) {
            Main.controller = Controller.BASIC;
            setupBasicBehaviourConfig();
        } else {
            Main.controller = Controller.CUSTOM;
            setupCustomBehaviourConfig();
        }


        behaviourString = tempBehaviourString;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        okayButton.setDisable(false);
        resetButton.setDisable(false);
    }

    @FXML
    public void onLoadPositionFromFileButtonMouseClicked() {
        locationsLoadFile = null;

        gridPaneOne.getChildren().clear();
        borderPane.setLeft(null);
        antCount=0;

        setupBorderPaneRightChildren(false);

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
    public void onLoadPositionFromFileButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onLoadPositionFromFileButtonMouseClicked();
    }

    @FXML
    public void onBehaviourStringButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    @FXML
    public void onBehaviourStringTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    @FXML
    public void onOkayKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onOkayClicked();
    }

    private boolean loadRightBorderPaneData() {
        VBox rightVBox = (VBox) borderPane.getRight();

        for (Node node : rightVBox.getChildren()) {
            if (!node.getClass().equals(TextField.class)) {
                continue;
            }
            TextField textField = (TextField) node;
            try {
                if (textField.getId().contains("delay")) {
                    Main.refreshDelay = Integer.parseInt(textField.getText().trim());
                    if (Main.refreshDelay < 1)
                        return false;
                } else if (textField.getId().contains("planeSize")) {
                    int planeSize = Integer.parseInt(textField.getText().trim());
                    if (planeSize < 1)
                        return false;
                    else {
                        Main.plane = new Plane(planeSize);
                    }
                } else if (textField.getId().contains("stepsLimit")) {
                    Long stepsLimit = Long.parseLong(textField.getText().trim());
                    if(stepsLimit < 1) {
                        Main.stepsLimit = -1L;
                    }
                    Main.stepsLimit = stepsLimit;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean loadAntList() {
        Main.antList = new LinkedList<>();

        for (Node gridPaneChild : gridPaneOne.getChildren()) {
            if (gridPaneChild.getClass().equals(HBox.class)) {
                HBox hBox = (HBox) gridPaneChild;
                int x = -1;
                int y = -1;

                for (Node hBoxChild : hBox.getChildren()) {
                    if (!hBoxChild.getClass().equals(TextField.class)) {
                        continue;
                    }
                    TextField textField = (TextField) hBoxChild;
                    System.out.println(textField.getId());
                    try {
                        if (textField.getId().contains("xPos")) {
                            x = Integer.parseInt(textField.getText().trim());
                            System.out.println("x: " + x);
                        } else if (textField.getId().contains("yPos")) {
                            y = Integer.parseInt(textField.getText().trim());
                            System.out.println("y: " + y);
                        }
                    } catch (Exception e) {
                        x = -1;
                        y = -1;
                        e.printStackTrace();
                    }
                }

                if (x > -1 && y > -1) {
                    Ant newAnt = new Ant(x, y);
                    newAnt.interpretBehaviourString(behaviourString);
                    newAnt.setStartDirection();
                    Main.antList.add(newAnt);
                }
            }
        }

        if (Main.antList.size() < 1) {
            return false;
        } else return true;
    }

    @FXML
    public void onOkayClicked() {
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

        Main.canvas = new Canvas(Main.plane.getPlaneSize() * 5, Main.plane.getPlaneSize() * 5);
        Main.graphicsContext = Main.canvas.getGraphicsContext2D();

        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onResetClicked() {
        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("configurationGui.fxml"));
            stage.setTitle("Langton's Ant");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitClicked() {
        Main.canvas = null;
        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
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

        Main.controller = controller;
        Main.plane = plane;
        Main.antList = antList;
        Main.colorMap = colors;
        Main.currentSteps = currentSteps;
        Main.stepsLimit += currentSteps;

        Main.canvas = new Canvas(Main.plane.getPlaneSize() * 5, Main.plane.getPlaneSize() * 5);
        Main.graphicsContext = Main.canvas.getGraphicsContext2D();

        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
    }

    private void displayAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
