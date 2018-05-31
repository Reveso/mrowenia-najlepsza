package application.langtonsant.behaviourcore;

import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Map;

public class BasicAntCore extends SavableAntCore {

    private List<Ant> antList;

    public BasicAntCore(Plane plane, List<Ant> antList, Map<Integer, SavableColor> colorMap,
                        GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = colorMap;
        this.plane = plane;
        this.antList = antList;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;

       for(Ant ant : antList) {
           ant.setStartDirection();
       }
       preFillPlane();
    }

    private void antSetDirection(Ant ant) {
        int nextDirection = plane.getValueOnPosition(ant.getX(), ant.getY());
        if (nextDirection == -1) //bialy - w prawo
            ant.setDirection(ant.getDirection() + 1);
        else    //czorny - w lewo
            ant.setDirection(ant.getDirection() + 3);  // += 3;
        ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;
    }

    private void antSetPlaneValue(Ant ant) {
        if (plane.getValueOnPosition(ant.getX(), ant.getY()) == -1) {
            plane.setValueOnPosition(ant.getX(), ant.getY(), ant.getId());
        } else {
            plane.setValueOnPosition(ant.getX(), ant.getY(), -1);
        }
    }

    private void antStep(Ant ant) {
        antSetPlaneValue(ant);

        fillPlaneOnPosition(ant.getX(), ant.getY());

        ant.antMove();
        antSetDirection(ant);
    }

    private boolean iterateThroughAntList() {
        boolean anyMove = false;

        for (Ant ant : antList) {
            if (ant.checkIfCrashed(plane.getPlaneSize())) {
                ant.setActive(false);
            }
            if (ant.isActive()) {
                antStep(ant);
                anyMove = true;
            }
        }
        return anyMove;
    }

    @Override
    public void run() {
        iterateThroughAntList();
    }

    @Override
    public List<Ant> getAntList() {
        return antList;
    }
}
