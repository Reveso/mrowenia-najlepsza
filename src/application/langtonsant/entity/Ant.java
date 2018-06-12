package application.langtonsant.entity;

import application.langtonsant.Direction;

import java.io.Serializable;

/**
 * Instancje tej klasy reprezentują kolejne mrówki.
 */
public class Ant implements Serializable {
    /**
     *
     */
    private long serialVersionUID = 1L;

    private static int antNumber;

    private final int id;
    private int x;
    private int y;
    private int direction;
    private boolean isActive;

    public Direction[] behaviourArray;

    /**
     * Zwraca obiekt mrówki o zadanych współrzędnych.
     * @param x współrzędna x.
     * @param y współrzędna y.
     */
    public Ant(int x, int y){
        this.id = antNumber++;
        this.x = x;
        this.y = y;
        this.isActive = true;
    }

    /**
     * Metoda zmienia współrzędne mrówki w zalezności od kierunku
     */

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

    /**
     * Sprawdza, czy mrówka nie zderzyła się ze ścianą.
     * @param planeSize wielkość tablicy
     * @return true jeśli mrówka się zderzyla (wyszla poza zakres).
     */

    public boolean checkIfCrashed(int planeSize) {
        if (x > 0 && y > 0 &&
                x < planeSize - 1 && y < planeSize - 1)
            return false;
        else return true;
    }

    /**
     * Interpretuje wprowadzony ciąg liter reprezentujący zachnowanie mrówki.
     * @param behaviourString ciąg z liter 'R' i 'L' opisujący zachowanie przy natrafieniu na konkretny kolor.
     */
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

    /**
     * Ustala początkowy kierunek mrówki.
     */

    public void setStartDirection() {
        if(behaviourArray[0].equals(Direction.LEFT))
            direction = 3;
        else if (behaviourArray[0].equals(Direction.RIGHT))
            direction = 1;
    }

    /**
     *
     * @return zwraca współrzędną x.
     */

    public int getX() {
        return x;
    }

    /**
     *
     * @return zwraca współrzędną y.
     */

    public int getY() {
        return y;
    }


    /**
     *
     * @return kierunek.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Ustala kierunek.
     * @param direction kierunek mrówki.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     *
     * @return wartość logiczna - true, jeśli mrowka wciąż w grze.
     */

    public boolean isActive() {
        return isActive;
    }

    /**
     * Ustala active.
     * @param active wartość logiczna - true, jeśli mrowka wciąż w grze.
     */

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     *
     * @return identyfikator mrówki.
     */

    public int getId() {
        return id;
    }

}
