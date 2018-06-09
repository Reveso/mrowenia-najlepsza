package application.langtonsant.entity;

import application.langtonsant.Controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ConfigurationSetup {
    private boolean isComplete = false;

    private int antSize;
    private int refreshDelay;
    private Long stepsLimit;

    private Long currentSteps;
    private List<Ant> antList;
    private Map<Integer, SavableColor> colorMap;
    private Controller controller;
    private Plane plane;


    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getRefreshDelay() {
        return refreshDelay;
    }

    public void setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay;
    }

    public Long getStepsLimit() {
        return stepsLimit;
    }

    public void setStepsLimit(Long stepsLimit) {
        this.stepsLimit = stepsLimit;
    }

    public Long getCurrentSteps() {
        return currentSteps;
    }

    public void setCurrentSteps(Long currentSteps) {
        this.currentSteps = currentSteps;
    }

    public List<Ant> getAntList() {
        return antList;
    }

    public void setAntList(List<Ant> antList) {
        this.antList = antList;
    }

    public Map<Integer, SavableColor> getColorMap() {
        return colorMap;
    }

    public void setColorMap(Map<Integer, SavableColor> colorMap) {
        this.colorMap = colorMap;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public int getAntSize() {
        return antSize;
    }

    public void setAntSize(int antSize) {
        this.antSize = antSize;
    }
}
