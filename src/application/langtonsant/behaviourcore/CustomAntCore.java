package application.langtonsant.behaviourcore;

import application.langtonsant.Direction;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomAntCore extends SavableAntCore {

    private Ant theOnlyAntThatMatters;
    private int behaviourLimit;

    public CustomAntCore(Plane plane, Ant theOnlyAntThatMatters, Map<Integer, SavableColor> colorMap,
                         GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = colorMap;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
        this.plane = plane;
        this.theOnlyAntThatMatters = theOnlyAntThatMatters;

        behaviourLimit = theOnlyAntThatMatters.behaviourArray.length;
        theOnlyAntThatMatters.setStartDirection();
        preFillPlane();
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

        fillPlaneOnPosition(ant.getX(), ant.getY());

        ant.antMove();
        markAsVisited(ant);
        antSetDirection(ant);
    }

    @Override
    public void run() {
        antStep(theOnlyAntThatMatters);
    }

    @Override
    public List<Ant> getAntList() {
        List<Ant> antList = new LinkedList<>();
        antList.add(theOnlyAntThatMatters);
        return antList;
    }
}
