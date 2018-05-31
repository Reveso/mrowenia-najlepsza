package application.core.entity;

import application.core.behaviourcontroller.Direction;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Ant implements Serializable {
    private long serialVersionUID = 1L;

    private static int antNumber;

    private final int id;
    private int x;
    private int y;
    private SavableColor color;
    private int direction;
    private boolean isActive;

    public Direction[] behaviourArray;

    public Ant(int x, int y){
        this.id = antNumber++;
        this.x = x;
        this.y = y;
        this.isActive = true;
    }

    public Ant(int x, int y, SavableColor color){
        this.id = antNumber++;
        this.x = x;
        this.y = y;
        this.isActive = true;
        this.color = color;
    }

    public void antMove() {
        switch (direction) {
            case 0:
                y-=1;
                break;
            case 1:
                x+=1;
                break;
            case 2:
                y+=1;
                break;
            case 3:
                x-=1;
                break;
            default:
                System.err.println("Wrong direction");
        }
    }

    public boolean checkIfCrashed(int planeSize) {
        if (x > 0 && y > 0 &&
                x < planeSize - 1 && y < planeSize - 1)
            return false;
        else return true;
    }

    public void interpretBehaviourString (String behaviourString) {
        int behaviourStringLength = behaviourString.length();
        behaviourArray = new Direction[behaviourStringLength];

        for (int i=0; i<behaviourStringLength; i++) {
            if(behaviourString.charAt(i) == 'L')
                behaviourArray[i] = Direction.LEFT;
            else if((behaviourString.charAt(i)) == 'R')
                behaviourArray[i] = Direction.RIGHT;
        }
    }

    public void setStartDirection() {
        if(behaviourArray[0].equals(Direction.LEFT))
            direction = 3;
        else if (behaviourArray[0].equals(Direction.RIGHT))
            direction = 1;
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

    public SavableColor getColor() {
        return color;
    }

    public void setColor(SavableColor color) {
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
