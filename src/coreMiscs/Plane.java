package coreMiscs;

public class Plane {
    private int[][] plane;
    private int planeSize;
    public void printPlane (/*int[][] plane, int planeSize*/) {
        for (int j=0; j < planeSize; j++) {
            for (int i = 0; i < planeSize; i++) {
                System.out.print(plane[i][j]);
            }
            System.out.println();
        }
    }

    public  Plane(int size) {
        this.planeSize = size;
        this.plane = new int[size][size];
        for(int i=0; i<size; i++) {
            for (int j = 0; j < size; j++)
                plane[i][j] = -1;
        }
    }

    public int[][] getPlane() {
        return plane;
    }

    private void setPlane(int[][] plane) {
        this.plane = plane;
    }
    public int cordinateValue(int x, int y){
        return plane[x][y];
    }
    public void setCordinatesValue(int x, int y, int value){
        plane[x][y] = value;
    }
}
