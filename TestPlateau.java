public class TestPlateau {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(8); // Crée un plateau de 8x8

        // Crée une pièce
        Piece pion = new Piece("PION",1,1,1,1, "BLANC", plateau);
        

        // Place la pièce sur le plateau
        plateau.placerPiece(pion, 1, 1);

        // Affiche la position initiale et actuelle
        System.out.println("Position initiale : (" + pion.getPositionXinit() + ", " + pion.getPositionYinit() + ")");
        System.out.println("Position actuelle : " + pion);

        // Essaye de déplacer la pièce
        plateau.deplacementPiece(1, 1, 3, 4);
 
        // Affiche la nouvelle position
        System.out.println("Position actuelle après déplacement : " + pion);
        System.out.println("Position initiale inchangée : (" + pion.getPositionXinit() + ", " + pion.getPositionYinit() + ")");

        Piece caseVide = plateau.getPiece(1, 1); // Ajoute une méthode pour obtenir une pièce à une position donnée
        System.out.println("Ancienne case (1, 1) : " + caseVide);
    }
}
/*Resultat attendu 
 * Position initiale : (1, 1)
    Position actuelle : PION BLANC (1, 1)
    Déplacement réussi !
    Position actuelle après déplacement : PION BLANC (3, 4)
    Position initiale inchangée : (1, 1)
 */