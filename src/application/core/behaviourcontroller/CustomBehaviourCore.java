package application.core.behaviourcontroller;

import application.Main;
import application.core.miscs.Plane;
import application.core.miscs.Ant;
import application.core.miscs.CoreMiscs;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CustomBehaviourCore implements Runnable{
    private Ant theOnlyAntThatMatters;
    private Plane plane;
    private int[] behaviourArray;
    private static final int LEFT = 69;
    private static final int RIGHT = 666;
    private int behaviourLimit;
    private Map<Integer, Color> colors;
    private int nextDirection;

    private GraphicsContext graphicsContext;
    private int antRectangleSize;

    private void markAsVisited(Ant ant, Plane plane){
        if(plane.cordinateValue(ant.getX(),ant.getY()) == -1)
            plane.setCordinatesValue(ant.getX(),ant.getY(),0);
    }

    private void antSetDirection(Ant ant, Plane plane) {
         nextDirection = behaviourArray[plane.cordinateValue(ant.getX(), ant.getY())];
            if (nextDirection == RIGHT) // - w prawo
                ant.setDirection(ant.getDirection() + 1);
            else    // - w lewo
                ant.setDirection(ant.getDirection() + 3);  // += 3;
            ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;

    }

    private void antSetPlaneValue(Ant ant, Plane plane) {
        if((( plane.cordinateValue(ant.getX(), ant.getY())) +1 ) == behaviourLimit)
            plane.setCordinatesValue(ant.getX(), ant.getY(), 0);
        else
            plane.setCordinatesValue(ant.getX(), ant.getY(), (plane.cordinateValue(ant.getX(), ant.getY()) + 1));
    }

    private void antStep (Ant ant, Plane plane) {
        markAsVisited(ant, plane);
        antSetPlaneValue(ant, plane);

        int planeValueOnAntPostition = plane.cordinateValue(ant.getX(), ant.getY());
        if(planeValueOnAntPostition == -1) {
            graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(colors.get(planeValueOnAntPostition));
        }
        graphicsContext.fillRect(ant.getX()*antRectangleSize, ant.getY()*antRectangleSize, antRectangleSize, antRectangleSize);

        CoreMiscs.antMove(ant);
        markAsVisited(ant, plane);
        antSetDirection(ant, plane);
    }

    public CustomBehaviourCore(int planeSize, String behaviourString, Ant theOnlyAntThatMatters,
                               GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = Main.colorMap;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
        this.plane = new Plane(planeSize);
        this.theOnlyAntThatMatters = theOnlyAntThatMatters;

        interpretBehaviourString(behaviourString);
        behaviourLimit = behaviourString.length();
        setAntStartDirection(theOnlyAntThatMatters);
    }

    @Override
    public void run() {
        antStep(theOnlyAntThatMatters, plane);
    }

    private void interpretBehaviourString (String behaviourString) {
        int behaviourStringLength = behaviourString.length();
        behaviourArray = new int[behaviourStringLength];

        for (int i=0; i<behaviourStringLength; i++) {
            if(behaviourString.charAt(i) == 'L')
                behaviourArray[i] = LEFT;
            else if((behaviourString.charAt(i)) == 'R')
                behaviourArray[i] = RIGHT;
        }
    }

    private void setAntStartDirection(Ant ant) {
        if(behaviourArray[0] == LEFT)
            ant.setDirection(3);
        else if (behaviourArray[0] == RIGHT)
            ant.setDirection(1);
    }
}
