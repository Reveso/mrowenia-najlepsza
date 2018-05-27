package application.core.miscs;

public class CoreMiscs {
    public static boolean isCrashed(Ant ant, int planeSize) {
        if (ant.getX() > 0 && ant.getY() > 0 &&
                ant.getX() < planeSize - 1 && ant.getY() < planeSize - 1)
            return false;
        else return true;
    }

    public static boolean checkBehaviourString(String behaviourString, String compare) {
        if(behaviourString.length()<2)
            return false;

        for (int i = 0; i < behaviourString.length(); i++) {
            if(behaviourString.charAt(i) !='R'  &&  behaviourString.charAt(i) !='L') {
                System.out.println("Zly ciag");
                return false;
            }
        }
        if (!behaviourString.equals(compare)) return false;
        return true;
    }


    public static void antMove(Ant ant) {
        switch (ant.getDirection()) {
            case 0:
                ant.setY(((ant.getY()-1))); //y-=1;
                break;
            case 1:
                ant.setX((ant.getX()+1)); //x+=1;
                break;
            case 2:
                ant.setY((ant.getY()+1)); //y+=1;
                break;
            case 3:
                ant.setX((ant.getX()-1)); //x-=1;
                break;
            default:
                System.err.println("KUREA NNIE SPODZIEWALEM SIE TEGO");
        }
    }

    public void printAntPosition(Ant ant) {
        System.out.println("x:  " + ant.getX());
        System.out.println("y:  " + ant.getY());
    }



    public static void setAntPosition (Ant ant, int x, int y) {
        ant.setX(x);
        ant.setY(y);
    }
}