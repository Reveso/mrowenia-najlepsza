package application.langtonsant.behaviourcore;

import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Map;

/**
 * Klasa stanowi obsługę dla wielu mrówek.
 */

public class BasicAntCore extends SavableAntCore {

    /**
     * @param antList przechowuje mrówki.
     */
    private List<Ant> antList;

    /**
     *
     * @param plane wskazuje na klasę przechowującą tablicę wartości.
     * @param antList kontener przechowujący mrówki.
     * @param colorMap przechowuje kolory odpowiadające konkretnym mrówkom.
     * @param graphicsContext rysuje po planszy.
     * @param antRectangleSize obiekt do zamalowywania komórek planszy.
     */

    public BasicAntCore(Plane plane, List<Ant> antList, Map<Integer, SavableColor> colorMap,
                        GraphicsContext graphicsContext, int antRectangleSize) {
        this.colors = colorMap;
        this.plane = plane;
        this.antList = antList;
        this.graphicsContext = graphicsContext;
        this.antRectangleSize = antRectangleSize;
        preFillPlane();
    }

    /**
     *
     * ustawia kierunek kolejnego kroku dla mrówki.
     * @param ant mrówka, której kierunek ustawiamy.
     */

    private void antSetDirection(Ant ant) {
        int nextDirection = plane.getValueOnPosition(ant.getX(), ant.getY());
        if (nextDirection == -1) //bialy - w prawo
            ant.setDirection(ant.getDirection() + 1);
        else    //czorny - w lewo
            ant.setDirection(ant.getDirection() + 3);  // += 3;
        ant.setDirection(ant.getDirection() % 4);     //kierunek %= 4;
    }

    /**
     * metoda zaznacza na mapie wartości identyfikator mrówki dla współrzędnej odpowiadającej lokalizacji mrówki.
     * @param ant mrówka którą zaznacza się na mapie wartości.
     */
    private void antSetPlaneValue(Ant ant) {
        if (plane.getValueOnPosition(ant.getX(), ant.getY()) == -1) {
            plane.setValueOnPosition(ant.getX(), ant.getY(), ant.getId());
        } else {
            plane.setValueOnPosition(ant.getX(), ant.getY(), -1);
        }
    }

    /**
     * wywołuje metody odpowiedzialne za jeden krok (cykl) mrówki
     * @param ant mrówka, dla której wykonujemy ruch.
     */

    private void antStep(Ant ant) {
        antSetPlaneValue(ant);

        fillPlaneOnPosition(ant.getX(), ant.getY());

        ant.antMove();
        antSetDirection(ant);
    }

    /**
     * metoda wykonuje ruch mrówki (antStep) dla każdej żywej mrówki.
     * @return zwraca wartość logiczną, która przyjmuje true, kiedy nie ma żywych mrówek.
     */

    private boolean iterateThroughAntList() {
        boolean anyMove = false;

        for (Ant ant : antList) {
            if (ant.checkIfCrashed(plane.getPlaneSize())) {
                ant.setActive(false);
            }
            if (ant.isActive()) {
                antStep(ant);
                anyMove = true;
            }
        }
        return anyMove;
    }

    @Override
    public boolean run() {
        return iterateThroughAntList();
    }

    @Override
    public List<Ant> getAntList() {
        return antList;
    }
}
