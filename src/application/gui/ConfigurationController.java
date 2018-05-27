package application.gui;

import application.Main;
import application.core.behaviourcontroller.Controller;
import application.core.miscs.Ant;
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
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
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
    private Queue<Color> colorQueue;

    @FXML
    public void initialize() {
        antCount = 0;
        setupColorCollecitons();
    }

    private void setupColorCollecitons() {
        colorQueue = new LinkedBlockingQueue<>();
        colorQueue.add(Color.BLUE);
        colorQueue.add(Color.PINK);
        colorQueue.add(Color.CORAL);
        colorQueue.add(Color.YELLOW);
        colorQueue.add(Color.CHOCOLATE);
        shuffleQueue();
    }

    private void shuffleQueue() {
        List<Color> colorList = new ArrayList<>(colorQueue);
        Collections.shuffle(colorList);
        colorQueue = new LinkedBlockingQueue<>();
        colorQueue.addAll(colorList);

        Main.colorMap = new HashMap<>();
        int i=0;
        for (Color color : colorList) {
            Main.colorMap.put(i, color);
            i++;
        }
    }

    private void addAntPosFields() {
        if(antCount+1 > colorQueue.size()) {
            displayAlert("Too much Ants", "Cannot add more Ants");
            return;
        }

        HBox startingPositionsTextFieldsHBox = new HBox(10);
        startingPositionsTextFieldsHBox.setAlignment(Pos.CENTER);

        Label label = new Label("Ant " + (antCount+1));
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

        setupBorderPaneRightChildren();
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

        setupBorderPaneRightChildren();

    }

    private void setupBorderPaneRightChildren() {
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

        VBox.getChildren().addAll(delayLabel, delayTextField, planeSizeLabel, planeSizeTextField);
        borderPane.setRight(VBox);
    }

    @FXML
    public void onBehaviourStringButtonMouseClicked() {
        String behaviourString = behaviourStringTextField.getText().trim();
        if(!checkBehaviourString(behaviourString)) {
            System.out.println(behaviourStringTextField.getText());
            displayAlert("Wrong Behaviour String", "Behaviour String can only contain letters R and L");
            return;
        }

        if (behaviourString.equals("RL")) {
            Main.controller = Controller.BASIC;
            setupBasicBehaviourConfig();
        } else {
            Main.controller = Controller.CUSTOM;
            setupCustomBehaviourConfig();
        }

        Main.behaviourString = behaviourString;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        okayButton.setDisable(false);
        resetButton.setDisable(false);
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
        VBox rightVBox = (VBox)borderPane.getRight();

        for (Node node : rightVBox.getChildren()) {
            if(!node.getClass().equals(TextField.class)) {
                continue;
            }
            TextField textField = (TextField) node;
            try {
                if (textField.getId().contains("delay")) {
                    Main.refreshDelay = Integer.parseInt(textField.getText().trim());
                    if(Main.refreshDelay < 1)
                        return false;
                } else if(textField.getId().contains("planeSize")){
                    Main.planeSize = Integer.parseInt(textField.getText().trim());
                    if(Main.planeSize < 1)
                        return false;
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
            if(gridPaneChild.getClass().equals(HBox.class)){
                HBox hBox = (HBox) gridPaneChild;
                int x = -1;
                int y = -1;

                for (Node hBoxChild : hBox.getChildren()) {
                    if(!hBoxChild.getClass().equals(TextField.class)) {
                        continue;
                    }
                    TextField textField = (TextField) hBoxChild;
                    System.out.println(textField.getId());
                    if(textField.getId().contains("xPos")) {
                        x = Integer.parseInt(textField.getText().trim());
                        System.out.println("x: " + x);
                    } else if (textField.getId().contains("yPos")) {
                        y = Integer.parseInt(textField.getText().trim());
                        System.out.println("y: " + y);
                    }
                }

                if(x > -1 && y > -1) {
                    Main.antList.add(new Ant(x, y, colorQueue.poll()));
                }
            }
        }

        if(Main.antList.size() < 1) {
            return false;
        } else return true;
    }

    @FXML
    public void onOkayClicked() {
        if(!loadRightBorderPaneData()) {
            displayAlert("Wrong data", "Right Pane");
            return;
        }

        if(!loadAntList()) {
            displayAlert("Wrong data", "Ant List");
            return;
        }

        Main.canvas = new Canvas(Main.planeSize*5, Main.planeSize*5);
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
        if(behaviourString == null) return false;
        if(behaviourString.length() < 2) return false;

        for(int i=0; i<behaviourString.length(); i++) {
            if(behaviourString.charAt(i) != 'R' && behaviourString.charAt(i) != 'L') {
                return false;
            }
        }
        return true;
    }

    private void displayAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
