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
        graphicsContext.strokeLine(0+5, 0+5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(0+5, 0+5, 0+5, canvas.getHeight()-5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, canvas.getWidth()-5, 0+5);
        graphicsContext.strokeLine(canvas.getHeight()-5, canvas.getWidth()-5, 0+5, canvas.getHeight()-5);
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
    public abstract void run();
}
