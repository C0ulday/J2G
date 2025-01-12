import java.util.ArrayList;

public class TestPlateau 
{
    public static void main(String[] args) 
    {
        Plateau plateau = new Plateau(8); // Crée un plateau de 8x8
        // Crée un Cavalier
        //Cavalier cavalierBlanc = new Cavalier(9, 7, 9, 7, "BLANC", plateau);
        Tour tour = new Tour(4, 4, 4, 4, "BLANC", plateau);


        // Placement de quelques pièces adverses et alliées
        Piece pionNoir = new Piece("PION", 6, 4, 6, 4, "NOIR", plateau);
        Piece pionBlanc = new Piece("PION", 4, 6, 4, 6, "BLANC", plateau);
        
        // Test des mouvements possibles
        ArrayList<coordonnee> mouvements = tour.casesPossibles_Jouable(4, 4);

        // Affichage des résultats
        System.out.println("Mouvements possibles pour la tour en (4, 4) :");

        System.out.println("Coordonnées jouables initiales Tour : " + tour.casesPossibles_Jouable(4, 4));


        
    }
}

/*
// Teste un déplacement valide
        plateau.deplacementPiece(3, 2, 5, 3); // Déplacement valide pour le Cavalier
        System.out.println("Position actuelle Cavalier après déplacement valide : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")");

        // Teste un déplacement non valide
        plateau.deplacementPiece(5, 3, 6, 6); // Déplacement invalide pour le Cavalier
        System.out.println("Position actuelle Cavalier après déplacement invalide : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")"); 


        // Test d'une case vide
        Piece caseVide = plateau.getPiece(3, 2); // Case initiale du Cavalier, désormais vide
        System.out.println("Ancienne case (3, 2) : " + (caseVide != null ? caseVide.getName() : "Vide"));

        */

/*
// Place le Cavalier sur le plateau
        plateau.placerPiece(cavalierBlanc, 9, 7);
        plateau.placerPiece(tour, 3, 3);
        // Affiche la position initiale
        System.out.println("Position initiale Cavalier : (" + cavalierBlanc.getPositionX() + ", " + cavalierBlanc.getPositionY() + ")");
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
        System.out.println("Coordonnées jouables après déplacement  Cavalier: " + cavalierBlanc.casesPossibles_Jouable(cavalierBlanc.getPositionX(), cavalierBlanc.getPositionY()));

        System.out.println("Coordonnées jouables initiales Tour : " + tour.casesPossibles_Jouable(3, 3));
*/