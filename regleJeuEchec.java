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
                for (Piece piece : pieces) 
                {
                    if (piece instanceof regle_Piece && !piece.getCouleur().equals(couleur) ) {
                        ArrayList<coordonnee> casesAdverses = ((regle_Piece) piece).casesPossibles(piece.getPositionX(),piece.getPositionY());
        
                        // Vérifie si la position cible est attaquée
                        for (coordonnee coordAdverse : casesAdverses) {
                            if (coordAdverse.getX() == xRoi && coordAdverse.getY() == yRoi) {
                                return true; // Le déplacement met le roi en échec
                            }
                        }
                    }
                
                    // Cas particulier pour les pions (les pions attaquent en diagonale)
                    if (piece.getName().equals("PION")) 
                    {
                        int direction = piece.getCouleur().equals("BLANC") ? -1 : 1; // Sens d'attaque du pion
                        int pionX = piece.getPositionX();
                        int pionY = piece.getPositionY();
        
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
        ArrayList<Piece> pieces = plateau.getPlateauPiece();
    
        // Vérifie si le roi est en échec
        if (Echec(plateau, couleur)) {
            return false; // Si le roi est en échec, ce n'est pas un pat
        }
    
        // Vérifie si au moins une pièce peut bouger
        for (Piece piece : pieces) {
            if (piece.getCouleur().equals(couleur) && piece instanceof regle_Piece) {
                ArrayList<coordonnee> casesPossibles = ((regle_Piece) piece).casesPossibles(piece.getPositionX(), piece.getPositionY());
                if (!casesPossibles.isEmpty()) {
                    return false; // Si une pièce peut bouger, ce n'est pas un pat
                }
            }
        }
    
        // Si aucune pièce ne peut bouger et le roi n'est pas en échec, c'est un pat
        return true;
    }
    

    public static boolean Mat(Plateau plateau, String couleur) {
        if (Echec(plateau, couleur)) {
            ArrayList<Piece> pieces = plateau.getPlateauPiece();
            for (Piece piece : pieces) {
                if (piece.getCouleur().equals(couleur) && piece instanceof regle_Piece) {
                    ArrayList<coordonnee> casesPossibles = ((regle_Piece) piece).casesPossibles(piece.getPositionX(), piece.getPositionY());
                    for (coordonnee coord : casesPossibles) {
                        if (!mouvementExposeRoi(plateau, piece, coord.getX(), coord.getY())) {
                            return false; // Un mouvement légal est possible
                        }
                    }
                }
            }
            return true; // Aucun mouvement légal, c'est un mat
        }
        return false;
    }
    public static boolean mouvementExposeRoi(Plateau plateau, Piece piece, int xNew, int yNew) {
        // Sauvegarde de l'état actuel
        Piece pieceCapturee = plateau.getPiece(xNew, yNew);
        int xOld = piece.getPositionX();
        int yOld = piece.getPositionY();
    
        // Simuler le déplacement
        plateau.deplacementPiece(xOld, yOld, xNew, yNew);
    
        // Vérifier si le roi est en échec après le déplacement
        boolean exposeRoi = Echec(plateau, piece.getCouleur());
    
        // Restaurer l'état initial
        plateau.deplacementPiece(xNew, yNew, xOld, yOld);
        if (pieceCapturee != null) {
            plateau.placerPiece(pieceCapturee, xNew, yNew);
        }
    
        return exposeRoi;
    }
}
