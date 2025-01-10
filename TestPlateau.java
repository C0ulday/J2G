public class TestPlateau {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(8); // Crée un plateau de 8x8
        // Crée un cavalier
        cavalier cavalierBlanc = new cavalier(9, 7, 9, 7, "BLANC", plateau);

        // Place le cavalier sur le plateau
        plateau.placerPiece(cavalierBlanc, 9, 7);

        // Affiche la position initiale
        System.out.println("Position initiale cavalier : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")");
        System.out.println("Coordonnées jouables initiales : " + cavalierBlanc.casesPossibles_Jouable(3, 2));

        // Vérification des cases jouables après déplacement
        System.out.println("Coordonnées jouables après déplacement : " + cavalierBlanc.casesPossibles_Jouable(cavalierBlanc.getPositionX(), cavalierBlanc.getPositionY()));

        // Test d'une prise de pièce
        Piece pionNoir = new Piece("PION", 1, 1, 1, 1, "NOIR", plateau);
        Piece pionBlanc = new Piece("PION", 1, 3, 1, 3, "BLANC", plateau);
        plateau.placerPiece(pionNoir, 1, 1);
        System.out.println("Coordonnées jouables après déplacement pion noir: " + pionNoir.getPositionX() +" "+ pionNoir.getPositionY());

        plateau.placerPiece(pionBlanc, 1, 3);
        System.out.println("Coordonnées jouables après déplacement pion blanc: " + pionBlanc.getPositionX() + " "+ pionBlanc.getPositionY());

        //plateau.deplacementPiece(5, 3, 6, 1);
        System.out.println("Coordonnées jouables après déplacement  cavalier: " + cavalierBlanc.casesPossibles_Jouable(cavalierBlanc.getPositionX(), cavalierBlanc.getPositionY()));


    }
}

/*
// Teste un déplacement valide
        plateau.deplacementPiece(3, 2, 5, 3); // Déplacement valide pour le cavalier
        System.out.println("Position actuelle cavalier après déplacement valide : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")");

        // Teste un déplacement non valide
        plateau.deplacementPiece(5, 3, 6, 6); // Déplacement invalide pour le cavalier
        System.out.println("Position actuelle cavalier après déplacement invalide : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")"); 


        // Test d'une case vide
        Piece caseVide = plateau.getPiece(3, 2); // Case initiale du cavalier, désormais vide
        System.out.println("Ancienne case (3, 2) : " + (caseVide != null ? caseVide.getName() : "Vide"));

        */