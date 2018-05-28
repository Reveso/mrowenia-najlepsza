package application.core.entity;

public class Plane {
    private int[][] plane;
    private int planeSize;

    public Plane(int size) {
        this.planeSize = size;
        this.plane = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                plane[i][j] = -1;
        }
    }

    public void printPlane() {
        for (int y = 0; y < planeSize; y++) {
            for (int x = 0; x < planeSize; x++) {
                System.out.print(plane[x][y]);
            }
            System.out.println();
        }
    }

    public int[][] getPlane() {
        return plane;
    }

    private void setPlane(int[][] plane) {
        this.plane = plane;
    }

    public int getValueOnPosition(int x, int y) {
        return plane[x][y];
    }

    public void setValueOnPosition(int x, int y, int value) {
        plane[x][y] = value;
    }

    public int getPlaneSize() {
        return planeSize;
    }
}
