package application.langtonsant.entity;

import application.langtonsant.Direction;

import java.io.Serializable;

/**
 * Klasa reprezentująca mrówke langtona poruszającą się po płaszczyźnie.
 */
public class Ant implements Serializable {
    private long serialVersionUID = 1L;

    /**
     * Ilość stworzonych instancji klasy.
     */
    private static int antNumber = 0;

    /**
     * Numer identyfikacji instancji.
     */
    private final int id;
    /**
     * Pozycja mrówki x na płaszczyźnie.
     */
    private int x;
    /**
     * Pozycja mrówki y na płaszczyźnie.
     */
    private int y;
    /**
     * Kierunek mrówki na płaszczyźnie.
     */
    private int direction;
    /**
     * Definiuje czy mrówka jest aktywna.
     */
    private boolean isActive;
    /**
     * Ciąg zachowań mrówki.
     */
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
     * Metoda zmienia współrzędne mrówki w zalezności od kierunku.
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
     * Sprawdza, czy mrówka zderzyła się ze ścianą.
     * @param planeSize wielkość boku płaszczyzny.
     * @return true jeśli mrówka zderzyła się ze ścianą płaszczyzny(wyszla poza zakres).
     */

    public boolean checkIfCrashed(int planeSize) {
        if (x > 0 && y > 0 &&
                x < planeSize - 1 && y < planeSize - 1)
            return false;
        else return true;
    }

    /**
     * Interpretuje wprowadzony ciąg liter reprezentujący zachnowanie mrówki,
     * na tablicę enumów Direction.
     * @param behaviourString ciąg z liter 'R' i 'L' opisujący zachowanie przy natrafieniu na konkretną
     * wartość płaszyzny.
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
     * Ustala początkowy kierunek mrówki według pierwszej wartości w tablicy behaviourArray.
     */
    public void setStartDirection() {
        if(behaviourArray[0].equals(Direction.LEFT))
            direction = 3;
        else if (behaviourArray[0].equals(Direction.RIGHT))
            direction = 1;
    }

    /**
     * Zwraca wartość x.
     * @return wartość właściwości x.
     */
    public int getX() {
        return x;
    }

    /**
     * Zwraca wartość y.
     * @return wartość właściwości y.
     */
    public int getY() {
        return y;
    }

    /**
     * Zwraca wartość direction.
     * @return wartość właściwości direction.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Ustala wartość direction.
     * @param direction kierunek mrówki.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Zwraca wartość właściwości isActive.
     * @return wartość logiczna - true, jeśli mrowka wciąż w grze.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Ustala wartość active.
     * @param active pożądana wartość active.
     */

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Zwraca wartość id.
     * @return wartość id.
     */
    public int getId() {
        return id;
    }

}
