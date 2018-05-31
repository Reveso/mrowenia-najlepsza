package application.core.behaviourcontroller;

import java.util.*;

import application.core.entity.Ant;
import application.core.entity.Plane;
import application.core.entity.SavableColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BasicAntCore extends SavableAntCore {

    private List<Ant> antList;
    private GraphicsContext graphicsContext;
    private int antRectangleSize;
    private Plane plane;

    public BasicAntCore(Plane plane, List<Ant> antList,
                        GraphicsContext graphicsContext, int antRectangleSize) {
        this.plane = plane;
        this.antList = antList;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;

       for(Ant ant : antList) {
           ant.setStartDirection();
       }
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

        fillAntPositionOnCanvas(ant);

        ant.antMove();
        antSetDirection(ant);
    }

    private void fillAntPositionOnCanvas(Ant ant) {
        if(plane.getValueOnPosition(ant.getX(), ant.getY()) == -1)
            graphicsContext.setFill(Color.WHITE);
        else
            graphicsContext.setFill(ant.getColor().toColorClass());

        graphicsContext.fillRect(ant.getX()*antRectangleSize, ant.getY()*antRectangleSize, antRectangleSize, antRectangleSize);
    }

    private boolean iterateThroughAntList() {
        boolean anyMove = false;

        for (Ant ant : antList) {
            if (ant.checkIfCrashed(plane.getPlaneSize())) {
//                System.out.println("CRASH " + ant.getId());
                ant.setActive(false);
            }
            if (ant.isActive()) {
                antStep(ant);
                anyMove = true;
//                System.out.println("X: " + ant.getX() + " Y: " + ant.getY());
            }
        }
        return anyMove;
    }

    @Override
    public void run() {
        iterateThroughAntList();
    }

    @Override
    public Plane getPlane() {
        return plane;
    }

    @Override
    public List<Ant> getAntList() {
        return antList;
    }

    @Override
    public Map<Integer, SavableColor> getColorList() {
        return new HashMap<>();
    }
}
