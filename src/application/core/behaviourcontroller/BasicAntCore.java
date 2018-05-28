package application.core.behaviourcontroller;

import java.util.*;

import application.core.miscs.Ant; //bedzie w coreMiscs
import application.core.miscs.CoreMiscs;
import application.core.miscs.Plane;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BasicAntCore implements Runnable {

    private List<Ant> antList;
    private GraphicsContext graphicsContext;
    private int antRectangleSize;
    private boolean finishTimer = false;
    private Plane plane;
    private int planeSize;

    private void multiAntSetDirection(Ant ant, Plane plane) {
        int nextDirection = plane.cordinateValue(ant.getX(), ant.getY());
        if (nextDirection == -1) //bialy - w prawo
            ant.setDirection(ant.getDirection() + 1);
        else    //czorny - w lewo
            ant.setDirection(ant.getDirection() + 3);  // += 3;
        ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;
    }

    void multiAntSetPlaneValue(Ant ant, Plane plane) {
        if (plane.cordinateValue(ant.getX(), ant.getY()) == -1) {
            plane.setCordinatesValue(ant.getX(), ant.getY(), ant.getId());
        } else {
            plane.setCordinatesValue(ant.getX(), ant.getY(), -1);
        }
    }

    void multiAntStep(Ant ant, Plane plane) {
        multiAntSetPlaneValue(ant, plane);

        CoreMiscs.antMove(ant);
        multiAntSetDirection(ant, plane);

        if(plane.cordinateValue(ant.getX(), ant.getY()) == -1)
            graphicsContext.setFill(Color.GRAY);
        else
            graphicsContext.setFill(ant.getColor());

            graphicsContext.fillRect(ant.getX()*antRectangleSize, ant.getY()*antRectangleSize, antRectangleSize, antRectangleSize);
    }


    boolean iterateThroughAntList(int planeSize, Plane plane) {
        boolean anyMove = false;

        for (Ant ant : antList) {
            if (CoreMiscs.isCrashed(ant, planeSize)) {
                System.out.println("CRASH " + ant.getId());
                ant.setActive(false);
            }
            if (ant.isActive()) {
                multiAntStep(ant, plane);
                anyMove = true;
                System.out.println("X: " + ant.getX() + " Y: " + ant.getY());
            }
        }
        return anyMove;
    }

    public BasicAntCore(int planeSize, Plane plane, List<Ant> antList,
                        GraphicsContext graphicsContext, int antRectangleSize) {
        this.planeSize = planeSize;
        this.plane = plane;
        this.antList = antList;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
    }

    @Override
    public void run() {
        iterateThroughAntList(planeSize, plane);
    }

    public void printAntsLocations(List<Ant> ants) {
        for (Ant ant : ants) {
            System.out.println("id: " + ant.getId() + "; x: " + ant.getX() + "; y: " + ant.getY());
        }
    }
}
