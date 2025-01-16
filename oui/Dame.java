import java.util.ArrayList;

public class Dame extends Piece implements regle_Piece {
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    public Dame(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("DAME", xinit, yinit, xactu, yactu, couleur, plateau);
        this.xactu = xactu;
        this.yactu = yactu;
        this.xinit = xinit;
        this.yinit = yinit;
        this.plateau = plateau;
    }

    @Override
    public ArrayList<coordonnee> casesPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();
        int[][] directions = { 
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Ligne droite
            {1, -1}, {1, 1}, {-1, -1}, {-1, 1} // Diagonales
        };

        for (int[] direction : directions) {
            int xnew = xactu, ynew = yactu;

            while (true) {
                xnew += direction[0];
                ynew += direction[1];

                if (!plateau.estDansLesLimites(xnew, ynew)) break;

                Piece piece = plateau.getPiece(xnew, ynew);
                if (piece == null) {
                    coords.add(new coordonnee(xnew, ynew)); // Case vide
                } else {
                    if (!piece.getCouleur().equals(this.getCouleur())) {
                        coords.add(new coordonnee(xnew, ynew)); // Case ennemie
                    }
                    break;
                }
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
    
        System.out.println("Coordonnées possibles pour la Dame en ["+xactu+","+yactu+"] :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }
}
