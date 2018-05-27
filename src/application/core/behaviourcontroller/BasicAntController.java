package application.core.behaviourcontroller;

import java.util.*;

import application.core.miscs.Ant; //bedzie w coreMiscs
import application.core.miscs.CoreMiscs;
import application.core.miscs.Plane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BasicAntController {

    private List<Ant> antList;
    private GraphicsContext graphicsContext;
    private int antRectangleSize;
    private boolean finishTimer = false;

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
            graphicsContext.setFill(Color.WHITE);
        else
            graphicsContext.setFill(ant.getColor());

            graphicsContext.fillRect(ant.getX()*antRectangleSize, ant.getY()*antRectangleSize, antRectangleSize, antRectangleSize);
    }


    boolean iterateThroughAntList(int planeSize, Plane plane) {
        boolean anyMove = false;

        for (Ant ant : antList) {
            if (CoreMiscs.isCrashed(ant, planeSize)) {
                ant.setActive(false);
            }
            if (ant.isActive()) {
                multiAntStep(ant, plane);
                anyMove = true;
            }
        }
        return anyMove;
    }

    private void setStartingLocations() {
        antList.add(new Ant(40, 40));
    }

    public BasicAntController(final int planeSize, int maxSteps, int antCount, final Plane plane, GraphicsContext graphicsContext,
                              int antRectangleSize) {
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;


        antList = new LinkedList<>();

        for (int i = 0; i < antCount; i++)
            setStartingLocations();

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(finishTimer) {
                    System.out.println("Canceling");
                    timer.cancel();

                }
                iterateThroughAntList(planeSize, plane);
            }
        }, 1, 1);

    }

    public void printAntsLocations(List<Ant> ants) {
        for (Ant ant : ants) {
            System.out.println("id: " + ant.getId() + "; x: " + ant.getX() + "; y: " + ant.getY());
        }
    }
}
