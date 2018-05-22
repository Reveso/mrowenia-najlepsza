import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BasicAntCore {
    List list = new List();
    Iterator iterator = list.iterator();
    public int[][] plane;

    public void setPlane(int[][] plane) {
        this.plane = plane;
    }
    private boolean isCrashed(Ant ant, int planeSize) {
        if (ant.x > 0 && ant.x < planeSize - 1 && ant.y > 0 && ant.y < planeSize - 1)
            return false;
        else return true;
    }

    public void multiAntSetDirection(Ant ant) {
        int nextDirection = plane[ant.x][ant.y];
        if (nextDirection == -1) //bialy - w prawo
            (ant.kerunek)++;
        else    //czorny - w lewo
            (ant.kerunek) += 3;
        (ant.kerunek) %= 4;
    }

    void multiAntSetPlaneValue(Ant ant) {
        if(plane[ant.x][ant.y] == -1) {
            plane[ant.x][ant.y] = ant.id;
        } else {
            plane[ant.x][ant.y] = -1;
        }
    }
//    void multiAntStep(Ant ant, SDL_Renderer *renderer, SDL_Rect r, ColorList *colorList, int antSize) {
//        multiAntSetPlaneValue(ant);
//        chooseColor(ant, renderer, r, colorList, plane, antSize);
//
//
//        antMove(ant);
//        multiAntSetDirection(ant);
//    }
    public boolean iterateThroughAntList(AntList *l, ColorList *colorList, int planeSize,SDL_Renderer *renderer, SDL_Rect r, int antSize) {
        boolean anyMove = false;

        for(Ant ant: antList) {
            if(ant.isActive){
                multiAntStep(ant);
                anymove=true;
        }
        return !(list.isEmpty());
//        return anyMove;
    }
    public void setStartingLocations() {
        int startX;
        int startY;
//        Scanner scanner= new Scanner();
//        for(int i=1; l!= null; i++) {
//            System.out.println();("\nPodaj poczatkowe wspolrzedne mrowki nr %d:\n", i);
//            scanf("%d", &startX);
//            scanf("%d", &startY);
//alternatywe poprosze szmato
            setAntPosition(l->ant, startX, startY);
            l->ant->id = i-1;
            l->ant->kierunek = 1;
            l->ant->isActive = true;

            l = l->next;
        }
    }
}
