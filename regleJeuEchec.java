import java.util.ArrayList;

public class regleJeuEchec {

    public static boolean Echec(Plateau plateau,String couleur)
    {
        ArrayList<Piece> pieces = plateau.getPlateauPiece();
        int xRoi,yRoi;

        for (Piece roi : pieces) {
            // Vérifie si la pièce est le roi de la couleur couleur
            if (roi.getName().equals("ROI") && roi.getCouleur().equals(couleur)) 
            {
                // On récupère ses coordonnées
                xRoi = roi.getPositionX();
                yRoi = roi.getPositionY();
                
                // pour chaque pièce adverse au roi
                for (Piece pieceDepart : pieces) 
                {
                    if (pieceDepart instanceof regle_Piece && !pieceDepart.getCouleur().equals(couleur) ) {
                        ArrayList<coordonnee> casesAdverses = ((regle_Piece) pieceDepart).casesPossibles(pieceDepart.getPositionX(),pieceDepart.getPositionY());
        
                        // Vérifie si la position cible est attaquée
                        for (coordonnee coordAdverse : casesAdverses) {
                            if (coordAdverse.getX() == xRoi && coordAdverse.getY() == yRoi) {
                                return true; // Le déplacement met le roi en échec
                            }
                        }
                    }
                
                    // Cas particulier pour les pions (les pions attaquent en diagonale)
                    if (pieceDepart.getName().equals("PION")) 
                    {
                        int direction = pieceDepart.getCouleur().equals("BLANC") ? -1 : 1; // Sens d'attaque du pion
                        int pionX = pieceDepart.getPositionX();
                        int pionY = pieceDepart.getPositionY();
        
                        if ((pionX + direction == xRoi && pionY + 1 == yRoi) ||
                            (pionX + direction == xRoi && pionY - 1 == yRoi)) {
                            return true; // Le pion attaque la case cible
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean Pat(Plateau plateau, String couleur) {
        return !Echec(plateau, couleur) && mouvementRealisable(plateau,couleur);
    }
    

    public static boolean Mat(Plateau plateau, String couleur) 
    {
        return Echec(plateau, couleur) && mouvementRealisable(plateau,couleur);
    }
    
    
    
    
    
    
    
    
    
    public static boolean mouvementExposeRoi(Plateau plateau, Piece pieceDepart, int xnew, int ynew) {
        // Sauvegarde de l'état actuel
        Piece pieceArrive = plateau.getPiece(xnew, ynew);
        int xactu = pieceDepart.getPositionX();
        int yactu = pieceDepart.getPositionY();
    
        // Simuler le déplacement
        plateau.placerPiece(pieceDepart, xnew, ynew);
        plateau.viderCase(xactu, yactu);
    
        // Vérifier si le roi est en échec après le déplacement
        boolean exposeRoi = Echec(plateau, pieceDepart.getCouleur());
    
        // Restaurer l'état initial
        plateau.placerPiece(pieceDepart,xactu, yactu);
        plateau.viderCase(xnew,ynew);

        if (pieceArrive != null) {
            plateau.placerPiece(pieceArrive, xnew, ynew);
        }
    
        return exposeRoi;
    }

    public static boolean mouvementRealisable(Plateau plateau, String couleur)
    {
        ArrayList<Piece> pieces = plateau.getPlateauPiece();
            for (Piece pieceDepart : pieces) {
                if (pieceDepart.getCouleur().equals(couleur) && pieceDepart instanceof regle_Piece) {
                    ArrayList<coordonnee> casesPossibles = ((regle_Piece) pieceDepart).casesPrenable(pieceDepart.getPositionX(), pieceDepart.getPositionY());
                    for (coordonnee coord : casesPossibles) {
                        if (!mouvementExposeRoi(plateau, pieceDepart, coord.getX(), coord.getY())) {
                            return false; // Un mouvement légal est possible
                        }
                    }
                }
            }
            return true; // Aucun mouvement légal, c'est un mat
    }
}