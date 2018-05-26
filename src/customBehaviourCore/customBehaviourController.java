package customBehaviourCore;

import coreMiscs.Plane;
import Ant.Ant;
import coreMiscs.coreMiscs;
public class customBehaviourController {
    public int[] behaviourArray;
    public static final int LEFT = 69;
    public static final int RIGHT = 666;
    private int behaviourLimit;

    public void markAsVisited(Ant ant, Plane plane){
        if(plane.cordinateValue(ant.getX(),ant.getY()) == -1)
            plane.setCordinatesValue(ant.getX(),ant.getY(),0);
    }

    public void antSetDirection(Ant ant, Plane plane) {
        int nextDirection = behaviourArray[plane.cordinateValue(ant.getX(), ant.getY())];
    }

    public void antSetPlaneValue(Ant ant, Plane plane) {
        if(( plane.cordinateValue(ant.getX(), ant.getY()) +1 ) == behaviourLimit)
            plane.setCordinatesValue(ant.getX(), ant.getY(), 0);
        else
            plane.setCordinatesValue(ant.getX(), ant.getY(), (plane.cordinateValue(ant.getX(), ant.getY() + 1)));
    }

    public void antStep (Ant ant, Plane plane) {
        markAsVisited(ant, plane);
        antSetPlaneValue(ant, plane);
        coreMiscs.antMove(ant);
        markAsVisited(ant, plane);
        antSetDirection(ant, plane);
        plane.printPlane();
    }

    public customBehaviourController (int planeSize, int maxSteps, String behaviourString, int startX, int startY) {
        Plane plane = new Plane(planeSize);
        //plane.createPlane(planeSize);
        interpretBehaviourString(behaviourString);

        behaviourLimit = behaviourString.length();
        Ant theOnlyAntThatMatters = new Ant();

        coreMiscs.setAntPosition(theOnlyAntThatMatters, startX, startY);
        setAntStartDirection(theOnlyAntThatMatters);
    }

    public void interpretBehaviourString (String behaviourString) {
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
