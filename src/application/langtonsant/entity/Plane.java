package application.langtonsant.entity;

import java.io.Serializable;

/**
 * Instancja tej klasy reprezentuje płaszczyznę wartości, czyli tablicę dwuwymiarową na której wartości reprezentują
 * konkretny kolor.
 */

public class Plane implements Serializable {
    long serialVersionUID = 1L;

    /**
     * Macierz reprezentująca płaszczyznę.
     */
    private int[][] plane;
    /**
     * Wielkość boku płaszczyzny.
     */
    private int planeSize;

    /**
     * Tworzy płaszczyznę o podanej wielkości.
     * @param size wielkość boku kwadratowej płaszczyzny.
     */
    public Plane(int size) {
        this.planeSize = size;
        this.plane = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                plane[i][j] = -1;
        }
    }

    /**
     * Wypisuje płaszczyznę konsolę.
     * Do debugu.
     */
    public void printPlane() {
        for (int y = 0; y < planeSize; y++) {
            for (int x = 0; x < planeSize; x++) {
                System.out.print(plane[x][y]);
            }
            System.out.println();
        }
    }

    /**
     * Zwraca wartość płaszczyzny na podanych współrzędnych.
     * @param x współrzędna x.
     * @param y współrzędna x.
     * @return wartość płaszczyzny na x, y.
     */
    public int getValueOnPosition(int x, int y) {
        return plane[x][y];
    }

    /**
     * Ustawia wartosc konkretnej komórki mapy wartości.
     * @param x współrzędna x.
     * @param y współrzędna y.
     * @param value pożądana wartość komróki.
     */

    public void setValueOnPosition(int x, int y, int value) {
        plane[x][y] = value;
    }

    /**
     * Zwraca wartość planeSize.
     * @return wartość planeSize.
     */
    public int getPlaneSize() {
        return planeSize;
    }
}
