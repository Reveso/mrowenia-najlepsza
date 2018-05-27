package application.core.miscs;

import javafx.scene.paint.Color;

public class Ant {
    private static int antNumber;

    private final int id;
    private int x;
    private int y;
    private Color color;
    private int direction;
    private boolean isActive;

    public Ant(int x, int y){
        this.id = antNumber++;
        this.x = x;
        this.y = y;
        this.isActive = true;
    }
    public Ant(int x, int y, Color color){
        this.id = antNumber++;
        this.x = x;
        this.y = y;
        this.isActive = true;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }


}
