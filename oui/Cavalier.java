import java.util.ArrayList;

class Cavalier extends Piece implements regle_Piece {
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;


    public Cavalier(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("CAVALIER", xinit, yinit, xactu, yactu, couleur, plateau);
        this.xactu = xactu;
        this.yactu = yactu;
        this.xinit = xinit;
        this.yinit = yinit;
        this.plateau = plateau;
    }

    @Override
public ArrayList<coordonnee> casesPossibles(int xactu, int yactu) {
    ArrayList<coordonnee> coords = new ArrayList<>();
    int[][] deplacements = {
        {-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
    };

    for (int[] deplacement : deplacements) {
        int xnew = xactu + deplacement[0];
        int ynew = yactu + deplacement[1];

        if (!plateau.estDansLesLimites(xnew, ynew)) continue;

        Piece piece = plateau.getPiece(xnew, ynew);
        if (piece == null || !piece.getCouleur().equals(this.getCouleur())) {
            coords.add(new coordonnee(xnew, ynew));
        }
    }
    return coords;
}

    @Override
    public ArrayList<coordonnee> casesPrenable(int xactu, int yactu) {
        ArrayList<coordonnee> casesPrenables = new ArrayList<>();
        ArrayList<coordonnee> casesJouables = casesPossibles(xactu, yactu);

        for (coordonnee coord : casesJouables) {
            int x = coord.getX();
            int y = coord.getY();
            
            // Récupérer la pièce à la position (x, y)
            Piece piece = plateau.getPiece(x,y);
            
            if (piece != null && !piece.getCouleur().equals(this.getCouleur())) {
                // Si la pièce est d'une couleur différente, elle est prenable
                casesPrenables.add(coord);
            }
        }

        return casesPrenables;
    }


    @Override
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossibles(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour le Cavalier en ["+xactu+","+yactu+"] :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }
}