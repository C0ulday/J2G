import java.util.ArrayList;
import java.util.List;

//TODO : Retirer les cases avec Echec

public class Roi extends Piece implements regle_Piece{
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    public Roi(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("ROI", xinit, yinit, xactu, yactu, couleur, plateau);
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
         int[][] directions = {
             {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}
         };
     
         // Récupère les coordonnées de tous les rois adverses
         List<regle_Piece> roisAdverses = trouverRoisAdverses();
     
         for (int[] direction : directions) {
             int xnew = xactu + direction[0];
             int ynew = yactu + direction[1];
     
             // Vérifie que la case est dans les limites du plateau
             if (!plateau.estDansLesLimites(xnew, ynew)) continue;
     
             // Vérifie si la case est adjacente à l'un des rois adverses
             boolean adjacenteARoi = false;
             for (regle_Piece roiAdverse : roisAdverses) {
                 if (Math.abs(xnew - roiAdverse.getPositionX()) <= 1 && 
                     Math.abs(ynew - roiAdverse.getPositionY()) <= 1) {
                     adjacenteARoi = true;
                     break;
                 }
             }
             if (adjacenteARoi) continue;
     
             // Vérifie si la case est vide ou contient une pièce adverse
             regle_Piece piece = plateau.getPiece(xnew, ynew);
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
            regle_Piece piece = plateau.getPiece(x,y);
            
            if (piece != null && !piece.getCouleur().equals(this.getCouleur())) {
                // Si la pièce est d'une couleur différente, elle est prenable
                casesPrenables.add(coord);
            }
        }

        return casesPrenables;
    }

    public boolean deplacementSecurise(Piece roi, int xnew, int ynew) {
        ArrayList<Piece> pieces = plateau.getPlateauPiece();
    
        for (Piece piece : pieces) {
            // Vérifie si la pièce est adverse
            if (!piece.getCouleur().equals(roi.getCouleur())) {
                // Cas particulier pour un autre roi
                if (piece.getName().equals("ROI")) {
                    // Vérifie si la case cible est adjacente à l'autre roi
                    if (Math.abs(piece.getPositionX() - xnew) <= 1 && Math.abs(piece.getPositionY() - ynew) <= 1) {
                        return false; // La case est adjacente à l'autre roi
                    }
                    continue; // Ignore les cases possibles de l'autre roi
                }
    
                // Vérifie si la pièce adverse peut attaquer la position cible
                if (piece instanceof regle_Piece) {
                    ArrayList<coordonnee> casesAdverses = ((regle_Piece) piece).casesPossibles(piece.getPositionX(), piece.getPositionY());
    
                    for (coordonnee coordAdverse : casesAdverses) {
                        if (coordAdverse.getX() == xnew && coordAdverse.getY() == ynew) {
                            return false; // La case est attaquée
                        }
                    }
                }
    
                // Cas particulier pour les pions (les pions attaquent en diagonale)
                if (piece.getName().equals("PION")) {
                    int direction = piece.getCouleur().equals("BLANC") ? -1 : 1;
                    int pionX = piece.getPositionX();
                    int pionY = piece.getPositionY();
    
                    if ((pionX + direction == xnew && pionY + 1 == ynew) ||
                        (pionX + direction == xnew && pionY - 1 == ynew)) {
                        return false; // La case est attaquée par un pion
                    }
                }
            }
        }
    
        return true; // Le déplacement est sécurisé
    }
   private List<regle_Piece> trouverRoisAdverses() {
    List<regle_Piece> roisAdverses = new ArrayList<>();
    List<regle_Piece> pieces = plateau.getPlateauPiece();

    for (regle_Piece piece : pieces) {
        if (piece.getName().equals("ROI") && !piece.getCouleur().equals(this.getCouleur())) {
            roisAdverses.add(piece); // Ajoute le roi adverse à la liste
        }
    }

    return roisAdverses;
}


    @Override
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossibles(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour le Roi :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }
    /*
    public boolean Roque(int x, int y)
    {
        // TODO: créer la fonction roque si on a le temps

        return true;
    }*/
}

