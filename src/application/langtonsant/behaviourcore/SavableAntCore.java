package application.langtonsant.behaviourcore;

import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

/**
 * Abstrakcyjna klasa jądra mrówki langtona. Posiada wymagane, chronione oraz publiczne właściwości i metody.
 *
 */
public abstract class SavableAntCore {

    /**
     * Rozmiar kwadratu mrówek na płótnie.
     */
    protected int antRectangleSize;
    /**
     * Płaszczyzna po której poruszają się mrówki.
     */
    protected Plane plane;
    /**
     * Kontekst graficzny płótna.
     */
    protected GraphicsContext graphicsContext;
    /**
     * Mapa kolorów mrówek na płótnie.
     */
    protected Map<Integer, SavableColor> colors;

    /**
     * Zapełnia całe płótno według wartości w instancji plane,
     * oraz rysuje ramkę na granicach dostępnej dla mrówki powierzchni.
     * Do użycia tylko przy potrzebie wypełnienia więcej niż jednej pozycji.
     * W przeciwnym wypadku użyć metody fillPlaneOnPosition(int, int).
     */
    protected void preFillPlane() {
        for(int x=0; x<plane.getPlaneSize(); x++) {
            for(int y=0; y<plane.getPlaneSize(); y++) {
                fillPlaneOnPosition(x, y);
            }
        }
        Canvas canvas = graphicsContext.getCanvas();
        antRectangleSize--;
        graphicsContext.strokeLine(antRectangleSize, antRectangleSize, canvas.getWidth()-antRectangleSize,
                antRectangleSize);
        graphicsContext.strokeLine(antRectangleSize, antRectangleSize, antRectangleSize,
                canvas.getHeight()-antRectangleSize);
        graphicsContext.strokeLine(canvas.getHeight()-antRectangleSize, canvas.getWidth()-antRectangleSize,
                canvas.getWidth()-antRectangleSize, antRectangleSize);
        graphicsContext.strokeLine(canvas.getHeight()-antRectangleSize, canvas.getWidth()-antRectangleSize,
                antRectangleSize, canvas.getHeight()-antRectangleSize);
        antRectangleSize++;
    }

    /**
     * Wypełnia kwadrat na pozycji na płótnie według schematu ustalonego w mapie kolorów.
     * @param x pozycja x na płótnie.
     * @param y pozycja y na płótnie.
     */
    protected void fillPlaneOnPosition(int x, int y) {
        int planeValueOnAntPosition = plane.getValueOnPosition(x, y);
        if(planeValueOnAntPosition == -1) {
            graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(colors.get(planeValueOnAntPosition).toColorClass());
        }
        graphicsContext.fillRect(x*antRectangleSize, y*antRectangleSize, antRectangleSize, antRectangleSize);
    }

    /**
     * Zwraca instancję plane.
     * @return instancja plane.
     */
    public Plane getPlane() {
        return this.plane;
    }

    /**
     * Zwraca mapę kolorów colors.
     * @return Mapa kolorów colors.
     */
    public Map<Integer, SavableColor> getColorList() {
        return this.colors;
    }

    /**
     * Zwraca listę mrówek poruszających się po płótnie.
     * @return lista mrówek.
     */
    public abstract List<Ant> getAntList();

    /**
     * Defniniuje jeden tick symulacji - krok dla jednej lub wielu mrówek.
     * @return true jeśli chociaż jedna mrówka w symulacji zmieniła pozycję,
     * w przeciwnym wypadku false.
     */
    public abstract boolean run();
}
