public class Ant {
    public int id;
    public int kerunek;
    public int x;
    public int y;
    public boolean isActive;
    public void antMove(){
        switch (this.kerunek) {
            case 0:
                this.y -= 1;
                break;
            case 1:
                this.x += 1;
                break;
            case 2:
                this.y += 1;
                break;
            case 3:
                this.x -= 1;
                break;
            default:
                System.out.println("KUREA TEGO SIE NIE SPODZIEWALEM");

        }
    }
}
