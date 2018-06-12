package application.langtonsant.behaviourcore;

import application.langtonsant.Direction;
import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Klasa stanowi obsługę dla mrówki wielokolorowej.
 */
public class CustomAntCore extends AbstractAntCore {

    /**
     * @param theOnlyAntThatMatters mrówka, którą klasa obsługuje.
     * @param behaviourLimit
     */
    private Ant theOnlyAntThatMatters;
    private int behaviourLimit;

    /**
     *
     * @param plane wskazuje na klasę przechowującą tablicę wartości.
     * @param theOnlyAntThatMatters obiekt mrówki kolorowej.
     * @param colorMap przechowuje kolory odpowiadające konkretnym mrówkom.
     * @param graphicsContext rysuje po planszy.
     * @param antRectangleSize obiekt do zamalowywania komórek planszy.
     */
    public CustomAntCore(Plane plane, Ant theOnlyAntThatMatters, Map<Integer, SavableColor> colorMap,
                         GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = colorMap;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
        this.plane = plane;
        this.theOnlyAntThatMatters = theOnlyAntThatMatters;

        behaviourLimit = theOnlyAntThatMatters.behaviourArray.length;
        preFillPlane();
    }

    /**
     * Metoda zmienia na mapie wartości miejsce na które mrówka weszła.
     * @param ant mrówka wielokolorowa.
     */
    private void markAsVisited(Ant ant){
        if(plane.getValueOnPosition(ant.getX(),ant.getY()) == -1)
            plane.setValueOnPosition(ant.getX(),ant.getY(),0);
    }

    /**
     *
     * Ustawia kierunek kolejnego kroku dla mrówki.
     * @param ant mrówka, której kierunek ustawiamy.
     */
    private void antSetDirection(Ant ant) {
         Direction nextDirection = ant.behaviourArray[plane.getValueOnPosition(ant.getX(), ant.getY())];
            if (nextDirection.equals(Direction.RIGHT)) // - w prawo
                ant.setDirection(ant.getDirection() + 1);
            else if (nextDirection.equals(Direction.LEFT)) // - w lewo
                ant.setDirection(ant.getDirection() + 3);  // += 3;
            ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;

    }


    /**
     * Metoda zaznacza na mapie wartości identyfikator mrówki dla współrzędnej odpowiadającej lokalizacji mrówki.
     * @param ant mrówka którą zaznacza się na mapie wartości.
     */

    private void antSetPlaneValue(Ant ant) {
        if(( plane.getValueOnPosition(ant.getX(), ant.getY()) +1 ) == behaviourLimit)
            plane.setValueOnPosition(ant.getX(), ant.getY(), 0);
        else
            plane.setValueOnPosition(ant.getX(), ant.getY(), (plane.getValueOnPosition(ant.getX(), ant.getY()) + 1));
    }

    /**
     * Wywołuje metody odpowiedzialne za jeden krok (cykl) mrówki
     * @param ant mrówka, dla której wykonujemy ruch.
     */
    private void antStep (Ant ant) {
        markAsVisited(ant);
        antSetPlaneValue(ant);

        fillPlaneOnPosition(ant.getX(), ant.getY());

        ant.antMove();
        markAsVisited(ant);
        antSetDirection(ant);
    }

    /**
     * Ustala akcję dla jednego wywołania metody run przez timeline.
     * @return true jeśli mrówka jest nadal aktywna.
     */
    @Override
    public boolean run() {
        theOnlyAntThatMatters.setActive(!theOnlyAntThatMatters.checkIfCrashed(plane.getPlaneSize()));
        if(theOnlyAntThatMatters.isActive()) {
            antStep(theOnlyAntThatMatters);
        }

        return theOnlyAntThatMatters.isActive();
    }

    /**
     * Pakuje mrówkę chodzącą po płaszczyźnie do listy i zwraca ją.
     * @return jednoelementowa lista mrówek.
     */
    @Override
    public List<Ant> getAntList() {
        List<Ant> antList = new LinkedList<>();
        antList.add(theOnlyAntThatMatters);
        return antList;
    }
}
