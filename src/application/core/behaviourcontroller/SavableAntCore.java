package application.core.behaviourcontroller;

import application.core.entity.Ant;
import application.core.entity.Plane;
import application.core.entity.SavableColor;

import java.util.List;
import java.util.Map;

public abstract class SavableAntCore {

    public abstract Plane getPlane();
    public abstract List<Ant> getAntList();
    public abstract Map<Integer, SavableColor> getColorList();
    public abstract void run();
}
