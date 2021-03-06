package application.gui.config;

import application.gui.animation.AnimationController;
import application.langtonsant.CoreType;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import application.langtonsant.entity.ConfigurationSetup;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
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

    /**
     * Ilość pól tekstowych mrówek.
     */
    private int antCount;
    /**
     * Mapa pól tekstowych z pozycjami mrówek.
     * Key - pozycja x, wartość - pozycja y.
     */
    private Map<TextField, TextField> antTextFieldsMap;
    /**
     * Kolejka dostępnych kolorów.
     */
    private Queue<SavableColor> colorQueue;

    /**
     * Ciąg zachowań mrówki/mrówek.
     */
    private String behaviourString;
    /**
     * Pozycja odczytu z pliku.
     */
    private File locationsLoadFile;

    /**
     * Konfiguracja animacji.
     */
    private ConfigurationSetup configurationSetup;


    /**
     * Initializuje okienko.
     */
    @FXML
    private void initialize() {
        configurationSetup = new ConfigurationSetup();
        antCount = 0;
        setupColorCollections();

        behaviourStringTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("[RLrl]+$")) {
                behaviourStringTextField.setText(newValue.replaceAll("[^RLrl]", ""));
            } else {
                behaviourStringTextField.setText(newValue.toUpperCase());
            }
        }));

        delayTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, delayTextField));
        planeSizeTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, planeSizeTextField));
        stepsLimitTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, stepsLimitTextField));
        antSizeTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, antSizeTextField));

    }

    /**
     * Wypełnia kolejkę kolorów.
     */
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

    /**
     * Miesza kolejkę kolorów.
     */
    private void shuffleQueue() {
        List<SavableColor> colorList = new ArrayList<>(colorQueue);
        Collections.shuffle(colorList);
        colorQueue = new LinkedBlockingQueue<>();
        colorQueue.addAll(colorList);

        configurationSetup.setColorMap(new LinkedHashMap<>());
        int i = 0;
        for (SavableColor color : colorList) {
            configurationSetup.getColorMap().put(i, color);
            i++;
        }
    }

    /**
     * Dodaje podpis i pola współrzędnych x, y dla kolejnej mrówki, oraz
     * zapisuje pola do mapy antTextFieldsMap.
     */
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

        xPosTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, xPosTextField));

        TextField yPosTextField = new TextField();
        yPosTextField.setPromptText("y");
        yPosTextField.setPrefWidth(40);
        yPosTextField.setPrefHeight(25);

        yPosTextField.textProperty().addListener((observable, oldValue, newValue) ->
                numericTextFieldChanged(observable, oldValue, newValue, yPosTextField));

        antTextFieldsMap.put(xPosTextField, yPosTextField);

        startingPositionsTextFieldsHBox.getChildren().addAll(label, xPosTextField, yPosTextField);

        gridPaneOne.add(startingPositionsTextFieldsHBox, 0, antCount);

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        if (stage.getHeight() > (screenSize.getHeight() / 2)) {
            stage.setHeight(screenSize.getHeight() / 2);
        }
        antCount++;
    }

    /**
     * Ustawia dalszą konfigurację dla ciągu innego niż RL
     */
    private void setupCustomBehaviourConfig() {
        Label startingPositionsLabel = new Label("Ant's starting position");
        BorderPane.setAlignment(startingPositionsLabel, Pos.CENTER);
        borderPane.setLeft(startingPositionsLabel);

        resetBorderPaneRightChildren(false);
        addAntPosFields();
    }

    /**
     * Ustawia dalszą konfigurację dla ciągu RL
     */
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

    /**
     * Resetuje dzieci z prawej strony instancji BorderPane do stanu początkowego.
     * @param textFieldDisabled true żeby wyłączyć pole tekstowe z rozmiarem płaszczyzny.
     */
    private void resetBorderPaneRightChildren(boolean textFieldDisabled) {
        stepsLimitTextField.setText("0");
        delayTextField.setText("1");
        planeSizeTextField.setText("100");
        planeSizeTextField.setDisable(textFieldDisabled);
        antSizeTextField.setText("5");
    }

    /**
     * Handler akcji onMouseClicked dla przycisku behaviourStringButton.
     * Ustawia okno dla dalszej konfiguracji w tym przypadku.
     */
    @FXML
    private void onBehaviourStringButtonMouseClicked() {
        locationsLoadFile = null;
        configurationSetup.setComplete(false);

        String tempBehaviourString = behaviourStringTextField.getText().trim();
        if (!checkBehaviourString(tempBehaviourString)) {
            System.out.println(behaviourStringTextField.getText());
            displayAlert("Wrong Behaviour String",
                    "Behaviour String can only contain letters R and L");
            return;
        }

        if (tempBehaviourString.length() > colorQueue.size()) {
            displayAlert("Too long string", "Max length is: " + colorQueue.size());
            return;
        }

        gridPaneOne.getChildren().clear();
        antCount = 0;
        antTextFieldsMap = new LinkedHashMap<>();
        configurationSetup.setCurrentSteps(0L);

        if (tempBehaviourString.equals("RL")) {
            configurationSetup.setCoreType(CoreType.BASIC);
            setupBasicBehaviourConfig();
        } else {
            configurationSetup.setCoreType(CoreType.CUSTOM);
            setupCustomBehaviourConfig();
        }

        behaviourString = tempBehaviourString;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.sizeToScene();

        okayButton.setDisable(false);
        resetButton.setDisable(false);
    }

    /**
     * Handler akcji onMouseClicked dla przycisku loadPositionFromFile.
     * Ustawia okno dla dalszej konfiguracji w tym przypadku.
     */
    @FXML
    private void onLoadPositionFromFileButtonMouseClicked() {
        locationsLoadFile = null;
        configurationSetup.setComplete(false);

        gridPaneOne.getChildren().clear();
        borderPane.setLeft(null);
        antCount = 0;
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

    /**
     * Handler akcji onKeyPressed dla przycisku loadPositionFromFile.
     * Dla entera uruchamia metodę onLoadPositionFromFileButtonMouseClicked().
     * @param keyEvent
     */
    @FXML
    private void onLoadPositionFromFileButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onLoadPositionFromFileButtonMouseClicked();
    }

    /**
     * Handler akcji onKeyPressed dla przycisku behaviourStringButton.
     * Dla entera uruchamia metodę onBehaviourStringButtonMouseClicked().
     * @param keyEvent
     */
    @FXML
    private void onBehaviourStringButtonKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    /**
     * Handler akcji onKeyPressed dla pola tekstowego behaviourStringTextField.
     * Dla entera uruchamia metodę onBehaviourStringTextFieldKeyPressed().
     * @param keyEvent
     */
    @FXML
    private void onBehaviourStringTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onBehaviourStringButtonMouseClicked();
    }

    /**
     * Handler akcji onKeyPressed dla przycisku Okay.
     * Dla entera uruchamia metodę onOkayClicked().
     * @param keyEvent
     */
    @FXML
    private void onOkayKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            onOkayClicked();
    }

    /**
     * Laduje dane z prawej strony BorderPane.
     * @return false jeśli dane byly niepoprawne.
     */
    private boolean loadRightBorderPaneData() {
        try {
            int refreshDelay = Integer.parseInt(delayTextField.getText().trim());
            if (refreshDelay < 1) {
                displayAlert("Wrong data", "Refresh delay must be positive");
                return false;
            }
            configurationSetup.setRefreshDelay(refreshDelay);

            int planeSize = Integer.parseInt(planeSizeTextField.getText().trim());
            if (planeSize < 1) {
                displayAlert("Wrong data", "Plane size must be positive");
                return false;
            } else {
                configurationSetup.setPlane(new Plane(planeSize));
            }

            Long stepsLimit = Long.parseLong(stepsLimitTextField.getText().trim());
            if (stepsLimit < 1) {
                configurationSetup.setStepsLimit(-1L);
            }
            configurationSetup.setStepsLimit(stepsLimit);

            int antSize = Integer.parseInt(antSizeTextField.getText().trim());
            if (antSize < 1) {
                displayAlert("Wrong data", "Ant size must be positive");
                return false;
            }
            configurationSetup.setAntSize(antSize);

        } catch (NumberFormatException e) {
            System.err.println(e);
            displayAlert("Wrong data", "Only numbers allowed in fields");
            return false;
        }
        return true;
    }

    /**
     * Laduje liste mrowek z centrum instancji BorderPane.
     * @return true jesli wspolrzedne przynajmniej jednej mrówki zostaly wczytane prawidlowo.
     */
    private boolean loadAntList() {
        configurationSetup.setAntList(new LinkedList<>());
        int planeSize = configurationSetup.getPlane().getPlaneSize();

        for (TextField xTextField : antTextFieldsMap.keySet()) {
            int x, y;
            TextField yTextField = antTextFieldsMap.get(xTextField);

            try {
                x = Integer.parseInt(xTextField.getText().trim());
                y = Integer.parseInt(yTextField.getText().trim());
            } catch (NumberFormatException e) {
                x = -1;
                y = -1;
                System.err.println(getClass() + "\n\t:" + e);
            }

            if (x > 0 && y > 0 && x < planeSize && y < planeSize) {
                Ant newAnt = new Ant(x, y);
                newAnt.interpretBehaviourString(behaviourString);
                newAnt.setStartDirection();
                configurationSetup.getAntList().add(newAnt);
            }
        }

        if (configurationSetup.getAntList().size() < 1) {
            return false;
        } else return true;

    }

    /**
     * Handler akcji onClick dla przycisku Okay.
     * Laduje ustawienia i uruchamia okno animacji metodą setupAnimationWindow().
     */
    @FXML
    private void onOkayClicked() {
        if (locationsLoadFile != null) {
            loadSavedAntCore(locationsLoadFile);
            return;
        }

        if (!loadRightBorderPaneData()) {
            displayAlert("Wrong data", "Right Pane");
            return;
        }

        if (!loadAntList()) {
            displayAlert("Wrong data", "Incorrect ants locations");
            return;
        }

        configurationSetup.setComplete(true);
        try {
            setupAnimationWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler akcji onClick dla przycisku Reset.
     * Czyści okno.
     */
    @FXML
    private void onResetClicked() {
        clearWindow();
    }

    /**
     * Handler akcji onClick dla przycisku Exit.
     */
    @FXML
    private void onExitClicked() {
        onStageCloseRequest();
    }

    /**
     * Sprawdza czy ciąg składa się tylko z liter R oraz L.
     * @param behaviourString ciąg zachowania mrówki/mrówek.
     * @return true jeśli ciąg poprawny.
     */
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

    /**
     * Laduje konfigurację z podanego pliku.
     * @param file zapisany plik z konfiguracją.
     */
    private void loadSavedAntCore(File file) {
        CoreType coreType;
        Plane plane;
        List<Ant> antList;
        Map<Integer, SavableColor> colors;
        Long currentSteps;

        try (ObjectInput locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            coreType = (CoreType) locFile.readObject();
            plane = (Plane) locFile.readObject();
            antList = (List<Ant>) locFile.readObject();
            colors = (Map<Integer, SavableColor>) locFile.readObject();
            currentSteps = (Long) locFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            displayAlert("Incorrect File", "Couldn't load a file");
            return;
        }

        if (!loadRightBorderPaneData()) {
            displayAlert("Wrong data", "Right Pane");
            return;
        }

        configurationSetup.setCoreType(coreType);
        configurationSetup.setPlane(plane);
        configurationSetup.setAntList(antList);
        configurationSetup.setColorMap(colors);
        configurationSetup.setCurrentSteps(currentSteps);
        configurationSetup.setStepsLimit(configurationSetup.getStepsLimit() + currentSteps);

        configurationSetup.setComplete(true);
        try {
            setupAnimationWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chowa obecne okno i tworzy scenę animacji z pliku /application/gui/animation/animationGUI.fxml,
     * w którym odtwarzana będzie symulacja stworzona według ustawień z instancji configurationSetup.
     * Jeśli stworzone okno ma wielkość większą niż 3/4 ekranu użytkownika, maksymalizuje je.
     * Po zamknięciu animacji, wywołuje void clearWindow()
     * @throws IOException
     */
    private void setupAnimationWindow() throws IOException {
        if (configurationSetup == null)
            return;
        if (!configurationSetup.isComplete())
            return;

        Stage animationStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass()
                .getResource("/application/gui/animation/animationGUI.fxml"));
        Parent animationRoot = fxmlLoader.load();

        AnimationController animationController = fxmlLoader.getController();
        animationController.setup(configurationSetup);
        animationStage.setTitle("Langton's Ant");

        int minimalSize = (configurationSetup.getPlane().getPlaneSize() * 5) + 100;
        int sceneSize = (configurationSetup.getPlane().getPlaneSize() * configurationSetup.getAntSize()) + 100;

        if(sceneSize < minimalSize)
            sceneSize = minimalSize;

        animationStage.setScene(new Scene(animationRoot, sceneSize, sceneSize));
        animationStage.setOnCloseRequest(event -> onExitClicked());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double availableHeight = screenBounds.getHeight() * 3 / 4;
        if (availableHeight < sceneSize) {
            animationStage.setMaximized(false);
            animationStage.setMaximized(true);
            animationStage.toFront();
        }

        Stage stage = (Stage) gridPaneOne.getScene().getWindow();
        stage.close();
        animationStage.showAndWait();
        clearWindow();
        stage.show();
    }

    /**
     * Czyści okno, ustawia domyślne wartości.
     */
    private void clearWindow() {
        configurationSetup = new ConfigurationSetup();
        configurationSetup.setCurrentSteps(0L);

        antCount = 0;
        locationsLoadFile = null;
        gridPaneOne.getChildren().clear();
        antTextFieldsMap = new LinkedHashMap<>();

        okayButton.setDisable(true);
        resetButton.setDisable(true);

        resetBorderPaneRightChildren(false);
        borderPane.setLeft(null);

        shuffleQueue();
    }


    /**
     * Wyświetla alert ostrzeżenia.
     * @param title tytuł ostrzeżenia.
     * @param header nagłówek ostrzeżenia.
     */
    private void displayAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * Nie pozwala na wpisanie wartości innej niż cyfra do podanej instancji TextField.
     * @param observable
     * @param oldValue
     * @param newValue
     * @param numericTextField instancja obiektu w którym blokuje się inne wartości niż numeryczne.
     */
    private void numericTextFieldChanged(ObservableValue<? extends String> observable,
                                         String oldValue, String newValue, TextField numericTextField) {
        if (!newValue.matches("\\d*")) {
            numericTextField.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    /**
     * Kończy działanie aplikacji.
     */
    private void onStageCloseRequest() {
        Platform.exit();
        System.exit(0);
    }
}
