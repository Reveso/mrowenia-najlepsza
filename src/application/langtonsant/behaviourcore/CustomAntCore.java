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
     * Mrówka poruszająca się po płaszczyźnie.
     */
    private Ant theOnlyAntThatMatters;
    /**
     * Długość ciągu zachowań mrówki.
     */
    private int behaviourLimit;

    /**
     * Konstruktor podane wartości i wypełniający początkowo planszę metodą preFillPlane().
     * Dodatkowo ustala wartość behaviourLimit według długości behaviourArray.
     * @param plane wskazuje na instancję płaszczyzny.
     * @param theOnlyAntThatMatters jedyna mrówka, która będzie poruszać się na płaszczyźnie.
     * @param colorMap przechowuje kolory odpowiadające konkretnym mrówkom.
     * @param graphicsContext kontekst graficzny płótna.
     * @param antRectangleSize wielkość kwadratu reprezentującego mrówkę na płótnie.
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
     * Metoda zmienia na wartość na płaszczyźnie jeśli jest ona równa -1 ->
     * oznacza że mrówka chociaż raz była na tym polu.
     * @param ant mrówka będąca na polu.
     */
    private void markAsVisited(Ant ant){
        if(plane.getValueOnPosition(ant.getX(),ant.getY()) == -1)
            plane.setValueOnPosition(ant.getX(),ant.getY(),0);
    }

    /**
     * Ustawia kierunek mrówki według obecnego kierunku mrówki oraz wartości płaszczyzny na pozycji mrówki.
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
     * Metoda zaznacza na płaszczyźnie wartość mrówki według jej tablicy zachowań.
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
