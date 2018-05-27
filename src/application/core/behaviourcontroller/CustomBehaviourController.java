package application.core.behaviourcontroller;

import application.core.miscs.Plane;
import application.core.miscs.Ant;
import application.core.miscs.CoreMiscs;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CustomBehaviourController {
    private int[] behaviourArray;
    private static final int LEFT = 69;
    private static final int RIGHT = 666;
    private int behaviourLimit;
    private Map<Integer, Color> colors;

    private boolean finishTimer;
    private GraphicsContext graphicsContext;
    private int antRectangleSize;

    private void markAsVisited(Ant ant, Plane plane){
        if(plane.cordinateValue(ant.getX(),ant.getY()) == -1)
            plane.setCordinatesValue(ant.getX(),ant.getY(),0);
    }

    private void antSetDirection(Ant ant, Plane plane) {
        int nextDirection = behaviourArray[plane.cordinateValue(ant.getX(), ant.getY())];
    }

    private void antSetPlaneValue(Ant ant, Plane plane) {
        if(( plane.cordinateValue(ant.getX(), ant.getY()) +1 ) == behaviourLimit)
            plane.setCordinatesValue(ant.getX(), ant.getY(), 0);
        else
            plane.setCordinatesValue(ant.getX(), ant.getY(), (plane.cordinateValue(ant.getX(), ant.getY() + 1)));
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

    public CustomBehaviourController(int planeSize, int maxSteps, String behaviourString, int startX, int startY,
                                     GraphicsContext graphicsContext, int antRectangleSize, Map<Integer, Color> colors) {
        this.colors = colors;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;

        Plane plane = new Plane(planeSize);
        //plane.createPlane(planeSize);
        interpretBehaviourString(behaviourString);

        behaviourLimit = behaviourString.length();
        final Ant theOnlyAntThatMatters = new Ant();

        CoreMiscs.setAntPosition(theOnlyAntThatMatters, startX, startY);
        setAntStartDirection(theOnlyAntThatMatters);

        while(!CoreMiscs.isCrashed(theOnlyAntThatMatters, planeSize)) {
            System.out.println("Ant x: " + theOnlyAntThatMatters.getX() + " y: " + theOnlyAntThatMatters.getY());
            antStep(theOnlyAntThatMatters, plane);
        }

//        final Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if(finishTimer) {
//                    System.out.println("Canceling");
//                    timer.cancel();
//                }
//
//                System.out.println("Ant x: " + theOnlyAntThatMatters.getX() + " y: " + theOnlyAntThatMatters.getY());
//
//                antStep(theOnlyAntThatMatters, plane);
//
//            }
//        }, 1, 1);

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
