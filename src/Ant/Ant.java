package Ant;

import javafx.scene.paint.Color;

public class Ant {
    private int x;
    private int y;
    private Color color = Color.BLUE;
    private int direction;
    private boolean isActive;
    private static int number;
    private int id;

    public Ant(){
        setId(++number);
    }
    public Ant(int x, int y){
        this.setX(x);
        this.setY(y);
        setId(++number);
        setActive(true);
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

    public void setId(int id) {
        this.id = id;
    }
    public int getNumber() {
        return number;
    }

}
