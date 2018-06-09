package application.langtonsant.behaviourcore;

import application.langtonsant.entity.Ant;
import application.langtonsant.entity.Plane;
import application.langtonsant.entity.SavableColor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public abstract class SavableAntCore {

    protected int antRectangleSize;
    protected Plane plane;
    protected GraphicsContext graphicsContext;
    protected Map<Integer, SavableColor> colors;

    protected void preFillPlane() {
        for(int x=0; x<plane.getPlaneSize(); x++) {
            for(int y=0; y<plane.getPlaneSize(); y++) {
                fillPlaneOnPosition(x, y);
            }
        }
        Canvas canvas = graphicsContext.getCanvas();
        graphicsContext.strokeLine(antRectangleSize, antRectangleSize, canvas.getWidth()-antRectangleSize,
                antRectangleSize);
        graphicsContext.strokeLine(antRectangleSize, antRectangleSize, antRectangleSize,
                canvas.getHeight()-antRectangleSize);
        graphicsContext.strokeLine(canvas.getHeight()-antRectangleSize, canvas.getWidth()-antRectangleSize,
                canvas.getWidth()-antRectangleSize, antRectangleSize);
        graphicsContext.strokeLine(canvas.getHeight()-antRectangleSize, canvas.getWidth()-antRectangleSize,
                antRectangleSize, canvas.getHeight()-antRectangleSize);
    }

    protected void fillPlaneOnPosition(int x, int y) {
        int planeValueOnAntPosition = plane.getValueOnPosition(x, y);
        if(planeValueOnAntPosition == -1) {
            graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(colors.get(planeValueOnAntPosition).toColorClass());
        }
        graphicsContext.fillRect(x*antRectangleSize, y*antRectangleSize, antRectangleSize, antRectangleSize);
    }

    public Plane getPlane() {
        return this.plane;
    }

    public Map<Integer, SavableColor> getColorList() {
        return this.colors;
    }

    public abstract List<Ant> getAntList();
    public abstract boolean run();
}
