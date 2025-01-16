import java.util.ArrayList;

public class Tour extends Piece implements regle_Piece{
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    /* Constructeur 
     * public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur, Plateau plateau) 
    */
    public Tour(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("TOUR", xinit, yinit, xactu, yactu, couleur, plateau);
        this.xactu = xactu;
        this.yactu = yactu;
        this.xinit = xinit;
        this.yinit = yinit;
        this.plateau = plateau;
    }

    /**
     * Verifie si le deplacement est possible
     * @param x L'abscisse d'arrive = les lettres
     * @param y l'ordonnee d'arrive = les chiffres
     * @return resultat du test
     */
    @Override
    public ArrayList<coordonnee> casesPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // Haut, Bas, Gauche, Droite

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
                    break; // Bloquer le mouvement après un obstacle
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
            if (!piece.getCouleur().equals(this.getCouleur()) || piece == null)  // si la case est vide
            {
                // Case vide, ajoutée aux mouvements possibles
                casesPrenables.add(coord);
            }
        }

        return casesPrenables;
    }

    @Override
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossibles(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour la Tour en ["+xactu+","+yactu+"] :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }

}