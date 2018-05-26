package basicAntCore;

import java.util.ArrayList;
import java.util.List;
import Ant.Ant; //bedzie w coreMiscs
import coreMiscs.coreMiscs;
import coreMiscs.Plane;

public class basicAntController {

    private int startX;
    private int startY;
//    OneWayList antListHead = new OneWayList();
    List<Ant> antListHead;

    private void multiAntSetDirection(Ant ant, Plane plane) {
        int nextDirection = plane.cordinateValue(ant.getX(),ant.getY());
        if (nextDirection == -1) //bialy - w prawo
            ant.setDirection(ant.getDirection()+1);
        else    //czorny - w lewo
            ant.setDirection(ant.getDirection()+3);  // += 3;
        ant.setDirection(ant.getDirection()%4);//kierunek %= 4;
    }

    void multiAntSetPlaneValue(Ant ant, Plane plane) {
        if(plane.cordinateValue(ant.getX(),ant.getY()) == -1) {
            plane.setCordinatesValue(ant.getX(), ant.getY(), ant.getId());
        } else {
            plane.setCordinatesValue(ant.getX(), ant.getY(),  -1);
        }
    }

    void multiAntStep(Ant ant, Plane plane) {
        multiAntSetPlaneValue(ant, plane);

        coreMiscs.antMove(ant);
        multiAntSetDirection(ant, plane);
    }


    boolean iterateThroughAntList(List<Ant> list, int planeSize, Plane plane){
        boolean anyMove=false;
//        Iterator iterator = new Iterator();
        for (Ant ant:list) {
//            plane.printPlane();
            System.out.println("X:  " + ant.getX());
            System.out.println("Y:  " + ant.getY());
            if(coreMiscs.isCrashed(ant, planeSize))
                ant.setActive(false);
            if(ant.isActive()){
                multiAntStep(ant, plane);
                anyMove = true;
            }
        }
        return anyMove;
    }

    private void setStartingLocations(List list){
        System.out.println("podaj poczatkowe wspolrzedne mrowki");
        setStartX(4);//System.in obsluga cnie
        setStartY(4);//Ja to bym do konstruktora jeszcze kolor wrzucil i po problemie tylko trzeba by obsluge kolorku
        list.add(new Ant(4,4));
    }


    public basicAntController(int planeSize, int maxSteps, int antCount, Plane plane){
        //plane = createPlane(planeSize);
        antListHead = new ArrayList<Ant>();
        for(int i=0; i<antCount;i++)
            setStartingLocations(antListHead);//add Ant rowniez w tej metodzie no bo tak #oryginalssie
        //while((maxSteps--)>0)
        for (int i=0; i< maxSteps; i++)
        {
            plane.printPlane();
            for(int k=0; k<5; k++)
                System.out.println();
            if(!iterateThroughAntList(antListHead,planeSize, plane))
                break; //iteruje po liscie w razie czego wypierdala
        }

    }

    public void printAntsLocations(List<Ant> ants) {
        for (Ant ant: ants) {
            System.out.println("id: " + ant.getId() + "; x: " + ant.getX() + "; y: " + ant.getY());
        }
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
