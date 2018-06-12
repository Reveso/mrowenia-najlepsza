package application.langtonsant.entity;

import java.io.Serializable;

/**
 * Instancja tej klasy reprezentuje mapę wartości, czyli tablicę dwuwymiarową na której wartości reprezentują
 * konkretny kolor.
 */

public class Plane implements Serializable {
    long serialVersionUID = 1L;

    private int[][] plane;
    private int planeSize;

    /**
     * zwraca mapę wartości o zadanej wielkości.
     * @param size wielkość mapy wartości - mapa z założenia jest kwadratem.
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
     * metoda wypisuje mapę wartości na konsolę
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
     *
     * @return zwraca mapę wartości.
     */
    public int[][] getPlane() {
        return plane;
    }

    /**
     *
     * @param plane ustawia mapę wartości.
     */

    private void setPlane(int[][] plane) {
        this.plane = plane;
    }

    /**
     *
     * @param x współrzędna x.
     * @param y współrzędna x.
     * @return Zwraca wartosc konkretnej komórki mapy wartości.
     */

    public int getValueOnPosition(int x, int y) {
        return plane[x][y];
    }

    /**
     * ustawia wartosc konkretnej komórki mapy wartości.
     * @param x współrzędna x.
     * @param y współrzędna y.
     * @param value wartość zadanej komórki.
     */

    public void setValueOnPosition(int x, int y, int value) {
        plane[x][y] = value;
    }

    /**
     *
     * @return wielkość mapy wartości.
     */

    public int getPlaneSize() {
        return planeSize;
    }
}
