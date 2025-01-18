import java.util.ArrayList;

public class Pion extends Piece implements regle_Piece {
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    /* Constructeur 
     * public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur, Plateau plateau) 
    */
    public Pion(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("PION", xinit, yinit, xactu, yactu, couleur, plateau);
        this.xactu = xactu;
        this.yactu = yactu;
        this.xinit = xinit;
        this.yinit = yinit;
        this.plateau = plateau;
    }

    @Override
    public ArrayList<coordonnee> casesPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();
        int dir = (this.getCouleur().equals("NOIR")) ? 1 : -1;
    
        // Vérifie si la case devant est libre
        if (plateau.estDansLesLimites(xactu + dir, yactu) && plateau.isCaseOccupee(xactu + dir, yactu, "NULL")) {
            coords.add(new coordonnee(xactu + dir, yactu));
            // Déplacement initial de 2 cases
            if (xactu == this.getPositionXinit() && plateau.isCaseOccupee(xactu + 2 * dir, yactu, "NULL")) {
                coords.add(new coordonnee(xactu + 2 * dir, yactu));
            }
        }
    
        // Capture diagonale gauche
        if (plateau.estDansLesLimites(xactu + dir, yactu - 1) && 
            plateau.isCaseOccupee(xactu + dir, yactu - 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
            coords.add(new coordonnee(xactu + dir, yactu - 1));
        }
    
        // Capture diagonale droite
        if (plateau.estDansLesLimites(xactu + dir, yactu + 1) && 
            plateau.isCaseOccupee(xactu + dir, yactu + 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
            coords.add(new coordonnee(xactu + dir, yactu + 1));
        }
    
        return coords;
    }

    @Override
    public ArrayList<coordonnee> casesPrenable(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();

        int dir = (this.getCouleur().equals("NOIR")) ? 1 : -1;

        if (plateau.estDansLesLimites(xactu + dir, yactu - 1) && 
            plateau.isCaseOccupee(xactu + dir, yactu - 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
            coords.add(new coordonnee(xactu + dir, yactu - 1));
        }

        if (plateau.estDansLesLimites(xactu + dir, yactu + 1) && 
            plateau.isCaseOccupee(xactu + dir, yactu + 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
            coords.add(new coordonnee(xactu + dir, yactu + 1));
        }

        return coords;
    }

    @Override
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossibles(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour le Pion en [" + xactu + "," + yactu + "] :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }

    @Override
    public String getName() {
        return "PION";
    }

    @Override
    public String getCouleur() {
        return super.getCouleur();
    }

    @Override
    public void setPosition(int x, int y) {
        this.xactu = x;
        this.yactu = y;
    }

    @Override
    public int getPositionXinit() {
        return this.xinit;
    }

    @Override
    public int getPositionYinit() {
        return this.yinit;
    }
}
