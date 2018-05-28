package application.core.behaviourcontroller;

import application.Main;
import application.core.entity.Ant;

import application.core.entity.Plane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.TimerTask;

public class CustomBehaviourCore extends TimerTask {

    private Ant theOnlyAntThatMatters;
    private Plane plane;
    private int behaviourLimit;
    private Map<Integer, Color> colors;

    private GraphicsContext graphicsContext;
    private int antRectangleSize;

    public CustomBehaviourCore(Plane plane, Ant theOnlyAntThatMatters,
                               GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = Main.colorMap;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
        this.plane = plane;
        this.theOnlyAntThatMatters = theOnlyAntThatMatters;

        behaviourLimit = theOnlyAntThatMatters.behaviourArray.length;
        theOnlyAntThatMatters.setStartDirection();
    }

    private void markAsVisited(Ant ant){
        if(plane.getValueOnPosition(ant.getX(),ant.getY()) == -1)
            plane.setValueOnPosition(ant.getX(),ant.getY(),0);
    }

    private void antSetDirection(Ant ant) {
         Direction nextDirection = ant.behaviourArray[plane.getValueOnPosition(ant.getX(), ant.getY())];
            if (nextDirection.equals(Direction.RIGHT)) // - w prawo
                ant.setDirection(ant.getDirection() + 1);
            else if (nextDirection.equals(Direction.LEFT)) // - w lewo
                ant.setDirection(ant.getDirection() + 3);  // += 3;
            ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;

    }

    private void antSetPlaneValue(Ant ant) {
        if(( plane.getValueOnPosition(ant.getX(), ant.getY()) +1 ) == behaviourLimit)
            plane.setValueOnPosition(ant.getX(), ant.getY(), 0);
        else
            plane.setValueOnPosition(ant.getX(), ant.getY(), (plane.getValueOnPosition(ant.getX(), ant.getY()) + 1));
    }

    private void antStep (Ant ant) {
        markAsVisited(ant);
        antSetPlaneValue(ant);

        fillAntPositionOnCanvas(ant);

        ant.antMove();
        markAsVisited(ant);
        antSetDirection(ant);
    }

    private void fillAntPositionOnCanvas(Ant ant) {
        int planeValueOnAntPosition = plane.getValueOnPosition(ant.getX(), ant.getY());
        if(planeValueOnAntPosition == -1) {
            graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(colors.get(planeValueOnAntPosition));
        }
        graphicsContext.fillRect(ant.getX()*antRectangleSize, ant.getY()*antRectangleSize, antRectangleSize, antRectangleSize);
    }

    @Override
    public void run() {
        antStep(theOnlyAntThatMatters);
    }
}
