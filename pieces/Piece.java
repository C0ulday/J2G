
public class Piece {
    private String name;
    private String couleur;
    private int positionXinit;
    private int positionYinit;
    private int positionX;
    private int positionY;
 
    public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur) {
        this.name = name;
        this.positionXinit = positionXinit;
        this.positionYinit = positionYinit;
        this.positionX = positionX;
        this.positionY = positionY;
        this.couleur = couleur;

    }

    public int getPositionXinit() {

        return this.positionXinit;
    }

    public int getPositionYinit() {
        return this.positionYinit;
    }
    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    public boolean coupPossible(int x, int y){
        return false;
    }


    @Override
    public String toString() {
        return name + " " + couleur + " (" + positionX + ", " + positionY + ")";
    }
}
